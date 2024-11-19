package com.RapiTube.Proyecto.Bases.Data;


import com.RapiTube.Proyecto.Bases.Domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author User
 */
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {
    @Query(value = "SELECT TOP 1 * FROM ANUNCIO ORDER BY NEWID()", nativeQuery = true)
    Advertisement findRandomAd();
}
