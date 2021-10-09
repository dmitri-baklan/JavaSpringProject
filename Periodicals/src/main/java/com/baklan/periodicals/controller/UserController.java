package com.baklan.periodicals.controller;

import com.baklan.periodicals.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/profile")
@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public String getUserProfile(){
        return "user/profile";
    }
}
