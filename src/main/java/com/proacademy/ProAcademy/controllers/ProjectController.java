package com.proacademy.proacademy.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.services.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable Long id) {
        Project obj = this.projectService.findById(id); 
        return ResponseEntity.ok(obj);  
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> findAllByUserId(@PathVariable Long userId){
        List<Project> objs = this.projectService.findByAllByUserId(userId);
        return ResponseEntity.ok().body(objs);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Project obj){
        this.projectService.createProject(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody Project obj, @PathVariable Long id){
        obj.setId(id);
        this.projectService.updateProject(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
} 
