package de.tomze.backend.controller.informationControllerTests;


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
class InformationControllerTest3 {

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

    @BeforeEach
    public void initializeDB() {
        informationRepository.save(InformationEntity.builder()
                .id(1L)
                .title("Halli")
                .info("Hallo").build());

        informationRepository.save(InformationEntity.builder()
                .id(2L)
                .title("Affen")
                .info("Tanz").build());

        informationRepository.save(InformationEntity.builder()
                .id(3L)
                .title("Manno")
                .info("Mann").build());

        informationRepository.save(InformationEntity.builder()
                .id(4L)
                .title("Frauo")
                .info("Frau").build());
    }


    @Test
    @Order(1)
    public void adminShouldUpdateSpecialInfo() {
        InformationFromAppDto informationFromAppDto = InformationFromAppDto.builder()
                .title("Guten")
                .info("Flug").build();

        //WHEN
        HttpEntity<InformationFromAppDto> httpEntity = new HttpEntity<>(informationFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<InformationToAppDto> response = restTemplate.exchange(url() + "/3", HttpMethod.PUT,  httpEntity, InformationToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Guten"));
        assertThat(response.getBody().getInfo(), is("Flug"));
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
