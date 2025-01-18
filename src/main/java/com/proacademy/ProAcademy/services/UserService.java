package com.proacademy.proacademy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.models.User.CreateUser;
import com.proacademy.proacademy.repositories.UserRepository;

import jakarta.validation.Valid;

@Service    // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class UserService {

    @Autowired  // Injeta automaticamente a dependência do repositório UserRepository.
    private UserRepository userRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id); // Busca o usuário no repositório.
        return user.orElseThrow(() -> new RuntimeException(
        "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));  
    }

    @Transactional
    public User createUser(@Valid @Validated(CreateUser.class)User obj) {
        obj.setId(null);    // Garante que o ID será gerado automaticamente ao salvar.
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
        } if (obj.getCourse() != null) {
            newObj.setCourse(obj.getCourse());
        } if (obj.getUniversity() != null) {
            newObj.setUniversity(obj.getUniversity());
        }
        return this.userRepository.save(newObj);    // Salva as alterações no banco.
    }

    public void deleteUser(Long id){
        findById(id);   // Verifica se o usuário existe antes de tentar excluir.
        try {
            this.userRepository.deleteById(id);     // Tenta excluir o usuário pelo ID.
        } catch (Exception e) {
             // Lança exceção caso existam relacionamentos que impeçam a exclusão.
            throw new RuntimeException("Não é possivel excluir. Existem entidades relacionadas!");
        }
    }
}
