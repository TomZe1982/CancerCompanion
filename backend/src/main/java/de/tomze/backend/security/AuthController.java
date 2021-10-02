package de.tomze.backend.security;

import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static io.jsonwebtoken.lang.Assert.hasText;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
public class AuthController{

    public static final String ACCESS_TOKEN_URL = "/auth/access_token";

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @GetMapping("/auth/me")
    public ResponseEntity<UserToAppDto> getLoggedInUser(@AuthenticationPrincipal UserEntity userEntity){
        return ok(
                UserToAppDto.builder()
                        .role(userEntity.getRole())
                        .userName(userEntity.getUserName())
                        .build()
                );
    }

    @PostMapping(ACCESS_TOKEN_URL)
    public ResponseEntity<AccessToken> getAccessToken(@RequestBody Credentials credentials){
        String userName = credentials.getUserName();
        String password = credentials.getPassword();

        if(userName == null || userName.isEmpty()){
            throw new IllegalArgumentException("username must not be empty");
        }

        if(password == null || password.isEmpty()){
            throw new IllegalArgumentException("password must not be empty");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            authenticationManager.authenticate(authToken);

            UserEntity userEntity = userService.getUser(userName);
            String token = jwtService.createJwtToken(userEntity);

            AccessToken accessToken = new AccessToken(token);
            return ok(accessToken);
        }catch (AuthenticationException ex){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }


    }


}
