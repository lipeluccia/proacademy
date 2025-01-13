package com.proacademy.proacademy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proacademy.proacademy.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
