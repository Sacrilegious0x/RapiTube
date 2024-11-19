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
@Embeddable
@Data
public class RecommendId implements Serializable{
    @Column(name = "ID_VIDEO")
    private int idVideo;

    @Column(name = "ID_VIDEO_RECOMENDADO")
    private int idVideoRecomendado;
}