package com.proacademy.proacademy.services;

import com.proacademy.proacademy.dtos.UserDTO;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.models.enums.ProfileEnum;
import com.proacademy.proacademy.repositories.UserRepository;
import com.proacademy.proacademy.security.UserSpringSecurity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserDTO> listAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    public UserDTO save(UserDTO dto) {
        User entity = dto.toEntity();
        if (entity.getPassword() != null && !entity.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        User saved = userRepository.save(entity);
        return UserDTO.fromEntity(saved);
    }

    public void createUser(User user) {
        user.setId(null);
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getProfiles() == null || user.getProfiles().isEmpty()) {
            user.setProfiles(Set.of(ProfileEnum.USER));
        }
        userRepository.save(user);
    }

    public void updateUser(User user) {
        User existing = findById(user.getId());
        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existing.setProfiles(user.getProfiles());
        userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
        return new UserSpringSecurity(user);
    }
}
