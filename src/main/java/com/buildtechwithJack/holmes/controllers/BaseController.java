package com.buildtechwithJack.holmes.controllers;

import com.buildtechwithJack.holmes.services.DiffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.buildtechwithJack.holmes.models.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.buildtechwithJack.holmes.utilities.InputUtil.processInput;

@Controller
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final DiffService diffService;

    public BaseController(DiffService diffService) {
        this.diffService = diffService;
    }

    @GetMapping("/")
    public ModelAndView getHome() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("compareRequest", new CompareRequest());
        mav.setViewName("index");
        return mav;
    }


    @PostMapping("/compare")
    public ModelAndView compare(@ModelAttribute CompareRequest request) {
        ModelAndView mav = new ModelAndView();
        try {
            String textA = processInput(request.getTextA(), request.getFileA());
            String textB = processInput(request.getTextB(), request.getFileB());

            request.setTextA(textA);
            request.setTextB(textB);

            CompareResult result = diffService.compare(
                    textA,
                    textB,
                    request.getMode(),
                    request.isIgnoreWhitespace(),
                    request.isIgnoreCase()
            );

            mav.addObject("compareRequest", request);
            mav.addObject("result", result);

        } catch (Exception e) {
            mav.addObject("error", "Error processing comparison: " + e.getMessage());
            mav.addObject("compareRequest", request);
        }

        mav.setViewName("index");
        return mav;
    }

}
