package com.proacademy.proacademy.services;
import com.proacademy.proacademy.dtos.ProjectDTO;
import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.repositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public List<ProjectDTO> findAllDTO() {
        return projectRepository.findAll().stream().map(ProjectDTO::new).toList();
    }

    @Override
    public ProjectDTO findDTOById(Long id) {
        Project p = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));
        return new ProjectDTO(p);
    }

    @Override
    public List<ProjectDTO> findAllDTOByUserId(Long userId) {
        return projectRepository.findAllByUserId(userId).stream().map(ProjectDTO::new).toList();
    }

    @Override
    public ProjectDTO save(ProjectDTO dto) {
        User user = null;
        if (dto.getUserId() != null) {
            user = userService.findById(dto.getUserId());
        }
        Project entity = toEntity(dto, user);
        Project saved = projectRepository.save(entity);
        return new ProjectDTO(saved);
    }

    private Project toEntity(ProjectDTO dto, User user) {
        Project p = new Project();
        p.setId(dto.getId());
        p.setTitle(dto.getTitle());
        p.setDescription(dto.getDescription());
        p.setFinishDate(dto.getFinishDate());
        p.setUser(user);
        return p;
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));
    }

    @Override
    public void createProject(Project obj) {
        obj.setId(null);
        projectRepository.save(obj);
    }

    @Override
    public void updateProject(Project obj) {
        if (obj.getId() == null || !projectRepository.existsById(obj.getId())) {
            throw new EntityNotFoundException("Project not found: " + obj.getId());
        }
        projectRepository.save(obj);
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}
