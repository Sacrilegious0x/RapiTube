/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Recommend;
import com.RapiTube.Proyecto.Bases.Domain.RecommendId;
import java.util.List;
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
public interface RecommendsRepository extends JpaRepository<Recommend, RecommendId> {
    @Query("SELECT r.id.idVideoRecomendado FROM Recommend r WHERE r.id.idVideo = :videoId")
    List<Integer> findRecommendedVideoIds(int videoId);
}
