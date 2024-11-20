/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Services.ProfileService;
import com.RapiTube.Proyecto.Bases.Services.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author User
 */
@Controller
public class SubscriptionsController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;
    
    @GetMapping("/subscriptions")
    public String showFollowingUsers(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/userLogin";
        }
        
         List<Profile> followingProfiles = profileService.getFollowingUserProfiles(user.getId());

        model.addAttribute("followingProfiles", followingProfiles);
        return "subscriptions";
    }

    @PostMapping("/unsubscribe/{id}")
    public String unsubscribe(@PathVariable("id") Integer followedUserId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            userService.removeSubscription(user.getId(), followedUserId);
        }
        return "redirect:/subscriptions"; // Redirige a la lista de usuarios seguidos
    }
}
