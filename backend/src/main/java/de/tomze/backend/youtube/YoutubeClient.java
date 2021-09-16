package de.tomze.backend.youtube;

import de.tomze.backend.api.YoutubeToApiDto;
import de.tomze.backend.config.YoutubeClientConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class YoutubeClient {

    private final YoutubeClientConfigProperties youtubeClientConfigProperties;
    private final YoutubeApi youtubeApi;

    @Autowired
    public YoutubeClient(YoutubeClientConfigProperties youtubeClientConfigProperties, YoutubeApi youtubeApi) {
        this.youtubeClientConfigProperties = youtubeClientConfigProperties;
        this.youtubeApi = youtubeApi;
    }

    public YoutubeToApiDto getVideo(String id) {
    return youtubeApi.getVideo(youtubeClientConfigProperties.getAccessToken(), id);

       // "AIzaSyBn0MUBtxKvqbeXLMu_iv7my_1l_Alut0A"

    }

}
