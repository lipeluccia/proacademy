package com.proacademy.proacademy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proacademy.proacademy.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
