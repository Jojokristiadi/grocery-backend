package com.mp16.homemart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {
    
    @RequestMapping("/2nd")
    public String secondSubPage() {
        return "test/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("message", "Ahoy there");
        return mav;
    }
}
