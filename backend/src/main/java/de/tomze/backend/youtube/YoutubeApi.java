package de.tomze.backend.youtube;

import de.tomze.backend.api.YoutubeToApiDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;



public interface YoutubeApi {


    @RequestLine("GET videos?id={id}&key={accessToken}")
    YoutubeToApiDto getVideo(@Param("accessToken") String accessToken,
                                @Param("id") String id);
}
