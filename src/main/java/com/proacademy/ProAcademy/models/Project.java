package com.proacademy.proacademy.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = Project.TABLE_NAME)
public class Project {
    public static final String TABLE_NAME = "project";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    private User user;

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

    @Column(name = "status_Active", nullable = false)
    private boolean statusActive;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

   // Construtor padrão
    public Project() {
    this.statusActive = true;
    this.creationDate = LocalDate.now();
}

    // Construtor completo
    public Project(Long id, User user, String title, String description, LocalDate initialDate, LocalDate finishDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.finishDate = finishDate;
        this.statusActive = true;
        this.creationDate = LocalDate.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, user, title, description, initialDate, finishDate, statusActive, creationDate);
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
        Project other = (Project) obj;
        return statusActive == other.statusActive &&
               Objects.equals(id, other.id) &&
               Objects.equals(user, other.user) &&
               Objects.equals(title, other.title) &&
               Objects.equals(description, other.description) &&
               Objects.equals(initialDate, other.initialDate) &&
               Objects.equals(finishDate, other.finishDate) &&
               Objects.equals(creationDate, other.creationDate);
    }
}