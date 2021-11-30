package com.baklan.periodicals.controller;

import javax.validation.Valid;

import com.baklan.periodicals.dto.UserDTO;
import com.baklan.periodicals.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/registration")
@Controller
public class RegistrationController extends ResponseEntityExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private final UserService userService;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registration";
    }

    @PostMapping
    public String registrateNewUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info(" >> userDTO: {}", userDTO.toString());
            return "registration";
        }

        try {

            userService.signUpUser(userDTO);
        } catch (Exception ex) {
            log.info(" >> userDTO: {}", userDTO.toString());
            bindingResult.rejectValue("email", "valid.user.email.exists");
            return "registration";
        }
        return "redirect:/login";
    }

}
