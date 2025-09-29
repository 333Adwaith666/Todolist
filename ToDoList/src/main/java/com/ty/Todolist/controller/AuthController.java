package com.ty.Todolist.controller;

import com.ty.Todolist.entity.User;
import com.ty.Todolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/auth/login";
    }

    @PostMapping("/login")
    public String loginUser(User user, Model model) {
        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            return "redirect:/todos";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
