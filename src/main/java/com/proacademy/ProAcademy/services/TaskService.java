package com.proacademy.proacademy.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.dtos.TaskDTO;
import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.Task;
import com.proacademy.proacademy.models.Task.CreateTask;
import com.proacademy.proacademy.repositories.TaskRepository;
import com.proacademy.proacademy.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Task not found! Id: " + id + ", Type: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByProjectId(Long projectId) {
        return this.taskRepository.findByProject_Id(projectId);
    }

    @Transactional
    public Task createTask(@Valid @Validated(CreateTask.class) Task obj) {
        Project project = this.projectService.findById(obj.getProject().getId());
        obj.setId(null);
        obj.setProject(project);
        return this.taskRepository.save(obj);
    }

    @Transactional
    public Task updateTask(Task obj) {
        Task updated = findById(obj.getId());
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
        return this.taskRepository.save(updated);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    // Methods using DTO
    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TaskDTO save(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatusActive(dto.isStatusActive());
        return toDTO(taskRepository.save(task));
    }

    private TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatusActive(task.isStatusActive());
        return dto;
    }
}
