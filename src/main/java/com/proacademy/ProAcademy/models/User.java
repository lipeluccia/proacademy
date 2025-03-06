package com.proacademy.proacademy.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.proacademy.proacademy.models.enums.ProfileEnum;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class})
    @NotEmpty(groups = {CreateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 6, max = 60)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Project> projects = new ArrayList<Project>();

    @Column(name = "creationDate", nullable = false, updatable = false)
    @NotNull
    private LocalDate creationDate = LocalDate.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles() {
        return profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profile) {
        profiles.add(profile.getCode());
    }
}