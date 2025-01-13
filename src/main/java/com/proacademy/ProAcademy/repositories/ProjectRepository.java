package com.proacademy.proacademy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proacademy.proacademy.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
