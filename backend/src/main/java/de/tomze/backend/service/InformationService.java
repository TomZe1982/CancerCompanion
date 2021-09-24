package de.tomze.backend.service;

import de.tomze.backend.api.InformationFromAppDto;
import de.tomze.backend.model.InformationEntity;
import de.tomze.backend.repository.InformationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    private final InformationRepository informationRepository;

    public InformationService(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    public InformationEntity createInfo(InformationFromAppDto informationFromAppDto) {
        InformationEntity newInformationEntity = map(informationFromAppDto);

        informationRepository.save(newInformationEntity);

        return newInformationEntity;
    }

    public InformationEntity getInfo(Long id) {
        Optional<InformationEntity> informationEntityOptional = informationRepository.findById(id);

        if(informationEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Info not found");
        }

        return informationEntityOptional.get();

    }


    public InformationEntity updateInfo(Long id, InformationFromAppDto informationFromAppDto) {
        InformationEntity informationEntityToUpdate = getInfo(id);

        informationEntityToUpdate.setInfo(informationFromAppDto.getInfo());
        informationEntityToUpdate.setTitle(informationFromAppDto.getTitle());

        informationRepository.save(informationEntityToUpdate);

        return informationEntityToUpdate;
    }

    public List<InformationEntity> getAllInfos() {

        return informationRepository.findAll();
    }


    private InformationEntity map(InformationFromAppDto informationFromAppDto) {
        return InformationEntity.builder()
                .info(informationFromAppDto.getInfo())
                .title(informationFromAppDto.getTitle()).build();
    }
}
