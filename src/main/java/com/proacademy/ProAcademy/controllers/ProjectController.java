package com.proacademy.proacademy.controllers;

import com.proacademy.proacademy.dtos.ProjectDTO;
import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.services.ProjectService;
import com.proacademy.proacademy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @PostMapping("/dto")
    public ResponseEntity<ProjectDTO> save(@Valid @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> findAllByUserId(@PathVariable Long userId){
        userService.findById(userId);
        return ResponseEntity.ok(projectService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Project obj){
        projectService.createProject(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody Project obj, @PathVariable Long id){
        obj.setId(id);
        projectService.updateProject(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
