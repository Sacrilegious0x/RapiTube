/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Subscribed;
import com.RapiTube.Proyecto.Bases.Domain.SubscribedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author User
 */
public interface SubscribedRepository extends JpaRepository<Subscribed, SubscribedId>{
       boolean existsById(SubscribedId id);
       
       @Query(value = "SELECT COUNT(*) FROM SUSCRITO WHERE ID_SEGUIDO = :userId", nativeQuery = true)
       int countByIdSeguido(@Param("userId") int userId);
}
