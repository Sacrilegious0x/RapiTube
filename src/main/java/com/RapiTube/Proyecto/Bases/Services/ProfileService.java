/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Services;

import com.RapiTube.Proyecto.Bases.Data.ProfileRepository;
import com.RapiTube.Proyecto.Bases.Data.SubscribedRepository;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.User;
import java.util.ArrayList;
import java.util.List;
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
    
    @Autowired
    private SubscribedRepository subscribedRepository;

    public Optional<Profile> getProfileByUser(User user) {
        return profileRepository.findByUser(user);
    }
    
    public List<Profile> getFollowingUserProfiles(int userId) {
        // Obtener los usuarios que el usuario logueado sigue
        List<User> followingUsers = subscribedRepository.findFollowingByUserId(userId);

        // Crear una lista para almacenar los perfiles de esos usuarios
        List<Profile> profiles = new ArrayList<>();

        for (User user : followingUsers) {
            // Buscar el perfil para cada usuario seguido y añadirlo a la lista si existe
            profileRepository.findByUser(user).ifPresent(profiles::add);
        }

        return profiles;
    }

    public void updateProfile(Profile profile) {
       profileRepository.save(profile);
    }
}
