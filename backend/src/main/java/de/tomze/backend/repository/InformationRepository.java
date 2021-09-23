package de.tomze.backend.repository;

import de.tomze.backend.model.InformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InformationRepository extends JpaRepository<InformationEntity, Long> {

    Optional<InformationEntity> findById(Long id);

    @Override
    List<InformationEntity> findAll();


}
