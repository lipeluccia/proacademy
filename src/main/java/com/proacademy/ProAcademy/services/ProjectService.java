package com.proacademy.proacademy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.repositories.ProjectRepository;

@Service // // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class ProjectService {

    @Autowired // Injeta automaticamente a dependência do repositório ProjectRepository.
    private ProjectRepository projectRepository;

    @Autowired  // Declara a dependência do serviço UserService, usada para operações relacionadas ao usuário.
    private UserService userService;

    /**
     * Busca um projeto pelo ID.
     * @param id ID do projeto a ser buscado.
     * @return Retorna o objeto Project encontrado.
     * @throws RuntimeException Caso o projeto não seja encontrado.
     */
    public Project findById(Long id) {
        Optional<Project> project = this.projectRepository.findById(id); // Busca o projeto no repositório.
        return project.orElseThrow(() -> new RuntimeException(
        "Projeto não encontrado! Id: " + id + ", Tipo: " + Project.class.getName()
        ));
    }

    /**
     * Cria um novo projeto e associa a um usuário existente.
     * @param obj Objeto Project contendo as informações do projeto a ser criado.
     * @return Retorna o projeto salvo no banco de dados.
     * A anotação @Transactional garante que a operação será atômica.
     */
    @Transactional
    public Project create(Project obj) {
        User user = this.userService.findById(obj.getUser().getId()); // Busca o usuário associado ao projeto.
        obj.setId(null); // Garante que o ID será gerado automaticamente ao salvar.
        obj.setUser(user); // Associa o usuário ao projeto.
        obj = this.projectRepository.save(obj); // Salva o projeto no banco de dados.
        return obj; // Retorna o projeto persistido.
    }

    /**
     * Atualiza os dados de um projeto existente.
     * @param obj Objeto Project contendo o ID e os novos dados.
     * @return Retorna o projeto atualizado.
     * A anotação @Transactional garante que a operação será realizada como uma transação.
     */
    @Transactional
    public Project update(Project obj) {
        Project newObj = findById(obj.getId()); // Busca o projeto pelo ID.
        newObj.setTitle(obj.getTitle()); // Atualiza o título do projeto.
        return this.projectRepository.save(newObj); // Salva as alterações no banco de dados.
    }

    /**
     * Exclui um projeto pelo ID.
     * @param id ID do projeto a ser excluído.
     * @throws RuntimeException Caso o projeto tenha entidades relacionadas que impeçam a exclusão.
     */
    public void delete(Long id) {
        findById(id); // Verifica se o projeto existe antes de tentar excluir.
        try {
            this.projectRepository.deleteById(id); // Tenta excluir o projeto pelo ID.
        } catch (Exception e) {
            // Lança exceção caso existam relacionamentos que impeçam a exclusão.
            throw new RuntimeException("Não é possível excluir. Existem entidades relacionadas!");
        }
    }
}
