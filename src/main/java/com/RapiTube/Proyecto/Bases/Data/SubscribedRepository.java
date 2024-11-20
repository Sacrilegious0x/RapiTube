/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Data;

import com.RapiTube.Proyecto.Bases.Domain.Subscribed;
import com.RapiTube.Proyecto.Bases.Domain.SubscribedId;
import com.RapiTube.Proyecto.Bases.Domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface SubscribedRepository extends JpaRepository<Subscribed, SubscribedId> {

    boolean existsById(SubscribedId id);

    @Query(value = "SELECT COUNT(*) FROM SUSCRITO WHERE ID_SEGUIDO = :userId", nativeQuery = true)
    int countByIdSeguido(@Param("userId") int userId);

    @Query("SELECT u FROM User u JOIN Subscribed s ON u.id = s.id.idFollow WHERE s.id.idUser = :userId")
    List<User> findFollowingByUserId(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Subscribed s WHERE s.id.idUser = :userId AND s.id.idFollow = :followedUserId")
    void deleteByUserIdAndFollowedUserId(@Param("userId") int userId, @Param("followedUserId") int followedUserId);
}
