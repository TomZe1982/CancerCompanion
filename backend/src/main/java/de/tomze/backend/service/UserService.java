package de.tomze.backend.service;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.UserRepository;
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


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> getUser(String userName) {
        return userRepository.findByUserName(userName);
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
        Optional<UserEntity> existingUserEntityOptional = getUser(userName);
        if(existingUserEntityOptional.isPresent()){
            throw new IllegalArgumentException("Username is already in use");
        }
        userRepository.save(createdUserEntity);
        return createdUserEntity;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateUser(String userName, UserFromAppDto userFromAppDto) {
        Optional<UserEntity> userEntityOptional = getUser(userName);
        if (userEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("NotFound");
        }
        UserEntity userEntityToUpdate = userEntityOptional.get();

        if(!userFromAppDto.getUserName().equals(userName)){
            throw new IllegalArgumentException("Username cannot be changed");
        }

        if(userEntityToUpdate.getEmail().equals(userFromAppDto.getEmail())){
            throw new IllegalArgumentException("Nothing to change");
        }
        userEntityToUpdate.setEmail(userFromAppDto.getEmail());
        userRepository.save(userEntityToUpdate);
        return userEntityToUpdate;
    }

    public UserEntity resetPassword(String userName, UserFromAppDto userFromAppDto) {

        Optional<UserEntity> fetchedUserEntityOptional = userRepository.findByUserName(userName);

        if(fetchedUserEntityOptional.isEmpty()){
            throw new EntityNotFoundException("User not found");
        }
        UserEntity resetPasswordUserEntity = fetchedUserEntityOptional.get();

        String newHashedPassword = new BCryptPasswordEncoder().encode(userFromAppDto.getPassword());
        resetPasswordUserEntity.setPassword(newHashedPassword);
        userRepository.save(resetPasswordUserEntity);
        return resetPasswordUserEntity;
    }

    public UserEntity deleteUser(String userName) {
        Optional<UserEntity> userEntityOptionalToDelete = getUser(userName);
        if(userEntityOptionalToDelete.isEmpty()){
            throw new IllegalArgumentException("No user found to delete");
        }
        UserEntity userEntityToDelete = userEntityOptionalToDelete.get();
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



}
