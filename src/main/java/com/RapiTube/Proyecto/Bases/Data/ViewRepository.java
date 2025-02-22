/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.View;
import com.RapiTube.Proyecto.Bases.Domain.ViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Repository
public interface ViewRepository extends JpaRepository<View, ViewId> {

    @Modifying
    @Transactional
    @Query("DELETE FROM View v WHERE v.id.idVideo = :videoId")
    void deleteByVideoId(@Param("videoId") int videoId);

}
