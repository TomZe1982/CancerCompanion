package de.tomze.backend.repository;

import de.tomze.backend.model.InformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<InformationEntity, Long> {


}
