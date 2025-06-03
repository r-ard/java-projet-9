package com.medilabo.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("pageTitle", "Connexion");
        return "auth/login";
    }
}
