package com.proacademy.proacademy.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.models.User.CreateUser;
import com.proacademy.proacademy.models.enums.ProfileEnum;
import com.proacademy.proacademy.repositories.UserRepository;
import com.proacademy.proacademy.services.exceptions.DataBindingViolationException;
import com.proacademy.proacademy.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service    // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired  // Injeta automaticamente a dependência do repositório UserRepository.
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id); // Busca o usuário no repositório.
        return user.orElseThrow(() -> new ObjectNotFoundException(
        "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));  
    }

    @Transactional
    public User createUser(@Valid @Validated(CreateUser.class)User obj) {
        obj.setId(null);    // Garante que o ID será gerado automaticamente ao salvar.
        obj.setPassword(this.passwordEncoder.encode(obj.getPassword()));    // Encripta a senha do usuário.
        obj.setProfiles(Stream.of(ProfileEnum.USER).map(ProfileEnum::getCode).collect(Collectors.toSet()));    // Define o perfil do usuário.
        obj.setCreationDate(obj.getCreationDate() == null ? LocalDate.now() : obj.getCreationDate());   // Define a data de criação do usuário.
        obj = this.userRepository.save(obj);    // Salva o usuário no banco.
        return obj;
    }

    @Transactional
    public User updateUser(User obj){
        User newObj = findById(obj.getId());    // Busca o usuário pelo ID
        if (obj.getFullName() != null) {
            newObj.setFullName (obj.getFullName());
        } if (obj.getBirthday() != null) {
            newObj.setBirthday(obj.getBirthday());            
        } if (obj.getPassword() != null) {
            newObj.setPassword(obj.getPassword());
            newObj.setPassword(this.passwordEncoder.encode(obj.getPassword()));  
        } if (obj.getCourse() != null) {
            newObj.setCourse(obj.getCourse());
        } if (obj.getUniversity() != null) {
            newObj.setUniversity(obj.getUniversity());
        }
        return this.userRepository.save(newObj);    // Salva as alterações no banco.
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    
        if (!user.getProjects().isEmpty()) {
            throw new DataBindingViolationException("Não é possível excluir o usuário, pois há projetos vinculados.");
        }

        userRepository.delete(user);
    }
    
}
