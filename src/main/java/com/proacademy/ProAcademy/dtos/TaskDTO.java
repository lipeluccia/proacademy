package com.proacademy.proacademy.dtos;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private boolean statusActive;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isStatusActive() { return statusActive; }
    public void setStatusActive(boolean statusActive) { this.statusActive = statusActive; }
}