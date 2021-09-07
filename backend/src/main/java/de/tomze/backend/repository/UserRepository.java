package de.tomze.backend.repository;

import de.tomze.backend.model.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    @Override
    List<UserEntity> findAll();
}
