/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.ViewAd;
import com.RapiTube.Proyecto.Bases.Domain.ViewAdId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public interface ViewAdRepository extends JpaRepository<ViewAd, ViewAdId> {
    
}