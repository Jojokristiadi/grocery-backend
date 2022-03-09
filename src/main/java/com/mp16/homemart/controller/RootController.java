package com.mp16.homemart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("message", "Ahoy there");
        return mav;
    }
    
    @RequestMapping("/2nd")
    public String secondSubPage() {
        return "test/list";
    }
}
