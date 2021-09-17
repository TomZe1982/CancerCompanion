package de.tomze.backend.repository;

import de.tomze.backend.model.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<VideoEntity, Long> {


    @Override
    List<VideoEntity> findAll();

}
