package de.tomze.backend.controller;


import de.tomze.backend.api.InformationFromAppDto;
import de.tomze.backend.api.InformationToAppDto;
import de.tomze.backend.api.UserToAppDto;
import de.tomze.backend.model.InformationEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<InformationToAppDto> createInfo(@AuthenticationPrincipal UserEntity authUser, @RequestBody InformationFromAppDto informationFromAppDto) throws IllegalAccessException {

        if(authUser.getRole().equals("user")){
            throw new IllegalAccessException("User must not create Information");
        }

        InformationEntity informationEntity = informationService.createInfo(informationFromAppDto);

        InformationToAppDto informationToAppDto = map(informationEntity);

        return ok(informationToAppDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformationToAppDto> getInfo(@AuthenticationPrincipal UserEntity authUser, @PathVariable Long id){

        InformationEntity informationEntity = informationService.getInfo(id);

        InformationToAppDto foundInformationToAppDto = map(informationEntity);

        return ok(foundInformationToAppDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<InformationToAppDto> updateInfo(@AuthenticationPrincipal UserEntity authUser, @PathVariable Long id, @RequestBody InformationFromAppDto informationFromAppDto) throws IllegalAccessException {

        if(authUser.getRole().equals("user")){
            throw new IllegalAccessException("User must not update Information");
        }

        InformationEntity updateInformationEntity = informationService.updateInfo(id, informationFromAppDto);

        InformationToAppDto updateInformationToAppDto = map(updateInformationEntity);

        return ok(updateInformationToAppDto);

    }


    private InformationToAppDto map(InformationEntity informationEntity){
        return InformationToAppDto.builder()
                .id(informationEntity.getId())
                .info(informationEntity.getInfo()).build();
    }


}
