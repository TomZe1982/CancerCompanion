package de.tomze.backend.controller;

import de.tomze.backend.api.TherapyPassFromAppDto;
import de.tomze.backend.api.TherapyPassToAppDto;
import de.tomze.backend.model.TherapyPassEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.TherapyPassRespository;
import de.tomze.backend.repository.UserRepository;
import de.tomze.backend.security.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
class TherapyPassControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/api/tomze/therapy";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private TherapyPassRespository therapyPassRespository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearTherapyRepo() {
        therapyPassRespository.deleteAll();
    }

    @AfterEach
    public void clearUserRepo() {
        userRepository.deleteAll();
    }


    @Test
    public void userShouldGetHisTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        //WHEN
        HttpEntity<TherapyPassEntity> httpEntity = new HttpEntity<>(authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto[]> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

    @Test
    public void adminShouldGetHisTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("admin")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        //WHEN
        HttpEntity<TherapyPassEntity> httpEntity = new HttpEntity<>(authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto[]> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

    @Test
    public void adminShouldGetUsersTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        //WHEN
        HttpEntity<TherapyPassEntity> httpEntity = new HttpEntity<>(authorizedHeader("tomze", "admin"));
        ResponseEntity<TherapyPassToAppDto[]> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

  /* @Test
    public void UserMustNotGetUsersTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("01.01.1910")
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        //WHEN
        HttpEntity<TherapyPassEntity> httpEntity = new HttpEntity<>(authorizedHeader("tomze", "user"));
        ResponseEntity<TherapyPassToAppDto[]> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED) );

    }*/

/*    @Test
    public void UserHasNoTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);


        //WHEN
        HttpEntity<TherapyPassEntity> httpEntity = new HttpEntity<>(authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto[]> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR) );
    }*/

    @Test
    public void adminShouldCreateUserTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .date("2021-11-17T09:01:45.809Z")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader("tomze", "admin"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.POST, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getDate(), is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Hallo Welt!"));
        assertThat(response.getBody().getDescription(), is("Hallo Welt!"));
        assertThat(responseList.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(3));

    }

    @Test
    public void userMustNotCreateOwnTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.POST, httpEntity, TherapyPassToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @Test
    public void userMustNotCreateUserTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Welt!")
                .description("Hallo Welt!").build());

        therapyPassRespository.save(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader("tomze", "user"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.POST, httpEntity, TherapyPassToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    //UPDATE THERAPY

    @Test
    public void adminCanUpdateUserTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader("tomze", "admin"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.PUT, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getDate(), is(notNullValue()));
        assertThat(response.getBody().getTitle(), is("Hallo Welt!"));
        assertThat(response.getBody().getDescription(), is("Hallo Welt!"));
        assertThat(responseList.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(2));
    }

    @Test
    public void userMustNotUpdateOwnTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.PUT, httpEntity, TherapyPassToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userMustNotUpdateUserTherapies() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader("tomze", "user"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.PUT, httpEntity, TherapyPassToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void therapyNotFoundToUpdate() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());

        TherapyPassFromAppDto newTherapy = TherapyPassFromAppDto.builder()
                .title("Hallo Welt!")
                .description("Hallo Welt!").build();

        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(newTherapy, authorizedHeader("tomze", "admin"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/1", HttpMethod.PUT, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(2));
    }

    //DELETE THERAPY

    @Test
    public void adminCanDeleteUserTherapy() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());


        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("tomze", "admin"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.DELETE, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(1));
    }

    @Test
    public void userMustNotDeleteOwnTherapy() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("11-11-2222 11:11")
                .dateTime(LocalDateTime.now())
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());


        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader(userEntity.getUserName(), userEntity.getRole()));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.DELETE, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(2));
    }

    /*
      @Test
    public void userMustNotDeleteUserTherapy() {
        //GIVEN
        UserEntity userEntity = UserEntity.builder()
                .role("user")
                .userName("toto")
                .password("123")
                .email("toto@toto.de").build();

        userRepository.save(userEntity);

        TherapyPassEntity therapyPassEntity1 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("01.01.1910")
                .title("Hallo Mond!")
                .description("Hallo Mond!").build());

        TherapyPassEntity therapyPassEntity2 = therapyPassRespository.saveAndFlush(TherapyPassEntity.builder()
                .userId(userEntity)
                .date("01.01.1920")
                .title("Hallo Sterne!")
                .description("Hallo Sterne!").build());


        //WHEN
        HttpEntity<TherapyPassFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("tomze", "user"));
        ResponseEntity<TherapyPassToAppDto> response = restTemplate.exchange(url() + "/" + userEntity.getUserName() + "/" + therapyPassEntity1.getTherapyId(), HttpMethod.DELETE, httpEntity, TherapyPassToAppDto.class);
        ResponseEntity<TherapyPassToAppDto[]> responseList = restTemplate.exchange(url() + "/" + userEntity.getUserName(), HttpMethod.GET, httpEntity, TherapyPassToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(responseList.getBody(), is(notNullValue()));
        assertThat(responseList.getBody().length, is(2));
    }*/


    private HttpHeaders authorizedHeader(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
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