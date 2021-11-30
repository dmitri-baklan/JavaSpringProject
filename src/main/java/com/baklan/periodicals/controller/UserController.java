package com.baklan.periodicals.controller;

import com.baklan.periodicals.dto.ReplenishmentDTO;
import com.baklan.periodicals.dto.UserDTO;
import com.baklan.periodicals.entity.user.User;
import com.baklan.periodicals.exception.UserNotFoundException;
import com.baklan.periodicals.service.ReplenishmentService;
import com.baklan.periodicals.service.UserService;
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
@RequestMapping("/profile")
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private final ReplenishmentService replenishmentService;

    @Autowired
    private final UserService userService;

    @GetMapping
    public String getUserProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userService.getUserByEmailAuth(userDetails);
        model.addAttribute("user", user);
        model.addAttribute("periodicals", user.getPeriodicals());
        return "user/profile";
    }

    @GetMapping("/edit")
    public String editUserProfile(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userService.getUserByEmailAuth(userDetails);
        user.setPassword(userDetails.getPassword());
        model.addAttribute("user", user);
        model.addAttribute("periodicals", user.getPeriodicals());
        return "user/editUser";
    }

    @PostMapping("/edit")
    public String editPeriodical(@ModelAttribute("user") @Valid UserDTO userDTO,
                                 BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info(" >> userDTO: {}", userDTO.toString());
            return "user/editUser";
        }

        try {
            userService.updateUser(userDTO);
        } catch (Exception ex) {
            log.info(" >> userDTO: {}", userDTO.toString());
            bindingResult.rejectValue("name", "valid.user.name.exists");
            return "user/editUser";
        }

        return "redirect:/profile";
    }

    @GetMapping("/readers")
    public String getReadersPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "8") Integer size,
                                     @RequestParam(required = false, defaultValue = "") String searchQuery,
                                     Model model){

        Page<User> readers =  userService.getAllReaders(page, size, searchQuery);
        model.addAttribute("readers", readers);

        Map<String, String> request_params = new HashMap<String, String>();
        request_params.put("page", page.toString());
        request_params.put("size", size.toString());
        request_params.put("searchQuery", searchQuery);
        model.addAttribute("request_params", request_params);

        return "user/userList";
    }

    @PostMapping("/readers/{id}")
    public String changeReaderStatus(@ModelAttribute("id") Long id){
        try{
            userService.changeIsActive(id);
        } catch (Exception ex){
            log.info(ex.getMessage());
            return "redirect:/profile/readers";
        }
        return "redirect:/profile/readers";
    }

    @GetMapping("/replenishment")
    public String getReplenishmentPage(Model model){
        model.addAttribute("replenishment", new ReplenishmentDTO());
        return "user/replenishment";
    }

    @PostMapping("/replenishment")
    public String replenishReaderBalance(@ModelAttribute("replenishment") @Valid ReplenishmentDTO replemishmentDTO,
                                         BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info(" >> replemishmentDTO: {}", replemishmentDTO.toString());
            return "user/replenishment";
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        try {
            replenishmentService.replenishBalance(userDetails, replemishmentDTO);
        } catch (UserNotFoundException ex) {
            log.info(" >> 'ex' replenishReaderBalance: {}", ex.getMessage());
            return "redirect:/profile";
        }

        return "redirect:/profile";
    }
}
