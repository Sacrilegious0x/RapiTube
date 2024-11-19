/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Domain;

import jakarta.persistence.Column;
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
@Data
@Table (name = "VIDEO")
public class Video {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VIDEO")
    private int  id; 
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private User user;
    
    @Column(name = "TITULO" ,nullable = true)
    private String title;
    
    @Column(name="DESCRIPCION", columnDefinition = "VARCHAR(MAX)")
    private String description; 
   
    @Column(name="LIKES")
    private int likes = 0;
    
    @Column(name="DISLIKES")
    private int dislikes = 0;
    
    @Column(name="MINUTOS")
    private int minutes;
    
    @Column(name="SEGUNDOS")
    private int seconds;
    
    @Column(name = "FECHA_SUBIDO")
    private LocalDate uploadDate = LocalDate.now();
    
    @Column(name = "ARCHIVO_VIDEO", length=255)
    private String videoFile;
}
