package com.mp16.homemart.controller;

import com.mp16.homemart.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);
    @RequestMapping("/2nd")
    public String secondSubPage() {
        return "test/list";
    }

    @GetMapping("/login")
    public String login(){
        User user = getCurrentAuth();
        if (user != null){
            return "/";
        }
        return "login";
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", getCurrentAuth());
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("message", "Ahoy there");
        return "home";
    }

    private User getCurrentAuth(){
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info(">>> Auth user detected : {}", user.getName());
        }
        return user;
    }

    /*@GetMapping("/authenticated")
    public String authenticated(Model model){
        model.addAttribute("user", getClass());
    }*/
}
