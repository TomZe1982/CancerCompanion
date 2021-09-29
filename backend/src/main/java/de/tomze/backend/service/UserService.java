package de.tomze.backend.service;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.UserRepository;
import de.tomze.backend.security.PasswordService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static org.springframework.util.StringUtils.hasText;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;


    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public UserEntity getUser(String userName) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);
        if(userEntityOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }

        return userEntityOptional.get();
    }

    public UserEntity createUser(UserFromAppDto userFromAppDto) {
        if(!hasText(userFromAppDto.getUserName())){
            throw new IllegalArgumentException("Username must not be empty!");
        }
        if(!hasText(userFromAppDto.getEmail())) {
            throw new IllegalArgumentException("Email must not be empty!");
        }
        if(!hasText(userFromAppDto.getPassword())){
            throw new IllegalArgumentException("Password must not be empty!");
        }
        UserEntity createdUserEntity = map(userFromAppDto);

        String userName = createdUserEntity.getUserName();

        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(userName);

        if(userEntityOptional.isPresent()){
            throw new IllegalArgumentException("Username is already in use");
        }

        userRepository.save(createdUserEntity);
        return createdUserEntity;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateUser(String userName, UserFromAppDto userFromAppDto) {
        UserEntity userEntityToUpdate = getUser(userName);

        if(!userFromAppDto.getUserName().equals(userName)){
            throw new IllegalArgumentException("Username cannot be changed");
        }
        if(userEntityToUpdate.getEmail().equals(userFromAppDto.getEmail()) && userEntityToUpdate.getPassword().equals(userFromAppDto.getPassword())){
            throw new IllegalArgumentException("Nothing to change");
        }
        if(userFromAppDto.getEmail().equals(userEntityToUpdate.getEmail())) {
            userEntityToUpdate = resetPassword(userFromAppDto.getUserName(), userFromAppDto);

        }
        if(userFromAppDto.getPassword() == null && !userFromAppDto.getEmail().equals(userEntityToUpdate.getEmail()))  {
            userEntityToUpdate.setEmail(userFromAppDto.getEmail());
        }

       return userRepository.save(userEntityToUpdate);

    }

    public UserEntity resetPassword(String userName, UserFromAppDto userFromAppDto) {

        UserEntity resetPasswordUserEntity = getUser(userName);

        String newHashedPassword = new BCryptPasswordEncoder().encode(userFromAppDto.getPassword());
        resetPasswordUserEntity.setPassword(newHashedPassword);
        userRepository.save(resetPasswordUserEntity);
        return resetPasswordUserEntity;
    }

        public UserToAppDto resetUserPassword(String userName) {
        UserEntity userEntityResetPassword = getUser(userName);

        UserToAppDto userToAppDtoResetPassword = mapUserToAppDto(userEntityResetPassword);

        String unhashedPassword = passwordService.getNewPassword();
        String hashedPassword = new BCryptPasswordEncoder().encode(unhashedPassword);

        userEntityResetPassword.setPassword(hashedPassword);
        userRepository.save(userEntityResetPassword);

        userToAppDtoResetPassword.setPassword(unhashedPassword);

        return userToAppDtoResetPassword;
    }

    public UserEntity deleteUser(String userName) {
        UserEntity userEntityToDelete = getUser(userName);
        userRepository.delete(userEntityToDelete);
        return userEntityToDelete;
    }

    public UserEntity map(UserFromAppDto userFromAppDto){
        String hashedPassword = new BCryptPasswordEncoder().encode(userFromAppDto.getPassword());
        return  UserEntity.builder()
                .role("user")
                .userName(userFromAppDto.getUserName())
                .password(hashedPassword)
                .email(userFromAppDto.getEmail())
                .build();
    }

    public UserToAppDto mapUserToAppDto(UserEntity userEntity){

        return  UserToAppDto.builder()
                .role("user")
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .build();
    }

}
