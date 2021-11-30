package com.baklan.periodicals.controller;


import com.baklan.periodicals.entity.replenishment.Replenishment;
import com.baklan.periodicals.service.ReplenishmentService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RequestMapping("/replenishments")
@Controller
public class ReplenishmentController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private ReplenishmentService replenishmentService;

    @GetMapping
    public String getReplenishmentsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "8") Integer size,
                                        @RequestParam(required = false, defaultValue = "time") String sortField,
                                        Model model){

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Page<Replenishment> replenishments =  replenishmentService.getAllReplenishments(page, size, sortField, userDetails);
        model.addAttribute("replenishments", replenishments);

        return "replenishment/replenishments";
    }

}
