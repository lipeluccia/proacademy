package com.proacademy.proacademy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.dtos.UserDTO;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.models.User.CreateUser;
import com.proacademy.proacademy.models.enums.ProfileEnum;
import com.proacademy.proacademy.repositories.UserRepository;
import com.proacademy.proacademy.services.exceptions.DataBindingViolationException;
import com.proacademy.proacademy.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
        "User not found! Id: " + id + ", Type: " + User.class.getName()
        ));  
    }

    @Transactional
    public User createUser(@Valid @Validated(CreateUser.class)User obj) {
        obj.setId(null);
        obj.setPassword(this.passwordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.USER).map(ProfileEnum::getCode).collect(Collectors.toSet()));
        obj.setCreationDate(obj.getCreationDate() == null ? LocalDate.now() : obj.getCreationDate());
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User updateUser(User obj){
        User newObj = findById(obj.getId());
        if (obj.getFullName() != null) {
            newObj.setFullName(obj.getFullName());
        } if (obj.getBirthday() != null) {
            newObj.setBirthday(obj.getBirthday());            
        } if (obj.getPassword() != null) {
            newObj.setPassword(this.passwordEncoder.encode(obj.getPassword()));  
        } if (obj.getCourse() != null) {
            newObj.setCourse(obj.getCourse());
        } if (obj.getUniversity() != null) {
            newObj.setUniversity(obj.getUniversity());
        }
        return this.userRepository.save(newObj);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        if (!user.getProjects().isEmpty()) {
            throw new DataBindingViolationException("Cannot delete user with associated projects.");
        }

        userRepository.delete(user);
    }

    // DTO METHODS
    public List<UserDTO> listAll() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO dto) {
        User user = new User();
        user.setFullName(dto.getName());
        user.setEmail(dto.getEmail());
        return toDTO(userRepository.save(user));
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getFullName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
