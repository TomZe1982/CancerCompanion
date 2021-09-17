package de.tomze.backend.service;

import de.tomze.backend.api.YoutubeApiDto;
import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class YoutubeService {

    private final YoutubeClient youtubeClient;
    private final VideoRepository videoRepository;

    @Autowired
    public YoutubeService(YoutubeClient youtubeClient, VideoRepository videoRepository) {
        this.youtubeClient = youtubeClient;
        this.videoRepository = videoRepository;
    }


    public YoutubeApiDto getVideo(String vid_id) {

     return youtubeClient.getVideo(vid_id);
    }

    public List<VideoEntity> getVideoList() {
        return videoRepository.findAll();
    }

    public VideoEntity deleteVideo(String vid_id) {
        List<VideoEntity> videoEntityList = videoRepository.findAll();
        VideoEntity videoEntityDelete = new VideoEntity();

       for(VideoEntity fetchedVideoEntity : videoEntityList){
          String videoId = fetchedVideoEntity.getVid_id();
          videoEntityDelete.setVid_id(vid_id);
           if(videoId.equals(vid_id)) {
               videoRepository.delete(fetchedVideoEntity);
               return fetchedVideoEntity;
           }
       }
       return videoEntityDelete;
    }
}


