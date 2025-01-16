package com.proacademy.proacademy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proacademy.proacademy.models.User;
import com.proacademy.proacademy.repositories.UserRepository;

@Service    // Declara que esta classe é um serviço do Spring, permitindo sua injeção e gerenciamento pelo framework.
public class UserService {

    @Autowired  // Injeta automaticamente a dependência do repositório UserRepository.
    private UserRepository userRepository;

    /**
     * Busca um usuário pelo ID.
     * @param id ID do usuário a ser buscado.
     * @return Retorna o objeto User encontrado.
     * @throws RuntimeException Caso o usuário não seja encontrado.
     */
    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id); // Busca o usuário no repositório.
        return user.orElseThrow(() -> new RuntimeException(
        "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));  
    }

    /**
     * Cria um novo usuário e associa seus projetos.
     * @param obj Objeto User contendo as informações do novo usuário.
     * @return Retorna o usuário salvo no banco de dados.
     * A anotação @Transactional garante que a operação será atômica.
     */
    @Transactional
    public User create(User obj) {
        obj.setId(null);    // Garante que o ID será gerado automaticamente ao salvar.
        obj = this.userRepository.save(obj);    // Salva o usuário no banco.
        return obj;
    }

    /**
     * Atualiza a senha de um usuário existente.
     * @param obj Objeto User contendo o ID e a nova senha.
     * @return Retorna o usuário atualizado.
     * A anotação @Transactional garante que a operação será realizada como uma transação.
     */
    @Transactional
    public User updatePassword(User obj){
        User newObj = findById(obj.getId());    // Busca o usuário pelo ID
        newObj.setPassword(obj.getPassword());  // Atualiza a senha.
        return this.userRepository.save(newObj);    // Salva as alterações no banco.
    }

     /**
     * Exclui um usuário pelo ID.
     * @param id ID do usuário a ser excluído.
     * @throws RuntimeException Caso o usuário tenha entidades relacionadas que impedem a exclusão.
     */
    public void delete(Long id){
        findById(id);   // Verifica se o usuário existe antes de tentar excluir.
        try {
            this.userRepository.deleteById(id);     // Tenta excluir o usuário pelo ID.
        } catch (Exception e) {
             // Lança exceção caso existam relacionamentos que impeçam a exclusão.
            throw new RuntimeException("Não é possivel excluir. Existem entidades relacionadas!");
        }
    }
}
