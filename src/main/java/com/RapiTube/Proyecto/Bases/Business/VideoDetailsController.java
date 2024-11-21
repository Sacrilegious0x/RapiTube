/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.VideoService;
import com.RapiTube.Proyecto.Bases.Domain.Comment;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
public class VideoDetailsController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/video/{id}")
    public String getVideoDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        // Obtiene el video completo por su ID
        Video video = videoService.getVideoById(id);

        if (video == null) {
            return "redirect:/"; // Redirige al inicio si no encuentra el video
        }
        // Registrar visualización del video
        User user = (User) session.getAttribute("loggedInUser"); // Obtener usuario de la sesión
        videoService.registerView(user, id);

        // Agrega el video y sus detalles al modelo
        model.addAttribute("video", video);

        // Obtener comentarios asociados al video
        List<Comment> comments = videoService.getCommentsByVideoId(id);
        model.addAttribute("comments", comments);

        // También obtén una lista de videos recomendados
        List<Video> recommendedVideos = videoService.getRecommendedVideos(id); //videoService.getRecommendedVideos(id);
        model.addAttribute("recommendedVideos", recommendedVideos);

        return "videoDetails"; // Vista para mostrar los detalles del video
    }

    @PostMapping("/video/like")
    public String likeVideo(@RequestParam("videoId") int videoId, RedirectAttributes redirectAttributes) {
        videoService.incrementLikes(videoId);
        redirectAttributes.addAttribute("id", videoId);
        return "redirect:/video/{id}";
    }

    @PostMapping("/video/dislike")
    public String dislikeVideo(@RequestParam("videoId") int videoId, RedirectAttributes redirectAttributes) {
        videoService.incrementDislikes(videoId);
        redirectAttributes.addAttribute("id", videoId);
        return "redirect:/video/{id}";
    }
}
