package de.tomze.backend.service;


import de.tomze.backend.api.TherapyPassFromAppDto;
import de.tomze.backend.model.TherapyPassEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.TherapyPassRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        List<TherapyPassEntity> therapyPassEntityList = userEntity.getTherapyEntries();

        therapyPassEntityList.sort(Comparator.comparing(TherapyPassEntity::getDateTime));

        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formatDateTime = dateTime.format(formatter2);

        LocalDateTime therapyDateTime = LocalDateTime.parse(formatDateTime, formatter2);

        List<TherapyPassEntity> upToDateTherapyList = new ArrayList<>();

        for(TherapyPassEntity therapyPassEntity : therapyPassEntityList){
            if(!therapyPassEntity.getDateTime().isBefore(therapyDateTime)) {
                upToDateTherapyList.add(therapyPassEntity);
            }
        }

            return upToDateTherapyList;

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

        for (TherapyPassEntity therapyPassEntity : therapyPassEntityList) {

            if (therapyPassEntity.getTherapyId().equals(therapyId)) {

                therapyPassEntity.setTitle(therapyPassFromAppDto.getTitle());
                therapyPassEntity.setDescription(therapyPassFromAppDto.getDescription());
                therapyPassRespository.save(therapyPassEntity);

                return therapyPassEntity;
            }

        }
        throw new EntityNotFoundException("No Therapy found!");
    }


    @Transactional
    public TherapyPassEntity deleteTherapy(String userName, Long therapyId) {
        var user = userService.getUser(userName);

        List<TherapyPassEntity> therapyPassEntityList = user.getTherapyEntries();

        for (TherapyPassEntity therapyPassEntity : therapyPassEntityList) {
            if (therapyPassEntity.getTherapyId().equals(therapyId)) {
                therapyPassEntityList.remove(therapyPassEntity);
                return new TherapyPassEntity();
            }

        }
        throw new EntityNotFoundException("No Therapy found!");
    }


    private TherapyPassEntity map(TherapyPassFromAppDto therapyPassFromAppDto) {

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        System.out.println(therapyPassFromAppDto.getDate());

        LocalDateTime dateTime = LocalDateTime.parse(therapyPassFromAppDto.getDate(), formatter1);

        System.out.println(dateTime);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String formatDateTime = dateTime.format(formatter2);

        System.out.println(formatDateTime);

        LocalDateTime therapyDateTime = LocalDateTime.parse(formatDateTime, formatter2);

        System.out.println(therapyDateTime);


        return TherapyPassEntity.builder()
                .dateTime(therapyDateTime)
                .date(formatDateTime)
                .title(therapyPassFromAppDto.getTitle())
                .description(therapyPassFromAppDto.getDescription()).build();
    }

}
