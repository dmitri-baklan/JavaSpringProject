package com.baklan.periodicals.controller;

import com.baklan.periodicals.entity.periodicals.Periodical;
import com.baklan.periodicals.service.PeriodicalService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
                                     HttpServletRequest httpServletRequest,
                                     Model model){
        model.addAttribute("periodicals",
                periodicalService.getAllPeriodicals(sortField, subject, asc, page, size, searchQuery));

        Map<String, String> request_params = new HashMap<String, String>();
        request_params.put("sortField", sortField);
        request_params.put("subject", subject);
        request_params.put("asc", asc.toString());
        request_params.put("page", page.toString());
        request_params.put("size", size.toString());
        request_params.put("searchQuery", searchQuery);
        model.addAttribute("request_params", request_params);

        return "periodical/periodicals";
    }
}
