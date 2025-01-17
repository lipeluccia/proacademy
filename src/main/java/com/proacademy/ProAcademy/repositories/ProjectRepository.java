package com.proacademy.proacademy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proacademy.proacademy.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUser_Id(Long id);
}
