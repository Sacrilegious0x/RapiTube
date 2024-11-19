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
import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Table(name = "USUARIO")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private int Id;
    
    @Column(name = "NOMBRE_USUARIO", nullable = false, unique = true, length=30)
    private String userName;
    
    @Column(name = "CORREO", nullable = false, unique = true, length = 30)
    private String email;
    
    @Column(name = "CONTRASENHA", nullable = false, length = 255)
    private String password;
    
    @Column(name = "FEChA_INGRESO", nullable = false)
    private LocalDate entryDate;
    
}
