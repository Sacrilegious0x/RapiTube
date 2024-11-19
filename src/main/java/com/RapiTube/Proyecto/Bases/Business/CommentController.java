/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Business;

import com.RapiTube.Proyecto.Bases.Services.VideoService;
import com.RapiTube.Proyecto.Bases.Data.CommentRepository;
import com.RapiTube.Proyecto.Bases.Domain.Comment;
import com.RapiTube.Proyecto.Bases.Domain.CommentId;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author User
 */
@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoService videoService;

    @PostMapping("/video/{videoId}/comment")
    public String addComment(@PathVariable("videoId") int videoId,
            @RequestParam("commentText") String commentText,
            HttpSession session,
            Model model) {

        // Verificar si el usuario está logueado
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/userLogin";
        }

        Video video = videoService.getVideoById(videoId);
        if (video == null) {
            return "redirect:/"; // Redirige al inicio si el video no existe
        }

        // Crear y guardar el comentario
        Comment comment = new Comment();
        CommentId commentId = new CommentId();
        commentId.setIdUser(user.getId());
        commentId.setIdVideo(videoId);
        
        comment.setCommentId(commentId);
        comment.setUser(user);
        comment.setVideo(video);
        comment.setText(commentText);
        comment.setCommentDate(LocalDate.now());

        // Guardar el comentario y generar automáticamente el idComment
        commentRepository.save(comment);

        // Redirigir de nuevo a la página del video para ver el comentario
        return "redirect:/video/" + videoId;
    }

}
