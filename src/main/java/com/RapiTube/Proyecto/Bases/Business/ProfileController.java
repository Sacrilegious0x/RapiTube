/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.VideoService;
import com.RapiTube.Proyecto.Bases.Services.UserService;
import com.RapiTube.Proyecto.Bases.Services.ProfileService;
import com.RapiTube.Proyecto.Bases.Data.UserRepository;
import com.RapiTube.Proyecto.Bases.Domain.Advertisement;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Services.AdService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {
    @Autowired
    private UserRepository userRespository;
   @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private VideoService videoService;
    
    @Autowired
    private AdService adService;
    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "profile"; 
        }

        // Obtener perfil y verificar si el usuario es premium
        Optional<Profile> profileOpt = profileService.getProfileByUser(user);
        boolean isPremium = userService.isPremium(user);

        model.addAttribute("user", user);
        model.addAttribute("isPremium", isPremium);
        model.addAttribute("suscriptores", userService.countSubscribers(user));
        model.addAttribute("videos", videoService.getUserVideos(user));

        // Asigna la URL de la foto de perfil y la descripción si existen
        profileOpt.ifPresent(profile -> {
            model.addAttribute("profileImageUrl","/FotosPerfilRP/"+ profile.getPhoto());
            model.addAttribute("description", profile.getDescription());
        });
        if(!isPremium){
            Advertisement ad = adService.getRandomAd();
            model.addAttribute("ad", ad);
            adService.registerViewAd(user.getId(), ad.getIdAdv());
        }
        return "profile"; // Retorna la vista del perfil
    }

    @PostMapping("/convertToPremium")
    public String convertToPremium(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        
        if (user == null) {
            return "redirect:/userLogin";
        }

        userService.convertToPremium(user); // Llama a tu lógica para cambiar al usuario a premium
        redirectAttributes.addFlashAttribute("message", "¡Ahora eres un usuario premium!");

        return "redirect:/profile";
    }

    
}
