/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Table (name = "COMENTARIO")
@Data
public class Comment {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Generado automáticamente por la base de datos
    @Column(name = "ID_COMENTARIO")
    private int idComment;
   @Embedded
    private CommentId commentId; // Clave compuesta (idComment, idUser, idVideo)

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_VIDEO", insertable = false, updatable = false)
    private Video video;

    @Column(name = "FECHA_COMENTARIO", nullable = false)
    private LocalDate commentDate;

    @Column(name = "TEXTO", nullable = false, length = 250)
    private String text;
}
