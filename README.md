# ProAcademy

**ProAcademy** é um aplicativo destinado ao gerenciamento de projetos acadêmicos, projetado para facilitar a organização e o acompanhamento de tarefas e cronogramas. O foco principal são estudantes de nível médio e superior, bem como docentes que necessitam de uma ferramenta eficiente para gerenciar suas atividades acadêmicas.

## Funcionalidades Planejadas

- **Gestão de Usuários**: Cadastro, atualização e exclusão de usuários.
- **Gestão de Projetos**: Criação, edição e exclusão de projetos associados aos usuários.
- **Gestão de Tarefas**: Gerenciamento de tarefas vinculadas aos projetos, com opções de atualização e remoção.
- **Banco de Dados**: Armazenamento seguro de dados de usuários, projetos e tarefas.

## Tecnologias Utilizadas

### Back-end
- **Linguagem**: Java
- **Framework**: Spring Boot
- **Banco de Dados**: MySQL
- **Gerenciador de Dependências**: Maven

### Futuro Front-end
- **Plataforma Planejada**: Android Studio (para criação de aplicativo mobile).

## Como Executar o Projeto

### Requisitos

Certifique-se de que os seguintes softwares estejam instalados:
- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/installer/)

### Configuração do Ambiente

1. Clone este repositório:
   ```bash
   git clone https://github.com/lipeluccia/proacademy.git
   cd proacademy
   ```

2. Configure o banco de dados:
   - Crie um banco de dados no MySQL.
   - Atualize o arquivo `src/main/resources/application.properties` com as credenciais do banco.

3. Execute o projeto:
   ```bash
   mvn spring-boot:run
   ```

4. A API estará disponível em `http://localhost:8080`.

## Estrutura do Projeto

```plaintext
src
├── main
│   ├── java
│   │   └── com.proacademy.proacademy
│   │       ├── controllers      # Controladores REST
│   │       ├── models           # Modelos de dados
│   │       ├── repositories     # Interfaces para acesso ao banco de dados
│   │       └── services         # Regras de negócio
│   └── resources
│       ├── application.properties # Configurações da aplicação
```

## Contribuições

Contribuições são bem-vindas! Caso tenha sugestões, melhorias ou correções, fique à vontade para abrir uma *issue* ou enviar um *pull request*.

### Como Contribuir

1. Faça um fork do repositório.
2. Crie um branch para a sua funcionalidade: `git checkout -b minha-funcionalidade`.
3. Commit suas alterações: `git commit -m "Adicionei minha funcionalidade"`.
4. Envie o branch: `git push origin minha-funcionalidade`.
5. Abra um *pull request*.

## Licença

Este projeto está licenciado sob a licença **MIT**. Consulte o arquivo `LICENSE` para mais informações.

---
*ProAcademy: Simplificando o gerenciamento de projetos acadêmicos!*
```

