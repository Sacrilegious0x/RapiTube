/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Services;

import com.RapiTube.Proyecto.Bases.Data.ProfileRepository;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Optional<Profile> getProfileByUser(User user) {
        return profileRepository.findByUser(user);
    }
}
