package de.tomze.backend.controller;


import de.tomze.backend.api.InformationFromAppDto;
import de.tomze.backend.api.InformationToAppDto;
import de.tomze.backend.model.InformationEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import javax.ws.rs.NotAuthorizedException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/tomze/info")
@CrossOrigin
public class InformationController {

    private final InformationService informationService;

    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }


    @PostMapping()
    public ResponseEntity<InformationToAppDto> createInfo(@AuthenticationPrincipal UserEntity authUser, @RequestBody InformationFromAppDto informationFromAppDto){

        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("User must not create Information");
        }

        InformationEntity informationEntity = informationService.createInfo(informationFromAppDto);

        InformationToAppDto informationToAppDto = map(informationEntity);

        return ok(informationToAppDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<InformationToAppDto>> getAllInfos(){

        List<InformationEntity> informationEntityList = informationService.getAllInfos();

        List<InformationToAppDto> informationToAppDtoList = mapList(informationEntityList);

        return ok(informationToAppDtoList);
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<InformationToAppDto> getInfo(@PathVariable Long id){

        InformationEntity informationEntity = informationService.getInfo(id);

        InformationToAppDto foundInformationToAppDto = map(informationEntity);

        return ok(foundInformationToAppDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<InformationToAppDto> updateInfo(@AuthenticationPrincipal UserEntity authUser, @PathVariable Long id, @RequestBody InformationFromAppDto informationFromAppDto){

        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("User must not update Information");
        }

        InformationEntity updateInformationEntity = informationService.updateInfo(id, informationFromAppDto);

        InformationToAppDto updateInformationToAppDto = map(updateInformationEntity);

        return ok(updateInformationToAppDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InformationToAppDto> deleteInfo(@AuthenticationPrincipal UserEntity authUser, @PathVariable Long id){

        if(authUser.getRole().equals("user")){
            throw new NotAuthorizedException("User must not delete Information");
        }

        InformationEntity deleteInformationEntity = informationService.deleteInfo(id);

        InformationToAppDto deleteInformationToAppDto = map(deleteInformationEntity);

        return ok(deleteInformationToAppDto);
    }


    private InformationToAppDto map(InformationEntity informationEntity){
        return InformationToAppDto.builder()
                .id(informationEntity.getId())
                .title(informationEntity.getTitle())
                .info(informationEntity.getInfo()).build();
    }

    private List<InformationToAppDto> mapList(List<InformationEntity> informationEntityList) {

        List<InformationToAppDto> informationToAppDtoList = new ArrayList<>();

        for(InformationEntity informationEntity : informationEntityList){
            InformationToAppDto informationToAppDto = map(informationEntity);
            informationToAppDtoList.add(informationToAppDto);
        }

        return informationToAppDtoList;
    }


}
