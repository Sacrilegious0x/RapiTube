/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import com.RapiTube.Proyecto.Bases.Services.ProfileService;
import com.RapiTube.Proyecto.Bases.Services.UserService;
import com.RapiTube.Proyecto.Bases.Services.VideoService;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@Controller
public class UserSettingsController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @GetMapping("/userSettings")
    public String settingsPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            // Si el usuario no ha iniciado sesión, redirige a la página de registro
            return "register";
        } else {
            // Si el usuario ha iniciado sesión, muestra la página de configuración de perfil
            model.addAttribute("user", user);
            return "userSettings";
        }
    }

    @GetMapping("/updateProfile")
    public String updateProfileForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        profileService.getProfileByUser(user).ifPresent(profile -> model.addAttribute("profile", profile));
        return "updateProfile";
    }

    // Mostrar el formulario de actualización de perfil
    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("photo") MultipartFile photoFile,
            @RequestParam("description") String description,
            HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            Optional<Profile> profileOpt = profileService.getProfileByUser(user);

            if (profileOpt.isPresent()) {
                Profile profile = profileOpt.get();
                profile.setDescription(description);

                if (!photoFile.isEmpty()) {
                    try {
                        String filePath = saveProfileImage(photoFile);
                        profile.setPhoto(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                profileService.updateProfile(profile);
            }
        }
        return "redirect:/profile";
    }
    
    @GetMapping("/deleteVideos")
    public String viewUserVideos(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Video> userVideos = videoService.getUserVideos(user);
        model.addAttribute("userVideos", userVideos);
        
        return "deleteVideos";
    }

    @PostMapping("/deleteVideo/{id}")
    public String deleteVideo(@PathVariable("id") int videoId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            videoService.deleteVideoById(videoId);
        }
        return "redirect:/deleteVideos";
    }

    private String saveProfileImage(MultipartFile photoFile) throws IOException {
        String fotoFileName = null;

        if (!photoFile.isEmpty()) {
            fotoFileName = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
            File uploadDir = new File(userService.getImageUploadDir());

            // Asegúrate de que la carpeta existe
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File file = new File(uploadDir, fotoFileName);

            try {
                photoFile.transferTo(file);  // Guardar el archivo en el servidor
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al guardar la imagen de perfil");
            }

            // Retornar la URL relativa para acceder a la imagen
            return  fotoFileName;
        }

        // Si el archivo está vacío, retorna null o una URL por defecto
        return null;
    }
    
    
}
