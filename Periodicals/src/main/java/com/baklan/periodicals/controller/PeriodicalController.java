package com.baklan.periodicals.controller;

import com.baklan.periodicals.service.PeriodicalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/periodicals")
@Controller
public class PeriodicalController {

    @Autowired
    private PeriodicalService periodicalService;

    @GetMapping
    public String periodicalsMain(){
        return "periodical/periodicals.html";
    }
}
