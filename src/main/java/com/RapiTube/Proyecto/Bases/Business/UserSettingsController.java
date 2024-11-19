/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author User
 */
@Controller
public class UserSettingsController {
    @GetMapping("/userSettings")
    public String settingsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            // Si el usuario no ha iniciado sesión, redirige a la página de registro
            return "register";
        } else {
            // Si el usuario ha iniciado sesión, muestra la página de configuración de perfil
            model.addAttribute("user", user);
            return "userSettings";
        }
    }
}
