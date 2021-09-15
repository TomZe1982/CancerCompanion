package de.tomze.backend.repository;

import de.tomze.backend.model.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

}
