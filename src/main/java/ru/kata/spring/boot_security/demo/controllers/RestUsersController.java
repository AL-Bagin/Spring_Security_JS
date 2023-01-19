package ru.kata.spring.boot_security.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.CrudService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUsersController {

    private CrudService crudService;
    private UserService userService;

    private UserValidator userValidator;

    public RestUsersController(CrudService crudService, UserService userService, UserValidator userValidator) {
        this.crudService = crudService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @Autowired


    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = crudService.index();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = crudService.show(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        crudService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/admin/users/{id}")
    public ResponseEntity<Void> editUser (@PathVariable("id") Long id, @RequestBody User user) {
        crudService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        crudService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Маппинг для навбара
    @GetMapping("/auth")
    public ResponseEntity<User> index(Principal principal) {
        User user = userService.findByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
