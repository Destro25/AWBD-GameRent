package com.awbd.gamerent.controller;

import com.awbd.gamerent.model.User;
import com.awbd.gamerent.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        User user = new User();
        user.setUserProfile(new com.awbd.gamerent.model.UserProfile());
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-form";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        if (user.getUserProfile() == null) {
            user.setUserProfile(new com.awbd.gamerent.model.UserProfile());
        }
        model.addAttribute("user", user);
        return "user-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}