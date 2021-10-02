package de.tomze.backend.controller;



import de.tomze.backend.api.BlogFromAppDto;
import de.tomze.backend.api.BlogToAppDto;
import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.BlogRepository;
import de.tomze.backend.repository.UserRepository;
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
class BlogControllerTest {

    @LocalServerPort
    private int port;

    private String url() {
        return "http://localhost:" + port + "/api/tomze/blog";
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clearBlogRepo() {
        blogRepository.deleteAll();
    }

    @AfterEach
    public void clearUserRepo() {
        userRepository.deleteAll();
    }


    //GET ALL METHOD

    @Test
    public void adminShouldGetAllBlogsFromUser() {
        //GIVEN
       UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto[]> response = restTemplate.exchange(url() + "/allblogs/lala", HttpMethod.GET, httpEntity, BlogToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

    @Test
    public void adminShouldGetAllBlogsFromAdmin() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto[]> response = restTemplate.exchange(url() + "/allblogs/lala", HttpMethod.GET, httpEntity, BlogToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

    @Test
    public void userShouldGetAllBlogsFromAdmin() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "user"));
        ResponseEntity<BlogToAppDto[]> response = restTemplate.exchange(url() + "/allblogs/lala", HttpMethod.GET, httpEntity, BlogToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));

    }

    @Test
    public void userShouldGetAllBlogsFromUser() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("toto")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<BlogToAppDto[]> response = restTemplate.exchange(url() + "/allblogs/toto", HttpMethod.GET, httpEntity, BlogToAppDto[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().length, is(2));
    }

    //GET BY ID METHOD

    @Test
    public void userShouldGetOwnBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/lala/"+blogEntity2.getBlogId(), HttpMethod.GET, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is("02.01.1901"));
        assertThat(response.getBody().getEntry(), is("Hallo Mond!"));
    }

    @Test
    public void userShouldGetOtherUsersBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("lulu", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/lala/"+blogEntity2.getBlogId(), HttpMethod.GET, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is("02.01.1901"));
        assertThat(response.getBody().getEntry(), is("Hallo Mond!"));
    }

    @Test
    public void adminShouldGetOtherUsersBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/lala/"+blogEntity2.getBlogId(), HttpMethod.GET, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is("02.01.1901"));
        assertThat(response.getBody().getEntry(), is("Hallo Mond!"));
    }

    @Test
    public void adminShouldGetOwnBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("toto")
                .role("admin")
                .password("123")
                .email("toto@toto.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/toto/"+blogEntity2.getBlogId(), HttpMethod.GET, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is("02.01.1901"));
        assertThat(response.getBody().getEntry(), is("Hallo Mond!"));
    }

    @Test
    public void adminShouldGetOtherAdminsBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("toto")
                .role("admin")
                .password("123")
                .email("toto@toto.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("tata", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/toto/"+blogEntity2.getBlogId(), HttpMethod.GET, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is("02.01.1901"));
        assertThat(response.getBody().getEntry(), is("Hallo Mond!"));
    }

    //CREATE METHOD

    @Test
    public void userShouldCreateOwnBlog() {
        //GIVEN
     UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

       BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("lala", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is(notNullValue()));
        assertThat(response.getBody().getBlogId(), is(notNullValue()));
        assertThat(response.getBody().getEntry(), is("Hallo Welt!"));
    }

    @Test
    public void adminShouldCreateOwnBlog() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("lala", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
        assertThat(response.getBody(),is(notNullValue()));
        assertThat(response.getBody().getDate(), is(notNullValue()));
        assertThat(response.getBody().getBlogId(), is(notNullValue()));
        assertThat(response.getBody().getEntry(), is("Hallo Welt!"));
    }

    @Test
    public void adminMustNotCreateOtherAdminsBlog() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND) );
    }

    @Test
    public void adminMustNotCreateOtherUsersBlog() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND) );
    }

    @Test
    public void userMustNotCreateOtherUsersBlog() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("toto", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND) );
    }

    @Test
    public void userMustNotCreateAdminsBlog() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        BlogFromAppDto blogFromAppDto = BlogFromAppDto.builder()
                .entry("Hallo Welt!").build();


        //WHEN
        HttpEntity<BlogFromAppDto> httpEntity = new HttpEntity<>(blogFromAppDto, authorizedHeader("toto", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/newblog", HttpMethod.POST, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND) );
    }

    //DELETE METHOD

    @Test
    public void userShouldDeleteOwnBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("lala", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId(), HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

        @Test
        public void adminShouldDeleteOwnBlogById() {
            //GIVEN
            UserEntity userEntity = userRepository.save(UserEntity.builder()
                    .userName("lala")
                    .role("admin")
                    .password("123")
                    .email("lala@lala.de").build());

            BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                    .userId(userEntity)
                    .date("01.01.1901")
                    .entry("Hallo Welt!").build());
            BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                    .userId(userEntity)
                    .date("02.01.1901")
                    .entry("Hallo Mond!").build());

            //WHEN
            HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("lala", "admin"));
            ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId(), HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

            //THEN
            assertThat(response.getStatusCode(), is(HttpStatus.OK) );
    }

    @Test
    public void adminShouldDeleteOtherUsersBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId(), HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK) );
    }

    @Test
    public void userMustNotDeleteAdminsBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId(), HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR) );
    }

    @Test
    public void userMustNotDeleteOtherUsersBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("user")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "user"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId(), HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR) );
    }

    @Test
    public void adminMustNotDeleteOtherAdminsBlogById() {
        //GIVEN
        UserEntity userEntity = userRepository.save(UserEntity.builder()
                .userName("lala")
                .role("admin")
                .password("123")
                .email("lala@lala.de").build());

        BlogEntity blogEntity1 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("01.01.1901")
                .entry("Hallo Welt!").build());
        BlogEntity blogEntity2 = blogRepository.save(BlogEntity.builder()
                .userId(userEntity)
                .date("02.01.1901")
                .entry("Hallo Mond!").build());

        //WHEN
        HttpEntity<BlogEntity> httpEntity = new HttpEntity<>(authorizedHeader("toto", "admin"));
        ResponseEntity<BlogToAppDto> response = restTemplate.exchange(url() + "/delete/lala/"+blogEntity2.getBlogId() , HttpMethod.DELETE, httpEntity, BlogToAppDto.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR) );
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