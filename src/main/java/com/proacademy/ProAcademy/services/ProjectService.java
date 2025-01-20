package com.proacademy.proacademy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.Project.CreateProject;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.repositories.ProjectRepository;

import jakarta.validation.Valid;

@Service // // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class ProjectService {

    @Autowired // Injeta automaticamente a dependência do repositório ProjectRepository.
    private ProjectRepository projectRepository;

    @Autowired  // Declara a dependência do serviço UserService, usada para operações relacionadas ao usuário.
    private UserService userService;

    public Project findById(Long id) {
        Optional<Project> project = this.projectRepository.findById(id); // Busca o projeto no repositório.
        return project.orElseThrow(() -> new RuntimeException(
        "Projeto não encontrado! Id: " + id + ", Tipo: " + Project.class.getName()
        ));
    }

    public List<Project> findByAllByUserId(Long userId) {
        List<Project> projects = this.projectRepository.findByUser_Id(userId);
        return projects;
    }

    @Transactional
    public Project createProject(@Valid @Validated(CreateProject.class) Project obj) {
        User user = this.userService.findById(obj.getUser().getId()); // Busca o usuário associado ao projeto.
        obj.setId(null); // Garante que o ID será gerado automaticamente ao salvar.
        obj.setUser(user); // Associa o usuário ao projeto.
        obj = this.projectRepository.save(obj); // Salva o projeto no banco de dados.
        return obj; // Retorna o projeto persistido.
    }

    @Transactional
    public Project updateProject(Project obj) {
        Project newObj = findById(obj.getId()); // Busca o projeto pelo ID.
        if (obj.getTitle() != null) {
            newObj.setTitle(obj.getTitle()); // Atualiza o título do projeto.
        }  if (obj.getDescription() != null) {
            newObj.setDescription(obj.getDescription()); // Atualiza a descrição projeto.
        } if (obj.getInitialDate()!= null) {
            newObj.setInitialDate(obj.getInitialDate());  // Atualiza a data inicial do projeto.
        } if (obj.getFinishDate() != null) {
            newObj.setFinishDate(obj.getFinishDate());  //Atualiza a data final do projeto 
        } newObj.setStatusActive(obj.isStatusActive()); // Atualiza se o projeto está concluido ou não.
        return this.projectRepository.save(newObj); // Salva as alterações no banco de dados.
    }
    
    public void deleteProject(Long id) {
        findById(id); // Verifica se o projeto existe antes de tentar excluir.
        try {
            this.projectRepository.deleteById(id); // Tenta excluir o projeto pelo ID.
        } catch (Exception e) {
            // Lança exceção caso existam relacionamentos que impeçam a exclusão.
            throw new RuntimeException("Não é possível excluir. Existem entidades relacionadas!");
        }
    }
}
