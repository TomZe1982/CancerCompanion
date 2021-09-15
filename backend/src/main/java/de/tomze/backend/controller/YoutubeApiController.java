package de.tomze.backend.controller;

import de.tomze.backend.api.YoutubeToApiDto;

import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
@Slf4j
public class YoutubeApiController {

    private final YoutubeService youtubeService;
    private final VideoRepository videoRepository;

    @Autowired
    public YoutubeApiController(YoutubeService youtubeService, VideoRepository videoRepository) {
        this.youtubeService = youtubeService;
        this.videoRepository = videoRepository;
    }


    @GetMapping("/api/tomze/videos/{id}")
    public  ResponseEntity<YoutubeToApiDto> getVideo (@PathVariable String id){
        YoutubeToApiDto youtubeToApiDto = youtubeService.getVideo(id);

        VideoEntity videoEntity = VideoEntity.builder()
                .vid_id(id).build();
        videoRepository.save(videoEntity);
        return ok(youtubeToApiDto);
    }

}
