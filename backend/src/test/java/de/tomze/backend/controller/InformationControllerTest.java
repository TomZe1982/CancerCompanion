package de.tomze.backend.controller;


import de.tomze.backend.api.InformationFromAppDto;
import de.tomze.backend.api.InformationToAppDto;
import de.tomze.backend.model.InformationEntity;
import de.tomze.backend.repository.InformationRepository;
import de.tomze.backend.security.JwtConfig;
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
class InformationControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/api/tomze/info";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private InformationRepository informationRepository;

    @AfterEach
    public void clearBlogRepo() {
        informationRepository.deleteAll();
    }


    @Test
    public void adminShouldCreateInfo() {
        //GIVEN
        InformationFromAppDto informationFromAppDto = InformationFromAppDto.builder()
                .title("Guten")
                .info("Tag").build();

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(informationFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url(), HttpMethod.POST,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Guten"));
        assertThat(response.getBody().getInfo(), is("Tag"));
    }

    @Test
    public void userMustNotCreateInfo() {
        //GIVEN
        InformationFromAppDto informationFromAppDto = InformationFromAppDto.builder()
                .title("Guten")
                .info("Tag").build();

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(informationFromAppDto, authorizedHeader("lala", "user"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url(), HttpMethod.POST,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void shouldGetAllInfos() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(null);
        ResponseEntity<InformationToAppDto[]> response = restTemplate.exchange(url() + "/all", HttpMethod.GET,  httpEntity, InformationToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));
    }

    @Test
    public void shouldGetSpecialInfo() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(null);
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/all/" + informationEntity1.getId(), HttpMethod.GET,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Halli"));
        assertThat(response.getBody().getInfo(), is("Hallo"));
    }


    @Test
    public void userMustNotUpdateSpecialInfo() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());

        InformationFromAppDto informationFromAppDto = InformationFromAppDto.builder()
                .title("Guten")
                .info("Flug").build();

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(informationFromAppDto, authorizedHeader("lala", "user"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/" + informationEntity2.getId(), HttpMethod.PUT,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }


    @Test
    public void userMustNotDeleteSpecialInfo() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/" + informationEntity2.getId(), HttpMethod.DELETE,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void adminShouldUpdateSpecialInfo() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());

        InformationFromAppDto informationFromAppDto = InformationFromAppDto.builder()
                .title("Guten")
                .info("Flug").build();

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(informationFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/" + informationEntity2.getId(), HttpMethod.PUT,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Guten"));
        assertThat(response.getBody().getInfo(), is("Flug"));
    }



    @Test
    public void adminShouldDeleteSpecialInfo() {
        //GIVEN
        InformationEntity informationEntity1 = informationRepository.save(InformationEntity.builder()
                .title("Halli")
                .info("Hallo").build());

        InformationEntity informationEntity2 = informationRepository.save(InformationEntity.builder()
                .title("Affen")
                .info("Tanz").build());


        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/" +informationEntity2.getId() , HttpMethod.DELETE,  httpEntity, InformationToAppDto.class);
        ResponseEntity<InformationToAppDto[]> response2 = restTemplate.exchange(url() + "/all", HttpMethod.GET,  httpEntity, InformationToAppDto[].class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response2.getStatusCode(), is(HttpStatus.OK));
        assertThat(response2.getBody(), is(notNullValue()));
        assertThat(response2.getBody().length, is(1));

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
