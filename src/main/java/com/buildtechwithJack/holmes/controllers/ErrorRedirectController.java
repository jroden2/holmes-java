package com.buildtechwithJack.holmes.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.buildtechwithJack.holmes.models.CompareRequest;

@Controller
@RequestMapping("/error")
public class ErrorRedirectController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorRedirectController.class);

    public ErrorRedirectController() {
    }

    @GetMapping("/404")
    public String notFound(Model model) {
        model.addAttribute("compareRequest", new CompareRequest());
        return "redirect:/";
    }

    @GetMapping("/405")
    public String methodNotAllowed(Model model) {
        model.addAttribute("compareRequest", new CompareRequest());
        return "redirect:/";
    }
}