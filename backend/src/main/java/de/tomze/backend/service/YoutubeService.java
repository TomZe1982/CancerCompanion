package de.tomze.backend.service;

import de.tomze.backend.api.YoutubeApiDto;
import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class YoutubeService {

    private final YoutubeClient youtubeClient;
    private final VideoRepository videoRepository;

    @Autowired
    public YoutubeService(YoutubeClient youtubeClient, VideoRepository videoRepository) {
        this.youtubeClient = youtubeClient;
        this.videoRepository = videoRepository;
    }


    public YoutubeApiDto getVideo(String id) {

     return youtubeClient.getVideo(id);
    }

    public List<VideoEntity> getVideoList() {
        return videoRepository.findAll();
    }

    public VideoEntity deleteVideo(String id) {
        List<VideoEntity> videoEntityList = videoRepository.findAll();
        VideoEntity videoEntityDelete = new VideoEntity();
       for(VideoEntity fetchedVideoEntity : videoEntityList){
          String videoId = fetchedVideoEntity.getVid_id();
           if(videoId.equals(id)){
               videoEntityDelete.setVid_id(videoId);
               videoRepository.delete(fetchedVideoEntity);
           }else{
               throw new EntityNotFoundException("No Entity with videoId found");
           }
       }
       return videoEntityDelete;
    }
}


