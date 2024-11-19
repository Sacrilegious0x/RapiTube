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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author User
 */
@Entity
@Getter
@Setter
@Table (name = "PERFIL")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERFIL")
    private int id;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "ID_USUARIO", nullable = false, referencedColumnName ="ID_USUARIO")
    private User user;
    
    @Column(name = "FOTO")
    private String photo;
    
    @Column(name = "DESCRIPCION", length = 250)
    private String description;
}
