package de.tomze.backend.controller;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.UserRepository;
import de.tomze.backend.security.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.hamcrest.Matchers.*;

@SpringBootTest(
        properties = "spring.profiles.active:h2",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class UserControllerTest {

    @LocalServerPort
    private int port;

    private String url(){
        return "http://localhost:" + port + "/api/tomze";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearUserRepo(){
        userRepository.deleteAll();
    }

    @BeforeEach
    public void initializeDB(){
        UserEntity userEntity1 = UserEntity.builder()
                .userId(1L)
                .userName("toto")
                .role("admin")
                .password("123")
                .email("toto@toto.de")
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .userId(2L)
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de")
                .build();

        UserEntity userEntity3 = UserEntity.builder()
                .userId(3L)
                .userName("lolo")
                .role("user")
                .password("123")
                .email("lolo@lolo.de")
                .build();

        userRepository.save(userEntity1);
        userRepository.save(userEntity2);
        userRepository.save(userEntity3);
    }


    @Test
    public void shouldFindAllUsersUser(){

        //WHEN
        HttpEntity<UserEntity> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto[]> response = restTemplate.exchange(url() + "/user", HttpMethod.GET, httpEntity, UserToAppDto[].class);


        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(3));
    }

    @Test
    public void shouldFindAllUsersAdmin(){

        //WHEN
        HttpEntity<UserEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto[]> response = restTemplate.exchange(url() + "/user", HttpMethod.GET, httpEntity, UserToAppDto[].class);


        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(3));
    }



    @Test
    public void shouldCreateUser(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lulu")
                .password("123")
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto);
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/register", HttpMethod.POST, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        UserToAppDto actualUser = response.getBody();
        assertThat(actualUser.getUserName(), is("lulu"));
        assertThat(actualUser.getEmail(), is("lulu@lulu.de"));


    }

    @Test
    public void shouldNotCreateUser(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password("123")
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/register", HttpMethod.POST, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userShouldUpdateHimself(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password(null)
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getEmail(), is("lulu@lulu.de"));

    }

    @Test
    public void userMustNotUpdateOtherUser(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password(null)
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("lolo", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void adminShouldUpdateOtherUser(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password(null)
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getEmail(), is("lulu@lulu.de"));
    }

    @Test
    public void userShouldUpdateHisPassword(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password("12345678")
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));

    }

    @Test
    public void userMustNotUpdateOtherUsersPassword(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password("12345678")
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("lolo", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    @Test
    public void adminShouldUpdateOtherUsersPassword(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("lala")
                .password("12345678")
                .email("lulu@lulu.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void adminShouldUpdateHisPassword(){
        //GIVEN
        UserFromAppDto userFromAppDto = UserFromAppDto.builder()
                .userName("toto")
                .password("12345678")
                .email("toto@toto.de")
                .build();

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(userFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/update/toto", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void adminShouldUpdateResetUserPassword(){
        //GIVEN

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/resetpassword/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getPassword().length(), is(12));
    }

    @Test
    public void adminMustNotResetAdminPassword(){
        //GIVEN

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/resetpassword/toto", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userMustNotResetAdminPassword(){
        //GIVEN

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/resetpassword/toto", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userMustNotResetOtherUserPassword(){
        //GIVEN

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/resetpassword/lolo", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userMustNotResetOwnPassword(){
        //GIVEN

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/resetpassword/lala", HttpMethod.PUT, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void userShouldDeleteHimself(){

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/delete/lala", HttpMethod.DELETE, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void adminShouldDeleteUser(){

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/delete/lala", HttpMethod.DELETE, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void userMustNotDeleteOtherUser(){

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("lolo", "user"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/delete/lala", HttpMethod.DELETE, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void adminMustNotDeleteAdmin(){

        //WHEN
        HttpEntity<UserFromAppDto> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<UserToAppDto> response = restTemplate.exchange(url() + "/user/delete/admin", HttpMethod.DELETE, httpEntity, UserToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
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