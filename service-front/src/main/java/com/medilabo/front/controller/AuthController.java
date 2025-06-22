package com.medilabo.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String loginView(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model
    ) {
        model.addAttribute("pageTitle", "Connexion");

        if (error != null) {
            model.addAttribute("errorMessage", "Nom d’utilisateur ou mot de passe incorrect.");
        }
        else if (logout != null) {
            model.addAttribute("logoutMessage", "Vous vous êtes déconnecté avec succès.");
        }

        return "auth/login";
    }

    @GetMapping("/")
    public String indexView(Model model) {
        model.addAttribute("pageTitle", "Acceuil");
        return "index";
    }
}
