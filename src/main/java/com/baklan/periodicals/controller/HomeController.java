package com.baklan.periodicals.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/periodiclaspringwebapp.azurewebsites.net/")
    public String root(Model model) {
        return "redirect:/welcome";
    }

    @GetMapping("/periodiclaspringwebapp.azurewebsites.net/welcome")
    public String welcome(Model model) {
        return "home.html";
    }

    @RequestMapping("/periodiclaspringwebapp.azurewebsites.net/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login.html";
        }
        return "redirect:/periodicals";
    }
}
