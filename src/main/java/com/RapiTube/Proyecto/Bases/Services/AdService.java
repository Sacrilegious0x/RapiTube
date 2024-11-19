/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Services;

import com.RapiTube.Proyecto.Bases.Data.AdvertisementRepository;
import com.RapiTube.Proyecto.Bases.Data.ViewAdRepository;
import com.RapiTube.Proyecto.Bases.Domain.Advertisement;
import com.RapiTube.Proyecto.Bases.Domain.ViewAd;
import com.RapiTube.Proyecto.Bases.Domain.ViewAdId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class AdService {
    @Autowired
    private AdvertisementRepository adRepository;
    
    @Autowired
    private ViewAdRepository viewAdRepository;

    // Método para guardar un anuncio
    public void saveAd(Advertisement ad) {
        adRepository.save(ad); 
    }
    
    public Advertisement getRandomAd(){
        return adRepository.findRandomAd();
    }
    
    public void registerViewAd(int idUser, int idAd){
        ViewAdId id = new ViewAdId();
        id.setIdUser(idUser);
        id.setIdAd(idAd);
        
        ViewAd viewAd = new ViewAd();
        viewAd.setId(id);
        
        viewAdRepository.save(viewAd);
    }
}
