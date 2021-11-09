package de.tomze.backend.controller;

import de.tomze.backend.api.TherapyPassFromAppDto;
import de.tomze.backend.api.TherapyPassToAppDto;
import de.tomze.backend.model.TherapyPassEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.TherapyPassService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin
@RequestMapping("api/tomze/therapy")
public class TherapyPassController {

    private final TherapyPassService therapyPassService;

    public TherapyPassController(TherapyPassService therapyPassService) {
        this.therapyPassService = therapyPassService;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<TherapyPassToAppDto>> getAllTherapies(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName) {
        if(authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("You must not see other Users Therapies");
        }
        List<TherapyPassEntity> therapyPassEntityList = therapyPassService.getAllTherapies(userName);

        if(therapyPassEntityList.size() < 1){
            throw new EntityNotFoundException("No entries found!");
        }

        List<TherapyPassToAppDto> therapyPassDtoList = mapList(therapyPassEntityList);

        return ok(therapyPassDtoList);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<TherapyPassToAppDto> createNewTherapy(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @RequestBody TherapyPassFromAppDto therapyPassFromAppDto){

        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("Only admin can create therapies!");
        }

        TherapyPassEntity newTherapyPassEntity = therapyPassService.createNewTherapy(userName, therapyPassFromAppDto);

        TherapyPassToAppDto newTherapyToAppDto = map(newTherapyPassEntity);

        return ok(newTherapyToAppDto);
    }

    @PutMapping("/{userName}/{therapyId}")
    public ResponseEntity<TherapyPassToAppDto> updateTherapy(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @PathVariable Long therapyId, @RequestBody TherapyPassFromAppDto therapyPassFromAppDto){

        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("Only admin can update therapies!");
        }

        TherapyPassEntity updatedTherapyEntity = therapyPassService.updateTherapy(userName, therapyId, therapyPassFromAppDto);

        TherapyPassToAppDto updatedTherapyPassToAppDto = map(updatedTherapyEntity);

        return ok(updatedTherapyPassToAppDto);
    }

    @DeleteMapping("/{userName}/{therapyId}")
    public ResponseEntity<TherapyPassToAppDto> updateTherapy(@AuthenticationPrincipal UserEntity authUser, @PathVariable String userName, @PathVariable Long therapyId) {

        if(authUser.getRole().equals("user") || authUser.getUserName().equals(userName)){
            throw new NotAuthorizedException("Only admin can delete therapies!");
        }

        TherapyPassEntity deletedTherapyEntity = therapyPassService.deleteTherapy(userName, therapyId);

        TherapyPassToAppDto deletedTherapyPassToAppDto = map(deletedTherapyEntity);

        return ok(deletedTherapyPassToAppDto);
    }


    private List<TherapyPassToAppDto> mapList(List<TherapyPassEntity> therapyPassEntityList) {

        List<TherapyPassToAppDto> therapyPassDtoList = new ArrayList<>();

        for (TherapyPassEntity therapyPassEntity : therapyPassEntityList) {
            TherapyPassToAppDto therapyPassDto = map(therapyPassEntity);
            therapyPassDtoList.add(therapyPassDto);
        }

        return therapyPassDtoList;
    }

    private TherapyPassToAppDto map(TherapyPassEntity therapyPassEntity) {

        return TherapyPassToAppDto.builder()
                .therapyId(therapyPassEntity.getTherapyId())
                .date(therapyPassEntity.getDate())
                .title(therapyPassEntity.getTitle())
                .description(therapyPassEntity.getDescription()).build();
    }

}



