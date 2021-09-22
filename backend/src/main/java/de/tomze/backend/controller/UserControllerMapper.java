package de.tomze.backend.controller;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.api.UserToUpdateDto;
import de.tomze.backend.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

abstract class UserControllerMapper {


    protected UserFromAppDto mapUserFromAppDto(UserEntity userEntity){
        return UserFromAppDto.builder()
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .build();
    }

    protected UserToAppDto mapUserToAppDto(UserEntity userEntity) {
        return UserToAppDto.builder()
                .id(userEntity.getId())
                .role(userEntity.getRole())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .build();
    }

    protected List<UserToAppDto> map(List<UserEntity> userEntityList){

        List<UserToAppDto> userToAppDtoList = new ArrayList<>();

        for(UserEntity user :userEntityList){
            UserToAppDto userToAppDto = mapUserToAppDto(user);
            userToAppDtoList.add(userToAppDto);

        }
        return(userToAppDtoList);
    }

    protected UserToUpdateDto mapUserToUpdateDto(UserEntity userEntity){
        return UserToUpdateDto.builder()
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .build();
    }
}
