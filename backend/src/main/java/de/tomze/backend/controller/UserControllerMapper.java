package de.tomze.backend.controller;

import de.tomze.backend.api.UserFromAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

abstract class UserControllerMapper {

    protected UserToAppDto mapUserToAppDto(UserEntity userEntity) {
        return UserToAppDto.builder()
                .role(userEntity.getRole())
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();
    }

    protected UserFromAppDto mapUserFromAppDto(UserEntity userEntity){
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
                .role(userEntity.getRole())
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
}
