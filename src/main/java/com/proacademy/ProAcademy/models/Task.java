package com.proacademy.proacademy.models;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    @NotNull
    private Project project;

    @Column(name = "title", length = 60, nullable = false)
    @NotNull
    private String title;

    @Column(name = "description", length = 255)
    @Size(min = 1, max = 255)
    private String description;

    @Column(name = "initial_date", nullable = false)
    @NotNull
    private LocalDate initialDate;

    @Column(name = "finish_date", nullable = false)
    @NotNull
    private LocalDate finishDate;

    @Column(name = "status_active", nullable = false)
    private boolean statusActive;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    // Construtor padrão
    public Task() {
        this.statusActive = true;
        this.creationDate = LocalDate.now();
    }

    // Construtor completo
    public Task(Long id, Project project, String title, String description, LocalDate initialDate, LocalDate finishDate, LocalDate creationDate) {
        this.id = id;
        this.project = project;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.finishDate = finishDate;
        this.statusActive = true;
        this.creationDate = creationDate != null ? creationDate : LocalDate.now();
    }

    // Getters e Setters
    public static String getTableName() {
        return TABLE_NAME;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isStatusActive() {
        return statusActive;
    }

    public void setStatusActive(boolean statusActive) {
        this.statusActive = statusActive;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, project, title, description, initialDate, finishDate, statusActive, creationDate);
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return statusActive == other.statusActive &&
               Objects.equals(id, other.id) &&
               Objects.equals(project, other.project) &&
               Objects.equals(title, other.title) &&
               Objects.equals(description, other.description) &&
               Objects.equals(initialDate, other.initialDate) &&
               Objects.equals(finishDate, other.finishDate) &&
               Objects.equals(creationDate, other.creationDate);
    }
}