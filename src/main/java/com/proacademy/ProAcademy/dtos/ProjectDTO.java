package com.proacademy.proacademy.dtos;

import com.proacademy.proacademy.models.Project;
import java.time.LocalDate;

public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate finishDate;
    private Long userId;

    public ProjectDTO() {}

    public ProjectDTO(Long id, String title, String description, LocalDate finishDate, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finishDate = finishDate;
        this.userId = userId;
    }

    public ProjectDTO(Project p) {
        this.id = p.getId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        this.finishDate = p.getFinishDate();
        this.userId = (p.getUser() != null ? p.getUser().getId() : null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getFinishDate() { return finishDate; }
    public void setFinishDate(LocalDate finishDate) { this.finishDate = finishDate; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
