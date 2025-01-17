package com.proacademy.proacademy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.Task;
import com.proacademy.proacademy.repositories.TaskRepository;

@Service // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class TaskService {

    @Autowired // Injeta automaticamente a dependência do repositório TaskRepository.
    private TaskRepository taskRepository;

    @Autowired // Injeta automaticamente a dependência do serviço ProjectService para operações relacionadas a projetos.
    private ProjectService projectService;

    /**
     * Busca uma tarefa pelo ID.
     * @param id ID da tarefa a ser buscada.
     * @return Retorna o objeto Task encontrado.
     * @throws RuntimeException Caso a tarefa não seja encontrada.
     */
    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id); // Busca a tarefa no repositório.
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    /**
     * Cria uma nova tarefa e associa a um projeto existente.
     * @param obj Objeto Task contendo as informações da nova tarefa.
     * @return Retorna a tarefa salva no banco de dados.
     * A anotação @Transactional garante que a operação será atômica.
     */
    @Transactional
    public Task createTask(Task obj) {
        Project project = this.projectService.findById(obj.getProject().getId()); // Busca o projeto associado à tarefa.
        obj.setId(null); // Garante que o ID será gerado automaticamente ao salvar.
        obj.setProject(project); // Associa o projeto à tarefa.
        obj = this.taskRepository.save(obj); // Salva a tarefa no banco de dados.
        return obj; // Retorna a tarefa persistida.
    }

    /**
     * Atualiza os dados de uma tarefa existente.
     * @param obj Objeto Task contendo o ID e os novos dados da tarefa.
     * @return Retorna a tarefa atualizada.
     * A anotação @Transactional garante que a operação será realizada como uma transação.
     */
    @Transactional
    public Task updateTask(Project obj) {
        Task newObj = findById(obj.getId()); // Busca o projeto pelo ID.
        newObj.setTitle(obj.getTitle() != null ? obj.getTitle() : newObj.getTitle()); // Atualiza o título do projeto.
        newObj.setDescription(obj.getDescription() != null ? obj.getDescription() : newObj.getDescription()); // Atualiza a descrição projeto.
        newObj.setInitialDate(obj.getInitialDate() != null ? obj.getInitialDate() : newObj.getInitialDate()); // Atualiza a data inicial do projeto.
        newObj.setFinishDate(obj.getFinishDate() != null ? obj.getFinishDate() : newObj.getFinishDate()); //Atualiza a data final do projeto 
        newObj.setStatusActive(obj.isStatusActive()); // Atualiza se o projeto está concluido ou não.
        return this.taskRepository.save(newObj); // Salva as alterações no banco de dados.
    }

    /**
     * Exclui uma tarefa pelo ID.
     * @param id ID da tarefa a ser excluída.
     * @throws RuntimeException Caso a tarefa tenha entidades relacionadas que impeçam a exclusão.
     */
    public void deleteTask(Long id) {
        findById(id); // Verifica se a tarefa existe antes de tentar excluir.
        try {
            this.taskRepository.deleteById(id); // Tenta excluir a tarefa pelo ID.
        } catch (Exception e) {
            // Lança exceção caso existam relacionamentos que impeçam a exclusão.
            throw new RuntimeException("Não é possível excluir. Existem entidades relacionadas!");
        }
    }
}
