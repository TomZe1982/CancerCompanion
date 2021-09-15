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

    private String getAccessToken() {
        return youtubeClientConfigProperties.getAccessToken();
    }

    public YoutubeToApiDto getVideo(String id) {
       YoutubeToApiDto youtubeAPIDto =  youtubeApi.getVideo(getAccessToken(), id);

       return youtubeAPIDto;
    }

}
