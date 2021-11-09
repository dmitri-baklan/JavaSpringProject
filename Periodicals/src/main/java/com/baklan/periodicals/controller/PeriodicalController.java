package com.baklan.periodicals.controller;

import com.baklan.periodicals.dto.PeriodicalDTO;
import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.exception.PeriodicalNotFoundException;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.service.PeriodicalService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@AllArgsConstructor
@RequestMapping("/periodicals")
@Controller
public class PeriodicalController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);


    @Autowired
    private PeriodicalService periodicalService;

    @GetMapping
    public String getPeriodicalsPage(@RequestParam (required = false, defaultValue = "name") String sortField,
                                     @RequestParam(required = false, defaultValue = "") String subject,
                                     @RequestParam(required = false, defaultValue = "true") Boolean asc,
                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "5") Integer size,
                                     @RequestParam(required = false, defaultValue = "") String searchQuery,
                                     Model model){
        log.info("[PAGINATION] Input params: sortField:[{}], subject:[{}], direction:[{}], page:[{}], size:[{}], searchQuery:[{}]",
                sortField, subject, asc, page, size, searchQuery);
        Page<Periodical> periodicals =  periodicalService.getAllPeriodicals(sortField,
                subject, asc, page, size, searchQuery);

        model.addAttribute("periodicals", periodicals);

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Map<String, Boolean> user_subscribe = new HashMap<String, Boolean>();

        periodicals.stream()
                .forEach(p->user_subscribe.put(p.getName(),
                        p.getUsers().stream()
                                .anyMatch(u->u.getEmail().equals(userDetails.getUsername()))));

        Map<String, String> request_params = new HashMap<String, String>();
        request_params.put("sortField", sortField);
        request_params.put("subject", subject);
        request_params.put("asc", asc.toString());
        request_params.put("page", page.toString());
        request_params.put("size", size.toString());
        request_params.put("searchQuery", searchQuery);
        model.addAttribute("request_params", request_params);
        model.addAttribute("user_subscribe", user_subscribe);

        return "periodical/periodicals";
    }

    @GetMapping("/add")
    public String getAddPeriodical(Model model){
        model.addAttribute("periodical", new PeriodicalDTO());
        return "periodical/periodicalAdd";
    }

    @GetMapping("/{id}")
    public String getPeriodicalPage(@PathVariable("id") Long id, Model model){
        Periodical periodical;
        try{
            periodical = periodicalService.getPeriodicalById(id);
        }catch (Exception ex){
            log.info(ex.getMessage());
            return "redirect:/periodicals";
        }

        model.addAttribute("periodical", periodical);
        model.addAttribute("users", periodical.getUsers());

        return "periodical/periodicalPage";
    }

    @GetMapping("/{id}/edit")
    public String getPeriodicalPageEdit(@PathVariable("id") Long id, Model model){
        Periodical periodical;
        try{
            periodical = periodicalService.getPeriodicalById(id);
        }catch (Exception ex){
            log.info(ex.getMessage());
            return "redirect:/periodicals";
        }
        model.addAttribute("periodical",periodical);
        return "periodical/periodicalEdit";
    }

    @PostMapping("/{id}/edit")
    public String editPeriodical(@PathVariable("id") Long id,
                                 @ModelAttribute("periodical") @Valid PeriodicalDTO periodicalDTO,
                                 BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info(" >> periodicalDTO: {}", periodicalDTO.toString());
            return "periodical/periodicalEdit";
        }

        try {
            periodicalService.updatePeriodical(periodicalDTO, id);
        } catch (Exception ex) {
            log.info(" >> periodicalDTO: {}", periodicalDTO.toString());
            bindingResult.rejectValue("name", "valid.periodical.name.exists");
            return "periodical/periodicalEdit";
        }

        return "redirect:/periodicals";
    }


    @PostMapping("/add")
    public String addPeriodical(@ModelAttribute("periodical") @Valid PeriodicalDTO periodicalDTO,
                                BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info(" >> periodicalDTO: {}", periodicalDTO.toString());
            return "periodical/periodicalAdd";
        }

        try {
            periodicalService.savePeriodical(periodicalDTO);
        } catch (Exception ex) {
            log.info(" >> periodicalDTO: {}", periodicalDTO.toString());
            bindingResult.rejectValue("name", "valid.periodical.name.exists");
            return "periodical/periodicalAdd";
        }

        return "redirect:/periodicals";
    }

    @PostMapping("/{id}/delete")
    public String deletePeriodical(@PathVariable("id") Long id){
        try{
            periodicalService.deletePeriodical(id);
        } catch (Exception ex){
            log.info(ex.getMessage());
        }

        return "redirect:/periodicals";
    }

    @PostMapping("/{id}/subscription")
    public String changeReaderSubscription(@PathVariable("id") Long id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        try{
            periodicalService.changeSubscription(id, userDetails);
        }catch (UserNotFoundException ex){
            log.info("Exception: {}", ex.getClass());
        }catch (PeriodicalNotFoundException ex){
            log.info("Exception: {}", ex.getClass());
        }
        return "redirect:/periodicals";
    }


}
