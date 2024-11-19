/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
@Embeddable
public class CommentId implements Serializable {
    
   

    @Column(name = "ID_USUARIO")
    private int idUser;

    @Column(name = "ID_VIDEO")
    private int idVideo;
}
