package com.baklan.periodicals.controller;

import com.baklan.periodicals.exception.NotEnoughBalanceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NotEnoughBalanceException.class})
    public String handlePeriodicalsSubscription(RedirectAttributes redirectAttributest){

        redirectAttributest.addFlashAttribute("NotEnoughBalanceError", true);
        return "redirect:/periodicals";
    }
}
