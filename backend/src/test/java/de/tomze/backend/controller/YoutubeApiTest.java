
package de.tomze.backend.controller;


import de.tomze.backend.api.VideoDto;
import de.tomze.backend.api.YoutubeApiDto;
import de.tomze.backend.service.SpringBootTests;
import de.tomze.backend.youtube.YoutubeApi;
import de.tomze.backend.youtube.YoutubeClient;
import org.junit.jupiter.api.Test;


import javax.annotation.Resource;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class YoutubeApiTest extends SpringBootTests {

    @Resource
    private YoutubeClient youtubeClient;

    @Resource(name = "youtubeAPIMock")
    private YoutubeApi youtubeApiMock; // this service is a mocked bean

    @Test
    public void getYoutubeApiDtoTest() {
        // GIVEN
       VideoDto videoDto = VideoDto.builder()
               .vid_id("1111").build();

       YoutubeApiDto youtubeApiDto = YoutubeApiDto.builder()
               .etag("1")
               .kind("2")
               .items(List.of())
               .build();

        // WHEN
        when(youtubeApiMock.getVideo(anyString(), anyString()))
                .thenReturn(youtubeApiDto);


        // THEN
        verify(youtubeApiMock, times(1)).getVideo(anyString(), anyString());



    }
}