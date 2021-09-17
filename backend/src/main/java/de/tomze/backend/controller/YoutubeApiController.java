package de.tomze.backend.controller;

import de.tomze.backend.api.YoutubeApiDto;

import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import java.util.List;

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


    @GetMapping("/api/tomze/videos/{vid_id}")
    public  ResponseEntity<YoutubeApiDto> getVideo (@PathVariable String vid_id){
        YoutubeApiDto youtubeToApiDto = youtubeService.getVideo(vid_id);

        VideoEntity videoEntity = VideoEntity.builder()
                .vid_id(vid_id).build();
        videoRepository.save(videoEntity);

        return ok(youtubeToApiDto);
    }

    @GetMapping("api/tomze/videolist")
    public ResponseEntity<List<VideoEntity>> getVideoList() {
        List<VideoEntity> videoList = youtubeService.getVideoList();

        return ok(videoList);
    }

   @DeleteMapping("/api/tomze/videos/{vid_id}")
    public ResponseEntity<VideoEntity> deleteVideo (@PathVariable String vid_id){
        VideoEntity videoEntityToDelete = youtubeService.deleteVideo(vid_id);

        return ok(videoEntityToDelete);
    }
}
