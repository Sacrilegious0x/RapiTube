/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
@Entity
@Table(name = "USUARIO_REGULAR")
public class RegularUser {
    @Id
    @Column(name = "ID_USUARIO")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_USUARIO")
    private User user;

}
