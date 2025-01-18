package com.proacademy.proacademy.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public static final String TABLE_NAME = "user";

    public interface CreateUser {}
    public interface UpdateUser {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class})
    @NotEmpty(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, max = 100)
    private String fullName;

    @Column(name = "birthday", nullable = false)
    @NotNull(groups = CreateUser.class)
    private LocalDate birthday;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUser.class)
    @NotEmpty(groups = CreateUser.class)
    @Email(groups = {CreateUser.class, UpdateUser.class})
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", length = 16, nullable = false)
    @NotNull(groups = {CreateUser.class})
    @NotEmpty(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6, max = 16)
    private String password;

    @Column(name = "course", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class})
    @NotEmpty(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, max = 100)
    private String course;

    @Column(name = "university", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class})
    @NotEmpty(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, max = 100)
    private String university;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<Project>();

    @Column(name = "creationDate", nullable = false, updatable = false)
    @NotNull
    private LocalDate creationDate;

    public User() {
        this.creationDate = LocalDate.now();
    }

    public User(Long id, String fullName, LocalDate birthday, String email, String password, String course, String university) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.course = course;
        this.university = university;
        this.creationDate = LocalDate.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @JsonIgnore
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        return id != null && id.equals(other.id);
    }
}
