/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.Video;
import com.RapiTube.Proyecto.Bases.Services.VideoService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class HomeController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/")
    public String index(@RequestParam(name = "filter", required = false) String filter, Model model) {

        List<Video> videos;

        if (filter == null || filter.isEmpty()) {
            filter = "all";
        } 
            switch (filter) {
                case "mostLiked":
                    videos = videoService.getMostLikedVideos();
                    break;
                case "recent":
                    videos = videoService.getMostRecentVideos();
                    break;
                case "mostViewed":
                    videos = videoService.getMostViewVideos();
                    break;
                    
                case "all":
                    videos = videoService.getAllVideos();
                    break;
                default:
                    videos = videoService.getAllVideos();
                    break;
            }
        

        model.addAttribute("videos", videos);
        model.addAttribute("filter", filter); // Pasar el filtro activo para la pestaña activa
        return "index";
    }

}
