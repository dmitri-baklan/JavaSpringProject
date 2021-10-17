package com.baklan.periodicals.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RequestMapping("/replenishments")
@Controller
public class ReplenishmentController {

    @GetMapping
    public String getReplenishmentsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "8") Integer size,
                                        @RequestParam(required = false, defaultValue = "") String searchQuery,
                                        Model model){

    }


}
