package com.proacademy.proacademy.services;

import com.proacademy.proacademy.dtos.ProjectDTO;
import com.proacademy.proacademy.models.Project;
import java.util.List;

public interface ProjectService {
    List<ProjectDTO> findAllDTO();
    ProjectDTO findDTOById(Long id);
    List<ProjectDTO> findAllDTOByUserId(Long userId);
    ProjectDTO save(ProjectDTO dto);
    Project findById(Long id);
    void createProject(Project obj);
    void updateProject(Project obj);
    void deleteProject(Long id);
}
