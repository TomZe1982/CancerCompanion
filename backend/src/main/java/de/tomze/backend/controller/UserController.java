package de.tomze.backend.controller;


import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
@Getter
@Setter
public class UserController extends UserControllerMapper {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/api/tomze/user")
    public ResponseEntity<List<UserToAppDto>> getAllUsers(@AuthenticationPrincipal UserEntity authUser) {
        if(authUser.getRole().equals("user")){
            throw new IllegalArgumentException("User must not get all users");
        }
        List<UserEntity> listOfAllUserEntities = userService.getAllUsers();
        List<UserToAppDto> listOfAllUsersToApp = map(listOfAllUserEntities);

        return ok(listOfAllUsersToApp);
    }

    @GetMapping("/api/tomze/user/{userName}")
    public ResponseEntity<UserToAppDto> getUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName) {
        if(authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)){
            throw new IllegalArgumentException("User must not get another User");
        }
        Optional<UserEntity> foundUserEntity = userService.getUser(userName);
        if (foundUserEntity.isPresent()) {
            UserEntity userEntity = foundUserEntity.get();
            UserToAppDto foundUserToAppDto = mapUserToAppDto(userEntity);
            return ok(foundUserToAppDto);
        }
        return notFound().build();
    }

    @PostMapping("/api/tomze/register")
    public ResponseEntity<UserFromAppDto> createUser(@RequestBody UserFromAppDto userFromAppDto) {
        UserEntity createdUserEntity = userService.createUser(userFromAppDto);
        UserFromAppDto createdUserFromAppDto = mapUserFromAppDto(createdUserEntity);
        return ok(createdUserFromAppDto);

    }

    @PutMapping("/api/tomze/user/update/{userName}")
    public ResponseEntity<UserFromAppDto> updateUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @RequestBody UserFromAppDto userFromAppDto) {
        if (authUser.getRole().equals("user") && !authUser.getUserName().equals(userName) && !userFromAppDto.getUserName().equals(userName)){
            throw new IllegalArgumentException("User must not update other user");
        }
        UserEntity updatedUserEntity = userService.updateUser(userName, userFromAppDto);
        UserFromAppDto updatedUserFromAppDto = mapUserFromAppDto(updatedUserEntity);
        return ok(updatedUserFromAppDto);
    }

    @PutMapping("/api/tomze/user/resetpassword/{userName}")
    public ResponseEntity<UserToAppDto> resetUserPassword(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName){
        if(authUser.getRole().equals("user")){
            throw new IllegalArgumentException("only admin can reset users password");
        }
        if(authUser.getRole().equals("admin") && authUser.getUserName().equals(userName)){
            throw new IllegalArgumentException(("Admin must not reset own password"));
        }
        UserToAppDto userToAppDtoToResetPassword = userService.resetUserPassword(userName);
        return ok(userToAppDtoToResetPassword);
    }


    @DeleteMapping("/api/tomze/user/delete/{userName}")
    public ResponseEntity<UserFromAppDto> deleteUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName) {
        if (authUser.getRole().equals("admin") && authUser.getUserName().equals(userName)) {
            throw new IllegalArgumentException("Admin is not allowed to delete himself");
        }
        if (authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)) {
            throw new IllegalArgumentException("User must not delete other User");
        }
        UserEntity userEntityToDelete = userService.deleteUser(userName);
        UserFromAppDto deletedUserFromAppDto = mapUserFromAppDto(userEntityToDelete);
        return ok(deletedUserFromAppDto);
    }



}
