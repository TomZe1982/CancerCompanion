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

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

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

        List<UserEntity> listOfAllUserEntities = userService.getAllUsers();
        List<UserToAppDto> listOfAllUsersToApp = map(listOfAllUserEntities);

        return ok(listOfAllUsersToApp);
    }

    @GetMapping("/api/tomze/user/{userName}")
    public ResponseEntity<UserToAppDto> getUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName) {

            UserEntity userEntity = userService.getUser(userName);
            UserToAppDto foundUserToAppDto = mapUserToAppDto(userEntity);
            return ok(foundUserToAppDto);

    }

    @PostMapping("/api/tomze/register")
    public ResponseEntity<UserToAppDto> createUser(@RequestBody UserFromAppDto userFromAppDto) {
        UserEntity createdUserEntity = userService.createUser(userFromAppDto);
        UserToAppDto createdUserToAppDto = mapUserToAppDto(createdUserEntity);
        return ok(createdUserToAppDto);

    }

    @PutMapping("/api/tomze/user/update/{userName}")
    public ResponseEntity<UserToAppDto> updateUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @RequestBody UserFromAppDto userFromAppDto) {
        if (authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("User must not update other user");
        }
        UserEntity updatedUserEntity = userService.updateUser(userName, userFromAppDto);
        UserToAppDto updatedUserToAppDto = mapUserToAppDto(updatedUserEntity);
        return ok(updatedUserToAppDto);
    }

    @PutMapping("/api/tomze/user/resetpassword/{userName}")
    public ResponseEntity<UserToAppDto> resetUserPassword(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName){
        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("only admin can reset users password");
        }
        if(authUser.getRole().equals("admin") && authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("Admin must not reset own password");
        }
        UserToAppDto userToAppDtoToResetPassword = userService.resetUserPassword(userName);
        return ok(userToAppDtoToResetPassword);
    }


    @DeleteMapping("/api/tomze/user/delete/{userName}")
    public ResponseEntity<UserToAppDto> deleteUser(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName) {
        if (authUser.getRole().equals("admin") && authUser.getUserName().equals(userName)) {
            throw new NotAuthorizedException("Admin is not allowed to delete himself");
        }
        if (authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)) {
            throw new NotAuthorizedException("User must not delete other User");
        }
        UserEntity userEntityToDelete = userService.deleteUser(authUser, userName);
        UserToAppDto deletedUserToAppDto = mapUserToAppDto(userEntityToDelete);
        return ok(deletedUserToAppDto);
    }



}
