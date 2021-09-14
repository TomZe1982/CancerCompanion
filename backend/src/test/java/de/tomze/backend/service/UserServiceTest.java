package de.tomze.backend.service;

import de.tomze.backend.api.UserFromAppDto;

import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.UserRepository;
import de.tomze.backend.service.SpringBootTests;
import de.tomze.backend.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;




class UserServiceTest extends SpringBootTests {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;



    @Test
    void ShouldFindOneUser() {

        //GIVEN
        UserEntity user = UserEntity.builder()
                .role("user")
                .userName("tom")
                .email("tom")
                .password("123")
              .build();

        userRepository.save(user);

        //WHEN

        Optional<UserEntity> actualOptional = userService.getUser("tom");

        //THEN

        if(actualOptional.isPresent()){
            UserEntity actual = actualOptional.get();

            assertEquals(user.getUserName(), actual.getUserName());
        }
    }

    @Test
    void ShouldNotFindOneUser() {
        //GIVEN
        UserEntity user = UserEntity.builder()
                .role("user")
                .userName("tom")
                .email("tom")
                .password("123")
                .build();

        userRepository.save(user);
        //WHEN

        Optional<UserEntity> actualOptional = userService.getUser("elise");


        //THEN
            assertEquals(Optional.empty(), actualOptional);

    }

    @Test
    void ShouldCreateUser() {
        //GIVEN

        UserFromAppDto user = UserFromAppDto.builder()
                .userName("tom")
                .email("tom")
                .password("123").build();

        //WHEN
        UserEntity actual = userService.createUser(user);

        UserEntity expected = UserEntity.builder()
                .role("user")
                .userName("tom")
                .email("tom")
                .password("123").build();

        //THEN
        assertEquals(expected.getUserName(), actual.getUserName());

    }

}