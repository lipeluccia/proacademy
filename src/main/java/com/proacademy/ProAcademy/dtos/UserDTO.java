package com.proacademy.proacademy.dtos;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.proacademy.proacademy.models.User;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;
    private String university;
    private String course;
    private LocalDate birthday;

    public UserDTO() {}

    public UserDTO(Long id, String fullName, String email,
                   String university, String course, LocalDate birthday) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.university = university;
        this.course = course;
        this.birthday = birthday;
    }

    public static UserDTO fromEntity(User u) {
        if (u == null) return null;
        return new UserDTO(
            u.getId(),
            u.getFullName(),
            u.getEmail(),
            u.getUniversity(),
            u.getCourse(),
            u.getBirthday()
        );
    }

    public User toEntity() {
        User u = new User();
        u.setId(this.id);
        u.setFullName(this.fullName);
        u.setEmail(this.email);
        u.setUniversity(this.university);
        u.setCourse(this.course);
        u.setBirthday(this.birthday);
        return u;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
}
