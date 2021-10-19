package de.tomze.backend.service;


import de.tomze.backend.api.TherapyPassFromAppDto;
import de.tomze.backend.api.TherapyPassToAppDto;
import de.tomze.backend.model.TherapyPassEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.TherapyPassRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class TherapyPassService {

    private final UserService userService;
    private final TherapyPassRespository therapyPassRespository;

    @Autowired
    public TherapyPassService(UserService userService, TherapyPassRespository therapyPassRespository) {
        this.userService = userService;
        this.therapyPassRespository = therapyPassRespository;
    }

    public List<TherapyPassEntity> getAllTherapies(String userName) {
        UserEntity userEntity = userService.getUser(userName);

        return userEntity.getTherapyEntries();

    }

    public TherapyPassEntity createNewTherapy(String userName, TherapyPassFromAppDto therapyPassFromAppDto) {
        UserEntity userEntity = userService.getUser(userName);

        TherapyPassEntity therapyPassEntityToCreate = map(therapyPassFromAppDto);

        therapyPassEntityToCreate.setUserId(userEntity);

        userEntity.addTherapy(therapyPassEntityToCreate);

        therapyPassRespository.save(therapyPassEntityToCreate);

        return therapyPassEntityToCreate;
    }

    public TherapyPassEntity updateTherapy(String userName, Long therapyId, TherapyPassFromAppDto therapyPassFromAppDto) {

        List<TherapyPassEntity> therapyPassEntityList = getAllTherapies(userName);

        for(TherapyPassEntity therapyPassEntity : therapyPassEntityList){
            if(therapyPassEntity.getTherapyId().equals(therapyId)){
                therapyPassEntity.setTitle(therapyPassFromAppDto.getTitle());
                therapyPassEntity.setDescription(therapyPassFromAppDto.getDescription());
                therapyPassRespository.save(therapyPassEntity);
                return therapyPassEntity;
            }

        }
        throw new EntityNotFoundException("No Therapy found!");
    }


    private TherapyPassEntity map(TherapyPassFromAppDto therapyPassFromAppDto) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formatDateTime = now.format(formatter);

        return TherapyPassEntity.builder()
                .date(formatDateTime)
                .title(therapyPassFromAppDto.getTitle())
                .description(therapyPassFromAppDto.getDescription()).build();
    }


}
