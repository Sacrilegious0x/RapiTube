/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.VideoService;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class TagsController {

    @Autowired
    private VideoService videoService;
    
    private static final List<String> predefinedTags = Arrays.asList(
            "ENTRETENIMIENTO", "MUSICA", "CIENCIA", "INFORMATICA", "VLOG", 
            "DEPORTE", "GAMING", "NOTICIAS", "INFORMACION"
    );
    
    @GetMapping("/addTags/{titulo}")
    public String showAddTagsForm(@PathVariable("titulo") String titulo, Model model) {
        model.addAttribute("titulo", titulo);
        model.addAttribute("predefinedTags", predefinedTags);
        return "addTags";
    }

   @PostMapping("/saveTags")
    public String saveTags(@RequestParam("titulo") String titulo,
                           @RequestParam("predefinedTag") String predefinedTag,
                           @RequestParam(value = "tags", required = false) String tags, 
                           Model model) {
        // Obtener el video por el título
        Video video = videoService.getVideoByTitle(titulo);
        if (video == null) {
            model.addAttribute("message", "Video no encontrado");
            return "redirect:/error";
        }

        // Crear lista de tags que incluirá el tag predefinido y adicionales si existen
        List<String> tagList = new ArrayList<>();
        tagList.add(predefinedTag); // Agregar el tag predefinido obligatorio

        // Agregar los tags adicionales si el usuario ingresó alguno
        if (tags != null && !tags.isEmpty()) {
            tagList.addAll(Arrays.asList(tags.split(",\\s*")));
        }

        // Guardar los tags en el servicio
        videoService.saveTags(video.getId(), tagList);

        return "redirect:/profile";
    }

}
