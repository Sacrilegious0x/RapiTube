/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.Advertisement;
import com.RapiTube.Proyecto.Bases.Services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
public class AdController {
     @Autowired
    private AdService adService;

    // Muestra el formulario para subir un anuncio
    @GetMapping("/uploadAd")
    public String showAdUploadForm() {
        return "uploadAd"; // Renderiza el HTML para subir anuncios
    }

    // Procesa el formulario de subida de anuncios
    @PostMapping("/submitAd")
    public String submitAd(
            @RequestParam("marca") String brand,
            @RequestParam("descripcion") String description,
            RedirectAttributes redirectAttributes
    ) {
        // Crea y guarda el anuncio
        try {
            Advertisement ad = new Advertisement();
            ad.setBrand(brand);
            ad.setDescription(description);

            adService.saveAd(ad);
            redirectAttributes.addFlashAttribute("message", "¡Anuncio subido exitosamente!");

            return "redirect:/"; // Redirige a la misma página con un mensaje de éxito
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al subir el anuncio. Intenta nuevamente.");
            return "redirect:/uploadAd"; // Redirige con mensaje de error
        }
    }
    
}
