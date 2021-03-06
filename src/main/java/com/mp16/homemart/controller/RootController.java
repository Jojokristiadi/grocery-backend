package com.mp16.homemart.controller;

import com.mp16.homemart.dto.CategoryDTO;
import com.mp16.homemart.dto.ProductDTO;
import com.mp16.homemart.dto.RegistrationRequestDTO;
import com.mp16.homemart.model.AppUserRole;
import com.mp16.homemart.model.Category;
import com.mp16.homemart.model.Product;
import com.mp16.homemart.model.User;
import com.mp16.homemart.repository.CategoryRepository;
import com.mp16.homemart.service.AppUserService;
import com.mp16.homemart.service.CategoryService;
import com.mp16.homemart.service.ProductService;
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
    private final ProductService productService;


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
        if (getCurrentAuth() != null){
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

        if (validatingUser(registrationRequestDTO, bindingResult).hasErrors() || bindingResult.hasErrors()) {
            return "register";
        }

        return appUserService.signUpUser(
                new User(
                        registrationRequestDTO.getName(),
                        registrationRequestDTO.getEmail(),
                        registrationRequestDTO.getPassword(),
                        "USER"
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
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }

    @GetMapping(value = "view-new-product")
    public String viewNewProduct(@ModelAttribute Product productDTO, Model model){
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getGetAllCategory());
        return "product/new_product";
    }

    @PostMapping(value = "new-product")
    public String addNewProduct(@Valid @ModelAttribute("productDTO") Product product, BindingResult result, Model model){
        model.addAttribute("user", getCurrentAuth());
        if(result.hasErrors()) {
            model.addAttribute("categories", categoryService.getGetAllCategory());
            return "product/new_product";
        }
        productService.save(product);
        return "redirect:/product?addsuccess";
    }

    @GetMapping(value = "view-edit-product/{id}")
    public String viewEditProduct(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("user", getCurrentAuth());
        Product product = productService.get(id);
        model.addAttribute("categories", categoryService.getGetAllCategory());
        model.addAttribute("productDTO", product);
        return "product/edit_product";
    }

    @PostMapping(value = "/edit-product")
    public String editProduct(@Valid @ModelAttribute("productDTO") Product product, BindingResult result, Model model) {
        model.addAttribute("user", getCurrentAuth());
        if(result.hasErrors()) {
            model.addAttribute("categories", categoryService.getGetAllCategory());
            return "product/edit_product";
        }
        productService.save(product);
        return "redirect:/product?editsuccess";
    }

    @RequestMapping(value = "/delete-product/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        productService.delete(id);
        return "redirect:/product?deletesuccess";
    }

    /*
     * *********************************
     * Category
     * *********************************
     * */

    @GetMapping(value = "category")
    public String viewCategory(Model model){
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("categories", categoryService.getGetAllCategory());
        return "category/category";
    }

    @GetMapping(value = "view-new-category")
    public String viewNewCategory(@ModelAttribute CategoryDTO categoryDTO, Model model){
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("request", categoryDTO);
        return "category/new_category";
    }

    @PostMapping(value = "new-category")
    public String addNewCategory(@Valid @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, BindingResult result, Model model) {
        model.addAttribute("user", getCurrentAuth());
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
        model.addAttribute("user", getCurrentAuth());
        Category category = categoryService.get(id);
        model.addAttribute("categoryDTO", category);
        return "category/edit_category";
    }

    @PostMapping(value = "/edit-category")
    public String editCategory(@Valid @ModelAttribute("categoryDTO") Category category, BindingResult result, Model model) {
        model.addAttribute("user", getCurrentAuth());
        if(result.hasErrors()) {
            return "category/edit_category";
        }
        categoryService.save(category);
        return "redirect:/category?editsuccess";
    }

    @RequestMapping(value = "/delete-category/{id}")
    public String deleteCategory(@PathVariable(name = "id") int id) {
        categoryService.delete(id);
        return "redirect:/category?deletesuccess";
    }




    /*
     * *********************************
     * User
     * *********************************
     * */
    @GetMapping(value = "user")
    public String viewUser(Model model){
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("users", appUserService.getAllUser());
        return "user/user";
    }

    @GetMapping(value = "view-new-user")
    public String viewNewUser(@ModelAttribute User userDTO, Model model){
        model.addAttribute("user", getCurrentAuth());
        model.addAttribute("userDTO", userDTO);
        return "user/new_user";
    }

    @PostMapping(value = "new-user")
    public String addNewUser(@Valid @ModelAttribute("userDTO") User userDTO, BindingResult result, Model model) {
        model.addAttribute("user", getCurrentAuth());
        if (validatingUser(userDTO, result).hasErrors() || result.hasErrors()) {
            return "user/new_user";
        }
        appUserService.save(userDTO);
        return "redirect:/user?addsuccess";
    }

    @GetMapping(value = "view-edit-user/{id}")
    public String viewEditUser(@PathVariable (value = "id") long id, Model model){
        model.addAttribute("user", getCurrentAuth());
        User user = appUserService.get(id);
        model.addAttribute("userDTO", user);
        return "user/edit_user";
    }

    @PostMapping(value = "/edit-user")
    public String editUser(@Valid @ModelAttribute("userDTO") User userDTO, BindingResult result, Model model) {
        model.addAttribute("user", getCurrentAuth());
        if (validatingUser(userDTO, result).hasErrors() || result.hasErrors()) {
            return "user/edit_user";
        }
        appUserService.save(userDTO);
        return "redirect:/user?editsuccess";
    }

    @RequestMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        appUserService.delete(id);
        return "redirect:/user?deletesuccess";
    }

    private BindingResult validatingUser(User user, BindingResult result){
        // check user if exists
        if (appUserService.userExists(user.getEmail())){
            result.addError(new FieldError(
                    "userDTO",
                    "email",
                    String.format("Email %s sudah ada yang punya", user.getEmail())
            ));
        }

        // check password match
        if (user.getPassword() != null && user.getRpassword() != null){
            if (!user.getPassword().equals(user.getRpassword())){
                result.addError(new FieldError(
                        "userDTO",
                        "rpassword",
                        "Password harus sama dengan konfirmasi password"
                ));
            }
        }
        return result;
    }


    private BindingResult validatingUser(RegistrationRequestDTO user, BindingResult result){
        // check user if exists
        if (appUserService.userExists(user.getEmail())){
            result.addError(new FieldError(
                    "registrationRequestDTO",
                    "email",
                    String.format("Email %s sudah ada yang punya", user.getEmail())
            ));
        }

        // check password match
        if (user.getPassword() != null && user.getRpassword() != null){
            if (!user.getPassword().equals(user.getRpassword())){
                result.addError(new FieldError(
                        "registrationRequestDTO",
                        "rpassword",
                        "Password harus sama dengan konfirmasi password"
                ));
            }
        }
        return result;
    }
}
