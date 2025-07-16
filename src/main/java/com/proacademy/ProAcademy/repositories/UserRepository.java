package com.proacademy.proacademy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proacademy.proacademy.models.User;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Transactional(readOnly = true)    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);    
}
