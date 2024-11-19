/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.UserService;
import com.RapiTube.Proyecto.Bases.Services.ProfileService;
import com.RapiTube.Proyecto.Bases.Data.SubscribedRepository;
import com.RapiTube.Proyecto.Bases.Data.UserRepository;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.SubscribedId;
import com.RapiTube.Proyecto.Bases.Domain.User;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
public class PublicProfileController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private SubscribedRepository subscribedRepository;

 

    @GetMapping("/publicProfile/{userName}")
    public String viewPublicProfile(@PathVariable("userName") String userName, Model model, HttpSession session) {
        // Buscar el usuario objetivo en la base de datos
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        if (optionalUser.isEmpty()) {
            return "redirect:/"; // Redirige al inicio si el usuario no existe
        }

        User targetUser = optionalUser.get();
        model.addAttribute("user", targetUser);

        // Obtener foto de perfil y suscriptores
        Optional<Profile> profileOpt = profileService.getProfileByUser(targetUser);
        profileOpt.ifPresent(profile -> model.addAttribute("profileImageUrl", "/FotosPerfilRP/" + profile.getPhoto()));
        model.addAttribute("suscriptores", userService.countSubscribers(targetUser));

        // Verifica si el usuario actual está logeado
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser == null) {
            model.addAttribute("isSubscribed", false); // Si no está logeado, no puede estar suscrito
        } else if(currentUser.getId()==targetUser.getId()){
            return "redirect:/profile";
            
         }else {
            // Si el usuario está logeado, verifica si ya está suscrito al usuario objetivo
            SubscribedId subId = new SubscribedId();
            subId.setIdUser(currentUser.getId());
            subId.setIdFollow(targetUser.getId());
            boolean isSubscribed = subscribedRepository.existsById(subId);
            model.addAttribute("isSubscribed", isSubscribed);
        }

        return "publicProfile";
    }

    @PostMapping("/subscribeToUser/{id}")
    public String subscribeToUser(@PathVariable("id") int targetUserId, HttpSession session, RedirectAttributes redirectAttributes) {
        // Verificar que el usuario esté logeado
        User currentUser = (User) session.getAttribute("loggedInUser");
        Optional<User> optionalUser = userRepository.findById(targetUserId);
        User targetUser = optionalUser.get();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("message", "Debes iniciar sesión para suscribirte a este usuario.");
            return "redirect:/userLogin";
        }
        
        // Realizar suscripción
        userService.subscribeUser(currentUser, targetUser);
        redirectAttributes.addFlashAttribute("message", "Te has suscrito exitosamente.");
        return "redirect:/publicProfile/" + targetUserId;
    }
}
