package com.proacademy.proacademy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.proacademy.proacademy.models.Project;
import com.proacademy.proacademy.models.Task;
import com.proacademy.proacademy.models.Task.CreateTask;
import com.proacademy.proacademy.repositories.TaskRepository;
import com.proacademy.proacademy.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class TaskService {

    @Autowired // Injeta automaticamente a dependência do repositório TaskRepository.
    private TaskRepository taskRepository;

    @Autowired // Injeta automaticamente a dependência do serviço ProjectService para operações relacionadas a projetos.
    private ProjectService projectService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id); // Busca a tarefa no repositório.
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

     public List<Task> findByAllByProjectId(Long projectId) {
        List<Task> tasks = this.taskRepository.findByProject_Id(projectId);
        return tasks;
    }

    @Transactional
    public Task createTask(@Valid @Validated(CreateTask.class) Task obj) {
        Project project = this.projectService.findById(obj.getProject().getId()); // Busca o projeto associado à tarefa.
        obj.setId(null); // Garante que o ID será gerado automaticamente ao salvar.
        obj.setProject(project); // Associa o projeto à tarefa.
        obj = this.taskRepository.save(obj); // Salva a tarefa no banco de dados.
        return obj; // Retorna a tarefa persistida.
    }

    @Transactional
    public Task updateTask(Task obj) {
        Task newObj = findById(obj.getId()); // Busca a task ID.
        if (obj.getTitle() != null) {
            newObj.setTitle(obj.getTitle()); // Atualiza o titulo da task.
        }  if (obj.getDescription() != null) {
            newObj.setDescription(obj.getDescription()); // Atualiza a descrição da task.
        } if (obj.getInitialDate()!= null) {
            newObj.setInitialDate(obj.getInitialDate());  // Atualiza a data inicial da task.
        } if (obj.getFinishDate() != null) {
            newObj.setFinishDate(obj.getFinishDate());  //Atualiza a data final da task 
        } newObj.setStatusActive(obj.isStatusActive()); // Atualiza se a task está concluido ou não.
        return this.taskRepository.save(newObj); // Salva as alterações no banco de dados.
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada"));  taskRepository.delete(task);
    }
}
