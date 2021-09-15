package de.tomze.backend.youtube;

import de.tomze.backend.api.YoutubeToApiDto;
import feign.Param;
import feign.RequestLine;

public interface YoutubeApi {

    String ACCESS_TOKEN_PARAM = "access-token";

    @RequestLine("GET videos/{id}")
    YoutubeToApiDto getVideo(@Param(ACCESS_TOKEN_PARAM) String accessToken,
                                @Param("id") String id);
}
