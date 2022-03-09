package com.mp16.homemart.controller;

import com.mp16.homemart.dto.RegistrationRequestDTO;
import com.mp16.homemart.model.AppUserRole;
import com.mp16.homemart.model.User;
import com.mp16.homemart.service.AppUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
//@RequestMapping("/registration")
@AllArgsConstructor
public class UserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    private final AppUserService appUserService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    /*public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }*/

    @GetMapping("/register")
    public String viewRegister(@ModelAttribute RegistrationRequestDTO registrationRequestDTO, Model model){
        model.addAttribute("request", registrationRequestDTO);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationRequestDTO registrationRequestDTO, BindingResult bindingResult){
        log.info(">>> registration Request DTO : {}", registrationRequestDTO.toString());

        // check user if exists
        if (appUserService.userExists(registrationRequestDTO.getEmail())){
            bindingResult.addError(new FieldError(
                    "registrationRequestDTO",
                    "email",
                    "Email sudah ada yang punya"
            ));
        }

        // check password match
        if (registrationRequestDTO.getPassword() != null && registrationRequestDTO.getRpassword() != null){
            if (!registrationRequestDTO.getPassword().equals(registrationRequestDTO.getRpassword())){
                bindingResult.addError(new FieldError(
                        "registrationRequestDTO",
                        "rpassword",
                        "Password harus sama dengan konfirmasi password"
                ));
            }
        }

        if (bindingResult.hasErrors()){
            return "register";
        }

        //return registrationService.register(registrationRequestDTO);
        //return "redirect:/login";

        return appUserService.signUpUser(
                new User(
                        registrationRequestDTO.getName(),
                        registrationRequestDTO.getEmail(),
                        registrationRequestDTO.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}