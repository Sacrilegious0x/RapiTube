/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.UserService;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@Controller
public class CreateProfileController {
    @Autowired
    private UserService userService;

   @GetMapping("/createProfile")
    public String mostrarFormularioPerfil(@RequestParam("username") String username, Model model) {
        // Agrega el nombre de usuario al modelo para mostrarlo en la vista
        model.addAttribute("username", username);
        model.addAttribute("profile", new Profile());
        return "createProfile";
    }

    @PostMapping("/createProfile")
    public String guardarNuevoPerfil(@RequestParam("username") String username,
                                     @RequestParam("descripcion") String description,
                                     @RequestParam(value = "premium", required = false) Boolean isPremium,
                                     @RequestParam("foto") MultipartFile photo,
                                     Model model) {
        
        // Si el checkbox no está seleccionado, `esPremium` será `null`
    if (isPremium == null) {
        isPremium = false;  // Asigna `false` si el usuario no ha marcado la casilla
    }

        // Obtener el usuario registrado
        User user = userService.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Llamar al servicio para crear el perfil y asignar el tipo de usuario
        userService.createProfilewithType(user, photo, description, isPremium);

        // Redirigir a la página principal o de bienvenida después de guardar el perfil
        return "redirect:/";
    }
}
