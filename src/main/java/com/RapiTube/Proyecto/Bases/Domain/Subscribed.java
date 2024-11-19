/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Table(name = "SUSCRITO")
@Data
public class Subscribed {
    
    @EmbeddedId
    private SubscribedId id;
    
    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @MapsId("idFollow")
    @JoinColumn(name = "ID_SEGUIDO", insertable = false, updatable = false)
    private User follow;
}
