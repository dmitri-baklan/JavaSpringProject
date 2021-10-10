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

//@AllArgsConstructor
@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "home.html";
        }
        return "redirect:/periodicals";
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        System.out.println(SecurityContextHolder.getContext().toString());
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "login.html";
        }
        return "redirect:/periodicals";
    }
}
