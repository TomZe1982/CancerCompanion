package de.tomze.backend.controller;

import de.tomze.backend.api.VideoDto;
import de.tomze.backend.api.YoutubeApiDto;

import de.tomze.backend.model.UserEntity;
import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
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

    @GetMapping("api/tomze/videolist")
    public ResponseEntity<List<VideoDto>> getVideoList() {
        List<VideoEntity> videoList = youtubeService.getVideoList();
        List<VideoDto> videoDtoList = mapList(videoList);

        return ok(videoDtoList);
    }

    @GetMapping("/api/tomze/videos/{vid_id}")
    public  ResponseEntity<YoutubeApiDto> getVideo (@PathVariable String vid_id){
        YoutubeApiDto youtubeToApiDto = youtubeService.getVideo(vid_id);

        VideoEntity videoEntity = VideoEntity.builder()
                .vid_id(vid_id).build();
        videoRepository.save(videoEntity);

        return ok(youtubeToApiDto);
    }

   @DeleteMapping("/api/tomze/videos/{vid_id}")
    public ResponseEntity<VideoDto> deleteVideo (@AuthenticationPrincipal UserEntity authUser,  @PathVariable String vid_id){
        if(authUser.getRole().equals("user")){
            throw new IllegalArgumentException("User must not delete videos");
        }
        VideoEntity videoEntityToDelete = youtubeService.deleteVideo(vid_id);
       VideoDto videoDtoToDelete = map(videoEntityToDelete);

        return ok(videoDtoToDelete);
    }

    private VideoDto map(VideoEntity videoEntityToDelete) {
        return VideoDto.builder()
                .vid_id(videoEntityToDelete.getVid_id()).build();
    }

    private List<VideoDto> mapList(List<VideoEntity> videoEntityList) {
       List<VideoDto> videoDtoList = new ArrayList<>();

       for (VideoEntity videoEntity : videoEntityList){
           VideoDto videoDto = map(videoEntity);
            videoDtoList.add(videoDto);
       }

       return videoDtoList;
    }
}
