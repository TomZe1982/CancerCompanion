package de.tomze.backend.controller;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.Address;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.SpringBootTests;
import de.tomze.backend.service.UserService;
import org.h2.engine.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends SpringBootTests {

    @Autowired
    UserService userService;



    @Test
    void ShouldFindOneUser() {

        //GIVEN
        Address address = Address.builder()
                .street("tom")
                .number("1")
                .zipCode("1")
                .city("K").build();
        UserEntity user = UserEntity.builder()
                .role("user")
                .userName("tom")
                .firstName("tom")
                .secondName("tom")
                .email("tom")
                .password("123")
                .address(address).build();

        //WHEN

        Optional<UserEntity> actualOptional = userService.getUser("tom");

        //THEN

        if(actualOptional.isPresent()){
            UserEntity actual = actualOptional.get();

            assertEquals(user, actual);
        }
    }

    @Test
    void ShouldNotFindOneUser() {

        //WHEN

        Optional<UserEntity> actualOptional = userService.getUser("elise");


        //THEN
            assertEquals(Optional.empty(),actualOptional);

    }

    @Test
    void ShouldCreateUser() {
        //GIVEN
        Address address = Address.builder()
                .street("tom")
                .number("1")
                .zipCode("1")
                .city("K").build();

        UserFromAppDto user = UserFromAppDto.builder()
                .role("user")
                .userName("tom")
                .firstName("tom")
                .secondName("tom")
                .email("tom")
                .password("123")
                .address(address).build();

        //WHEN
        UserEntity actual = userService.createUser(user);

        UserEntity expected = UserEntity.builder()
                .role("user")
                .userName("tom")
                .firstName("tom")
                .secondName("tom")
                .email("tom")
                .password("123")
                .address(address).build();

        //THEN
        assertEquals(expected.getUserName(), actual.getUserName());

    }

    @Test
    void UserShouldUpdateHimself() {
    }

    @Test
    void UserShouldNotUpdateOtherUser() {
    }

    @Test
    void UserShouldNotUpdateAdmin() {
    }

    @Test
    void AdminShouldUpdateHimself() {
    }

    @Test
    void AdminShouldUpdateOtherUser() {
    }

    @Test
    void UserShouldDeleteHimself() {
    }

    @Test
    void UserShouldNotDeleteOtherUSer() {
    }

    @Test
    void UserShouldNotDeleteAdmin() {
    }

    @Test
    void AdminShouldDeleteOtherUser() {
    }

    @Test
    void AdminShouldNotDeleteHimself() {
    }
}