/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;


import com.RapiTube.Proyecto.Bases.Services.VideoService;
import com.RapiTube.Proyecto.Bases.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UploadVideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/uploadVideo")
    public String uploadVideoPage(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/userLogin";
        }

        return "uploadVideo";
    }

    @PostMapping("/uploadVideo")
    public String subirVideo(@RequestParam("video") MultipartFile videoFile,
            @RequestParam("titulo") String titulo,
            @RequestParam("description") String description, HttpSession session,
            Model model) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/userLogin";
        }

        // Intenta cargar el video y maneja errores en caso de falla
        try {
            videoService.uploadVideoWithDuration(user, videoFile, titulo, description);
            model.addAttribute("message", "Video subido correctamente");
            return "redirect:/addTags/" + titulo;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error al subir el video. Inténtalo de nuevo.");
            return "uploadVideo";
        }
    }

}
