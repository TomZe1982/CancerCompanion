package de.tomze.backend.controller;


import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.api.YoutubeApiDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.model.VideoEntity;
import de.tomze.backend.repository.BlogRepository;
import de.tomze.backend.repository.UserRepository;
import de.tomze.backend.repository.VideoRepository;
import de.tomze.backend.security.JwtConfig;

import de.tomze.backend.service.YoutubeService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class YoutubeControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/api/tomze";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private VideoRepository videoRepository;

    @AfterEach
    public void clearVideoRepo() {
        videoRepository.deleteAll();
    }



    @Test
    public void adminShouldGetAllVideos() {
        //GIVEN
        videoRepository.save(VideoEntity.builder()
                .vid_id("1a1a1a").build());

        videoRepository.save(VideoEntity.builder()
                .vid_id("2b2b2b").build());

        //WHEN
        HttpEntity<UserEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<VideoEntity[]> response = restTemplate.exchange(url() + "/videolist", HttpMethod.GET, httpEntity, VideoEntity[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));
    }

    @Test
    public void userShouldGetAllVideos() {
        //GIVEN
        videoRepository.save(VideoEntity.builder()
                .vid_id("1a1a1a").build());

        videoRepository.save(VideoEntity.builder()
                .vid_id("2b2b2b").build());

        //WHEN
        HttpEntity<UserEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "user"));
        ResponseEntity<VideoEntity[]> response = restTemplate.exchange(url() + "/videolist", HttpMethod.GET, httpEntity, VideoEntity[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));
    }






    private HttpHeaders authorizedHeader(String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role", role);
        Instant now = Instant.now();
        Date iat = Date.from(now);
        Date exp = Date.from(now.plus(Duration.ofMinutes(jwtConfig.getExpireAfterMinutes())));
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        return headers;
    }

}

