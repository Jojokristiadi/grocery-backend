package com.mp16.homemart.controller;

import com.mp16.homemart.dto.CategoryDTO;
import com.mp16.homemart.dto.RegistrationRequestDTO;
import com.mp16.homemart.model.AppUserRole;
import com.mp16.homemart.model.Category;
import com.mp16.homemart.model.User;
import com.mp16.homemart.service.AppUserService;
import com.mp16.homemart.service.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RootController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    private final AppUserService appUserService;
    private final CategoryService categoryService;


    @GetMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("user", getCurrentAuth());
        return "home";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    /*
    * *********************************
    * Authentication
    * *********************************
    * */

    @GetMapping(value = "login")
    public String login(){
        User user = getCurrentAuth();
        if (user != null){
            return "redirect:/";
        }
        return "auth/login";
    }

    @GetMapping(value = "register")
    public String viewRegister(@ModelAttribute RegistrationRequestDTO registrationRequestDTO, Model model){
        if (getCurrentAuth() != null){
            return "redirect:/";
        }
        model.addAttribute("request", registrationRequestDTO);
        return "register";
    }

    @PostMapping("register")
    public String register(@Valid RegistrationRequestDTO registrationRequestDTO, BindingResult bindingResult){
        log.info(">>> registration Request DTO : {}", registrationRequestDTO.toString());

        // check user if exists
        if (appUserService.userExists(registrationRequestDTO.getEmail())){
            bindingResult.addError(new FieldError(
                    "registrationRequestDTO",
                    "email",
                    String.format("Email %s sudah ada yang punya", registrationRequestDTO.getEmail())
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

    private User getCurrentAuth(){
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User){
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info(">>> Auth user detected : {}", user.getName());
        }
        return user;
    }



    /*
     * *********************************
     * Product
     * *********************************
     * */
    @GetMapping(value = "product")
    public String viewProduct(Model model){
        return "product/product";
    }


    /*
     * *********************************
     * Category
     * *********************************
     * */

    @GetMapping(value = "category")
    public String viewCategory(Model model){
        model.addAttribute("categories", categoryService.getGetAllCategory());
        return "category/category";
    }

    @GetMapping(value = "view-new-category")
    public String viewNewCategory(@ModelAttribute CategoryDTO categoryDTO, Model model){
        model.addAttribute("request", categoryDTO);
        return "category/new_category";
    }

    @PostMapping(value = "new-category")
    public String addNewCategory(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, BindingResult result) {
        if(result.hasErrors()) {
            return "category/new_category";
        }
        categoryService.save(
                new Category(
                        categoryDTO.getName(),
                        categoryDTO.getParent()
                )
        );
        return "redirect:/category?addsuccess";
    }

    @GetMapping(value = "view-edit-category/{id}")
    public String viewEditCategory(@PathVariable (value = "id") long id, Model model){
        Category category = categoryService.get(id);
        model.addAttribute("categoryDTO", category);
        return "category/edit_category";
    }

    @PostMapping(value = "/edit-category")
    public String editCategory(@Valid @ModelAttribute("categoryDTO") Category category, BindingResult result) {
        if(result.hasErrors()) {
            return "category/edit_category";
        }
        categoryService.save(category);
        return "redirect:/category?editsuccess";
    }

    @RequestMapping(value = "/delete-category/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id /*RedirectAttributes redirectAttributes*/) {
        categoryService.delete(id);
        /*redirectAttributes.addFlashAttribute("swal_flag", "confirmDelete");*/
        return "redirect:/category?deletesuccess";
    }


    /*
     * *********************************
     * User
     * *********************************
     * */
    @GetMapping(value = "user")
    public String viewUser(Model model){
        return "user/user";
    }
}
