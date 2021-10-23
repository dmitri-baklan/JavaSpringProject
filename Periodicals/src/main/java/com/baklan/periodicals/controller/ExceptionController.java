package com.baklan.periodicals.controller;

import com.baklan.periodicals.exception.NotEnoughBalanceException;
import com.baklan.periodicals.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpClient;


@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({NotEnoughBalanceException.class})
    public String handlePeriodicalsSubscription(HttpServletRequest request,
                                    NotEnoughBalanceException e,
                                    RedirectAttributes redirectAttributest){

        redirectAttributest.addFlashAttribute("NotEnoughBalanceError", true);
        return "redirect:/periodicals";
    }
}
