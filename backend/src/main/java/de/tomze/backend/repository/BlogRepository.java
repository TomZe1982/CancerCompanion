package de.tomze.backend.repository;


import de.tomze.backend.model.BlogEntity;
import de.tomze.backend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BlogRepository extends JpaRepository<BlogEntity, Long> {

    List<BlogEntity> findAllById(Long id);

    @Override
    List<BlogEntity> findAll();
}
