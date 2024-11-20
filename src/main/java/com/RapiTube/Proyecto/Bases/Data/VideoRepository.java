/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findByUserId(int userId);    

    Video findByTitle(String title);
    
    @Query("SELECT DISTINCT v.id FROM Video v JOIN Tag t ON v.id = t.id.idVideo WHERE t.id.tag IN :tags")
    List<Integer> findVideoIdsByTags(List<String> tags);
    
    @Query(value = """
            SELECT v.*, COUNT(vs.ID_VIDEO) AS cantidad_vistas 
            FROM VIDEO v
            JOIN VISUALIZACION vs ON v.ID_VIDEO = vs.ID_VIDEO
            GROUP BY  v.ID_VIDEO, v.TITULO, v.ID_USUARIO, v.LIKES, v.DISLIKES, v.MINUTOS, v.SEGUNDOS, v.DESCRIPCION, v.FECHA_SUBIDO, v.ARCHIVO_VIDEO 
            ORDER BY cantidad_vistas DESC
            """, nativeQuery = true)
    List<Video> findAllVideosOrderByViews();
    
    // Método para obtener videos con más likes
    @Query(value = "SELECT * FROM VIDEO ORDER BY LIKES DESC", nativeQuery = true)
    List<Video> findTopVideosByLikes();

    // Método para obtener los videos más recientes
    @Query(value = "SELECT * FROM VIDEO ORDER BY FECHA_SUBIDO DESC", nativeQuery = true)
    List<Video> findMostRecentVideos();
    
    @Query(value = """
        SELECT v.* 
        FROM VIDEO v
        JOIN (
            SELECT ID_VIDEO, MAX(FECHA_VISUALIZACION) AS FECHA_MAS_RECIENTE
            FROM VISUALIZACION
            WHERE ID_USUARIO = :userId
            GROUP BY ID_VIDEO
        ) vis ON v.ID_VIDEO = vis.ID_VIDEO
        ORDER BY vis.FECHA_MAS_RECIENTE DESC
        """, nativeQuery = true)
    List<Video> findWatchedVideosByUserId(@Param("userId") int userId);
}
