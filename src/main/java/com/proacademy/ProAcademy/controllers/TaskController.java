package com.proacademy.proacademy.controllers;

import com.proacademy.proacademy.dtos.TaskDTO;
import com.proacademy.proacademy.models.Task;
import com.proacademy.proacademy.services.ProjectService;
import com.proacademy.proacademy.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @PostMapping("/dto")
    public ResponseEntity<TaskDTO> save(@Valid @RequestBody TaskDTO dto) {
        return ResponseEntity.ok(taskService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> findAllByProject(@PathVariable Long projectId){
        projectService.findById(projectId);
        return ResponseEntity.ok(taskService.findAllByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj){
        taskService.createTask(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Task obj, @PathVariable Long id){
        obj.setId(id);
        taskService.updateTask(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}