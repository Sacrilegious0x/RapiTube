/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.UserService;
import com.RapiTube.Proyecto.Bases.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class LoginController {

  @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Página de inicio de sesión
    @GetMapping("/userLogin")
    public String loginPage() {
        return "userLogin";  // Renderiza el formulario de inicio de sesión (userLogin.html)
    }

    @PostMapping("/userLogin")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model,
                            HttpSession session) {
        // Buscar el usuario por nombre
        User user = userService.findByUserName(username).orElse(null);

        // Verificar si el usuario existe y la contraseña es correcta
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/profile";  // Redirige al perfil si las credenciales son correctas
        } else {
            model.addAttribute("error", true);  // Agrega un mensaje de error si las credenciales no coinciden
            return "userLogin";  // Vuelve a cargar la página de inicio de sesión con un mensaje de error
        }
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalida la sesión actual para cerrar la sesión del usuario
        return "redirect:/";  // Redirige a la página de inicio de sesión
    }
}
