/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Services;

import com.RapiTube.Proyecto.Bases.Data.PremiumUserRepository;
import com.RapiTube.Proyecto.Bases.Data.ProfileRepository;
import com.RapiTube.Proyecto.Bases.Data.RegularUserRepository;
import com.RapiTube.Proyecto.Bases.Data.SubscribedRepository;
import com.RapiTube.Proyecto.Bases.Data.UserRepository;
import com.RapiTube.Proyecto.Bases.Data.VideoRepository;
import com.RapiTube.Proyecto.Bases.Domain.PremiumUser;
import com.RapiTube.Proyecto.Bases.Domain.Profile;
import com.RapiTube.Proyecto.Bases.Domain.RegularUser;
import com.RapiTube.Proyecto.Bases.Domain.Subscribed;
import com.RapiTube.Proyecto.Bases.Domain.SubscribedId;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@Service
public class UserService {

    @Autowired //me ahorra el constructor de la clase y la inicializacion 
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PremiumUserRepository premiumUserRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;
    
    @Autowired
    private SubscribedRepository subscribedRepository;
    
    @Autowired
    private VideoRepository videoRepository;
    
    private final String imageUploadDir = "C:/FotosPerfilRP/";

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void createProfilewithType(User user, MultipartFile foto, String descripcion, boolean esPremium) {
        // Verificar que el directorio de imágenes exista
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Guardar la imagen de perfil en el servidor
        String fotoFileName = null;
        if (!foto.isEmpty()) {
            try {
                fotoFileName = UUID.randomUUID() + "_" + foto.getOriginalFilename();
                File file = new File(imageUploadDir + fotoFileName);
                foto.transferTo(file);  // Guardar el archivo en el servidor
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al guardar la imagen de perfil");
            }
        }

        // Crear y guardar el perfil del usuario
        Profile profile = new Profile();
        profile.setUser(user); // Asocia el perfil con el usuario (IMPORTANTE)
        profile.setPhoto(fotoFileName); // Guardar solo el nombre del archivo en la base de datos
        profile.setDescription(descripcion);
        profileRepository.save(profile);

        // Guardar el usuario en la tabla correspondiente según el tipo (premium o regular)
        if (esPremium) {
            PremiumUser usuarioPremium = new PremiumUser();
            usuarioPremium.setUser(user); // Asignar el usuario a PremiumUser
            usuarioPremium.setPremiumDate(LocalDate.now());
            premiumUserRepository.save(usuarioPremium);
        } else {
            RegularUser usuarioRegular = new RegularUser();
            usuarioRegular.setUser(user); // Asignar el usuario a RegularUser
            regularUserRepository.save(usuarioRegular);
        }
    }

    public boolean isPremium(User user) {
        return premiumUserRepository.existsById(user.getId());
    }

    public long countSubscribers(User user) {
        return subscribedRepository.countByIdSeguido(user.getId());
    }
    @Transactional
    public void convertToPremium(User user) {
         User managedUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!isPremium(managedUser)) {
            PremiumUser premiumUser = new PremiumUser();
            premiumUser.setUser(managedUser);
            premiumUser.setPremiumDate(LocalDate.now());
            premiumUserRepository.save(premiumUser);
            regularUserRepository.deleteById(managedUser.getId());
        }
    }
    public String getImageUploadDir() {
        return imageUploadDir;
    }
    
    public void subscribeUser(User currentUser, User targetUser) {
        SubscribedId suscritoId = new SubscribedId();
        suscritoId.setIdUser(currentUser.getId());
        suscritoId.setIdFollow(targetUser.getId());

        // Verifica si ya existe la suscripción antes de crear una nueva
        if (!subscribedRepository.existsById(suscritoId)) {
            Subscribed suscrito = new Subscribed();
            suscrito.setId(suscritoId);
            suscrito.setUser(currentUser);
            suscrito.setFollow(targetUser);
            subscribedRepository.save(suscrito);
        }
    }
    
    public List<User> getFollowingUsers(int userId) {
        return subscribedRepository.findFollowingByUserId(userId);
    }

    public void removeSubscription(int id, int followedUserId) {
        subscribedRepository.deleteByUserIdAndFollowedUserId(id, followedUserId);
    }

}
