package de.tomze.backend.controller;


import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/tomze")
@CrossOrigin
@Getter
@Setter
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserToAppDto>> getAllUsers (){
        List<UserEntity> listOfAllUserEntities = new ArrayList<>(userService.getAllUsers());
        List<UserToAppDto> listOfAllUsersToApp = map(listOfAllUserEntities);

        return ok(listOfAllUsersToApp);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<UserToAppDto> getUser(@PathVariable String userName) {
        Optional<UserEntity> foundUserEntity = userService.getUser(userName);
        if (foundUserEntity.isPresent()) {
            UserEntity userEntity = foundUserEntity.get();
            UserToAppDto foundUserToAppDto = map1(userEntity);
            return ok(foundUserToAppDto);
        }
        return notFound().build();
    }

    @PostMapping()
    public ResponseEntity<UserFromAppDto> createUser(@RequestBody UserFromAppDto userFromAppDto) {

        UserEntity createdUserEntity = userService.createUser(userFromAppDto);
        UserFromAppDto createdUserFromAppDto = map2(createdUserEntity);
        return ok(createdUserFromAppDto);

    }

    @PutMapping("/user/{userName}")
    public ResponseEntity<UserFromAppDto> updateUser(@PathVariable String userName, @RequestBody UserFromAppDto userFromAppDto){
        UserEntity updatedUserEntity = userService.updateUser(userName, userFromAppDto);
        UserFromAppDto updatedUserFromAppDto = map2(updatedUserEntity);
        return ok(updatedUserFromAppDto);
    }



    private UserToAppDto map1(UserEntity userEntity) {
        return UserToAppDto.builder()
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();
    }

    private UserFromAppDto map2(UserEntity userEntity){
        return UserFromAppDto.builder()
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .secondName(userEntity.getSecondName())
                .firstName(userEntity.getFirstName())
                .email(userEntity.getEmail())
                .street(userEntity.getStreet())
                .number(userEntity.getNumber())
                .city(userEntity.getCity())
                .zipCode(userEntity.getZipCode())
                .build();
    }

    private List<UserToAppDto> map(List<UserEntity> userEntityList){

       List<UserToAppDto> userToAppDtoList = new ArrayList<>();

        for(UserEntity user :userEntityList){
            UserToAppDto userToAppDto = map1(user);
            userToAppDtoList.add(userToAppDto);

        }
        return(userToAppDtoList);
    }
}
