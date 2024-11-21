/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Tag;
import com.RapiTube.Proyecto.Bases.Domain.TagId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface TagRepository extends JpaRepository<Tag, TagId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Tag t WHERE t.id.idVideo = :videoId")
    void deleteByVideoId(@Param("videoId") int videoId);
}
