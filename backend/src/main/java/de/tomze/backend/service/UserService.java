package de.tomze.backend.service;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
            throw new IllegalArgumentException("This username is not available");
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
            throw new IllegalArgumentException("NotFound");
        }
        UserEntity userEntityToUpdate = userEntityOptional.get();
        if (userEntityToUpdate.equals(userFromAppDto)) {
            throw new IllegalArgumentException("Nothing to Change");
        }
        deleteUser(userEntityToUpdate.getUserName());
        UserEntity updatedUserEntity = map(userFromAppDto);
        userRepository.save(updatedUserEntity);
        return updatedUserEntity;
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
