package de.tomze.backend.repository;


import de.tomze.backend.model.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface BlogRepository extends JpaRepository<BlogEntity, Long> {


    @Override
    List<BlogEntity> findAll();
}

