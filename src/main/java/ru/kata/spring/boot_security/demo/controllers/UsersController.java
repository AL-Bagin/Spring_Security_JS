package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.CrudService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
public class UsersController {

    private CrudService crudService;

    private UserService userService;

    private UserValidator userValidator;

    @Autowired
    public UsersController(CrudService crudService, UserService userService,
                           UserValidator userValidator) {
        this.crudService = crudService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String main() {
        return "/main";
    }
    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("allUsers", crudService.index());
        return "/index";
    }

    @GetMapping ("/admin/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = crudService.indexRoles();
        model.addAttribute("allRoles", roles);
        return "/new";
    }

    @PostMapping("/admin")
    public String create(Model model, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Role> roles = crudService.indexRoles();
            model.addAttribute("allRoles", roles);
            return "/new";
        }
        crudService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") long id) {
        crudService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", crudService.show(id));
        return "/edit";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        crudService.update(id, user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String main(Principal principal, Model model) {
        User user = userService.findByName(principal.getName());
        model.addAttribute("user", user);
        return "/userInfo";
    }
}
