package de.tomze.backend.repository;

import de.tomze.backend.model.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

/*    Optional<VideoEntity> findVideoEntityByVid_id();*/


    @Override
    List<VideoEntity> findAll();

}
