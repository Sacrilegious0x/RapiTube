/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Comment;
import com.RapiTube.Proyecto.Bases.Domain.CommentId;
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
public interface CommentRepository extends JpaRepository<Comment, CommentId> {

    List<Comment> findByVideoIdOrderByCommentDateDesc(int videoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.commentId.idVideo = :videoId")
    void deleteByVideoId(@Param("videoId") int videoId);

}
