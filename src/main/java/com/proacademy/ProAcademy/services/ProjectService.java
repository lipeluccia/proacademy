package com.proacademy.proacademy.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.dtos.ProjectDTO;
import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.Project.CreateProject;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.repositories.ProjectRepository;
import com.proacademy.proacademy.services.exceptions.DataBindingViolationException;
import com.proacademy.proacademy.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    public Project findById(Long id) {
        Optional<Project> project = this.projectRepository.findById(id);
        return project.orElseThrow(() -> new ObjectNotFoundException(
                "Project not found! Id: " + id + ", Type: " + Project.class.getName()
        ));
    }

    public List<Project> findAllByUserId(Long userId) {
        return this.projectRepository.findByUser_Id(userId);
    }

    @Transactional
    public Project createProject(@Valid @Validated(CreateProject.class) Project obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        return this.projectRepository.save(obj);
    }

    @Transactional
    public Project updateProject(Project obj) {
        Project updated = findById(obj.getId());
        if (obj.getTitle() != null) {
            updated.setTitle(obj.getTitle());
        }  if (obj.getDescription() != null) {
            updated.setDescription(obj.getDescription());
        } if (obj.getInitialDate()!= null) {
            updated.setInitialDate(obj.getInitialDate());
        } if (obj.getFinishDate() != null) {
            updated.setFinishDate(obj.getFinishDate());
        }
        updated.setStatusActive(obj.isStatusActive());
        return this.projectRepository.save(updated);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Project not found"));
        if (!project.getTasks().isEmpty()) {
            throw new DataBindingViolationException("Cannot delete project with related tasks.");
        }
        projectRepository.delete(project);
    }

    // Methods using DTO
    public List<ProjectDTO> findAll() {
        return projectRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ProjectDTO save(ProjectDTO dto) {
        Project p = new Project();
        p.setTitle(dto.getName());
        p.setDescription(dto.getDescription());
        return toDTO(projectRepository.save(p));
    }

    private ProjectDTO toDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getTitle());
        dto.setDescription(project.getDescription());
        return dto;
    }
}
