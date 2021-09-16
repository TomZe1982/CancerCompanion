package de.tomze.backend.service;

import de.tomze.backend.api.YoutubeApiDto;
import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.model.VideoList;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public YoutubeApiDto getVideo(String id) {

     return youtubeClient.getVideo(id);
    }

    public List<VideoEntity> getVideoList() {
        return videoRepository.findAll();
    }
}


