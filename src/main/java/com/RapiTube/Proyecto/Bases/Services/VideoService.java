/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.RapiTube.Proyecto.Bases.Services;

import com.RapiTube.Proyecto.Bases.Data.CommentRepository;
import com.RapiTube.Proyecto.Bases.Data.RecommendsRepository;
import com.RapiTube.Proyecto.Bases.Data.TagRepository;
import com.RapiTube.Proyecto.Bases.Data.VideoRepository;
import com.RapiTube.Proyecto.Bases.Data.ViewRepository;
import com.RapiTube.Proyecto.Bases.Domain.Comment;
import com.RapiTube.Proyecto.Bases.Domain.Recommend;
import com.RapiTube.Proyecto.Bases.Domain.RecommendId;
import com.RapiTube.Proyecto.Bases.Domain.Tag;
import com.RapiTube.Proyecto.Bases.Domain.TagId;
import com.RapiTube.Proyecto.Bases.Domain.User;
import com.RapiTube.Proyecto.Bases.Domain.Video;
import com.RapiTube.Proyecto.Bases.Domain.View;
import com.RapiTube.Proyecto.Bases.Domain.ViewId;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

/**
 *
 * @author User
 */
@Service
public class VideoService {

    private final String videoUploadDir = "C:/VideosRP/";

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private RecommendsRepository recommendRepository;

    public Video getVideoById(int id) {
        return videoRepository.findById(id).orElse(null);
    }

    public Video getVideoByTitle(String title) {
        return videoRepository.findByTitle(title);
    }

    public List<Video> getAllVideos() {
       
        return videoRepository.findAll();
    }
    
    public List<Video> getMostViewVideos(){
        return videoRepository.findAllVideosOrderByViews();
    }
    // Obtener videos con más likes
    public List<Video> getMostLikedVideos() {
        return videoRepository.findTopVideosByLikes();
    }

    // Obtener videos más recientes
    public List<Video> getMostRecentVideos() {
        return videoRepository.findMostRecentVideos();
    }

    public List<Video> getUserVideos(User user) {
        return videoRepository.findByUserId(user.getId());
    }

    public void uploadVideoWithDuration(User user, MultipartFile videoFile, String title, String description) {
        // Verificar que el directorio de videos exista
        File directory = new File(videoUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Guardar el archivo de video en el servidor
        String videoFileName = null;
        if (!videoFile.isEmpty()) {
            try {
                videoFileName = UUID.randomUUID() + "_" + videoFile.getOriginalFilename();
                File file = new File(videoUploadDir + videoFileName);
                videoFile.transferTo(file); // Guardar el archivo en el servidor

                // Obtener la duración del video
                int[] duration = getVideoDuration(file);

                // Crear y guardar el objeto Video
                Video video = new Video();
                video.setUser(user);
                video.setTitle(title);
                video.setDescription(description);
                video.setVideoFile(videoFileName); // Guardar solo el nombre del archivo en la base de datos
                video.setMinutes(duration[0]);
                video.setSeconds(duration[1]);
                
                videoRepository.save(video);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al guardar el archivo de video");
            }
        }
    }

    public int[] getVideoDuration(File videoFile) {
        try {
            MultimediaObject multimediaObject = new MultimediaObject(videoFile);
            MultimediaInfo multimediaInfo = multimediaObject.getInfo();
            long durationInMilliseconds = multimediaInfo.getDuration();

            int minutes = (int) (durationInMilliseconds / 60000);
            int seconds = (int) ((durationInMilliseconds % 60000) / 1000);
            System.out.println("Video duration: " + minutes + " minutes, " + seconds + " seconds"); 
            return new int[]{minutes, seconds};
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("DEVOLVIO 0");
            return new int[]{0, 0};
        }
    }

    public void saveTags(int videoId, List<String> tags) {
        for (String tag : tags) {
            TagId tagId = new TagId();
            tagId.setIdVideo(videoId);
            tagId.setTag(tag);

            Tag tagVideo = new Tag();
            tagVideo.setId(tagId);

            tagRepository.save(tagVideo);

        }

        List<Integer> recommendedVideoIds = videoRepository.findVideoIdsByTags(tags);

        // Guardar en la tabla RECOMIENDA
        for (Integer recommendedId : recommendedVideoIds) {
            if (!recommendedId.equals(videoId)) {
                // Crear recomendación del video actual hacia el recomendado
                RecommendId recommendId1 = new RecommendId();
                recommendId1.setIdVideo(videoId);
                recommendId1.setIdVideoRecomendado(recommendedId);

                // Crear recomendación inversa (del video recomendado al video actual)
                RecommendId recommendId2 = new RecommendId();
                recommendId2.setIdVideo(recommendedId);
                recommendId2.setIdVideoRecomendado(videoId);

                // Guardar ambas recomendaciones solo si no existen
                if (!recommendRepository.existsById(recommendId1)) {
                    Recommend recommend1 = new Recommend();
                    recommend1.setId(recommendId1);
                    recommendRepository.save(recommend1);
                }
                if (!recommendRepository.existsById(recommendId2)) {
                    Recommend recommend2 = new Recommend();
                    recommend2.setId(recommendId2);
                    recommendRepository.save(recommend2);
                }
            }

        }
    }

    public void registerView(User user, int videoId) {
        int userId = (user != null) ? user.getId() : 0; // Si el usuario es null, usa 0 como ID

        ViewId viewid = new ViewId();
        viewid.setIdUser(userId);
        viewid.setIdVideo(videoId);
        viewid.setViewDate(LocalDateTime.now()); // Añadir la fecha de visualización

        View view = new View();
        view.setId(viewid);

        viewRepository.save(view);
    }

    public List<Comment> getCommentsByVideoId(int videoId) {
        return commentRepository.findByVideoIdOrderByCommentDateDesc(videoId);
    }

    public List<Video> getRecommendedVideos(int videoId) {
        List<Integer> recommendedVideoIds = recommendRepository.findRecommendedVideoIds(videoId);
        List<Video> recommendedVideos = new ArrayList<>(); // Lista de resultados

        for (Integer id : recommendedVideoIds) {
            // Intentamos encontrar el video por ID
            Video video = videoRepository.findById(id).orElse(null);

            if (video != null) {
                // Si el video existe, creamos un VideoDTO y lo añadimos a la lista
                recommendedVideos.add(video);
            }
        }

        // Devolvemos la lista de videos recomendados
        return recommendedVideos;

    }
    
    public int incrementLikes(int videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setLikes(video.getLikes() + 1);
        videoRepository.save(video);
        return video.getLikes();
    }

    public int incrementDislikes(int videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video not found"));
        video.setDislikes(video.getDislikes() + 1);
        videoRepository.save(video);
        return video.getDislikes();
    }
    
    public List<Video> getHistory(int idUser){
        return videoRepository.findWatchedVideosByUserId(idUser);
    }
    
    @Transactional
    public void deleteVideoById(int videoId) {
        // Elimina las relaciones en otras tablas primero
        tagRepository.deleteByVideoId(videoId);
        viewRepository.deleteByVideoId(videoId);
        recommendRepository.deleteByVideoId(videoId);
        recommendRepository.deleteByVideoRecomendadoId(videoId);
        commentRepository.deleteByVideoId(videoId);
        videoRepository.deleteById(videoId);
    }

}
