/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import com.RapiTube.Proyecto.Bases.Services.VideoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author User
 */
@Controller
public class HistoryController {
    @Autowired
    private VideoService videoService;
    
    @GetMapping("/history")
    public String showHistory(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        
        if (user == null) {
            return "redirect:/userLogin";
        }

        List<Video> watchedVideos = videoService.getHistory(user.getId());
         System.out.println(watchedVideos);
        model.addAttribute("watchedVideos", watchedVideos);

        return "history";
    }
}
