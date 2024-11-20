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
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Table(name = "ANUNCIO", schema = "dbo")
@Data
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ANUNCIO")
    private int idAdv;
    
    @Column(name = "MARCA", length =30)
    private String brand;
    
    @Column(name ="DESCRIPCION", length = 250)
    private String description;
}
