package de.tomze.backend.youtube;

import de.tomze.backend.api.YoutubeApiDto;
import feign.Param;
import feign.RequestLine;



public interface YoutubeApi {


    @RequestLine("GET videos?id={id}&key={accessToken}")
    YoutubeApiDto getVideo(@Param("accessToken") String accessToken,
                           @Param("id") String id);
}
