package de.tomze.backend.service;

import de.tomze.backend.api.YoutubeToApiDto;
import de.tomze.backend.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

    private final YoutubeClient youtubeClient;

    @Autowired
    public YoutubeService(YoutubeClient youtubeClient) {
        this.youtubeClient = youtubeClient;
    }


    public YoutubeToApiDto getVideo(String id) {

     return youtubeClient.getVideo(id);
    }
}


