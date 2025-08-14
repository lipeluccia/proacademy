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
proacademy/
├── .github/
│   └── workflows/
│       └── maven.yml         # (CI/CD) - Automação para build e testes do projeto com GitHub Actions.
│
├── .mvn/                     # Contém o Maven Wrapper, que permite executar o projeto sem instalar o Maven.
│
├── src/                      # Pasta principal que contém todo o código-fonte da aplicação.
│   ├── main/                 # Código da aplicação principal.
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── proacademy/
│   │   │           └── proacademy/
│   │   │               ├── ProacademyApplication.java  # Ponto de entrada da aplicação Spring Boot.
│   │   │               │
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java     # Configurações de segurança (quais rotas são públicas/privadas, etc).
│   │   │               │
│   │   │               ├── controller/                 # (Camada de Apresentação) - Define os endpoints da API (/auth, /user, etc).
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── ProjectController.java
│   │   │               │   ├── TaskController.java
│   │   │               │   └── UserController.java
│   │   │               │
│   │   │               ├── dto/                        # (Data Transfer Objects) - Objetos que definem o formato dos dados enviados e recebidos pela API.
│   │   │               │   ├── AuthDTO.java
│   │   │               │   ├── TaskDTO.java
│   │   │               │   └── UserDTO.java
│   │   │               │
│   │   │               ├── entities/                   # (Camada de Domínio/Modelo) - Classes que representam as tabelas do banco de dados.
│   │   │               │   ├── ProjectEntity.java
│   │   │               │   ├── TaskEntity.java
│   │   │               │   └── UserEntity.java
│   │   │               │
│   │   │               ├── repository/                 # (Camada de Acesso a Dados) - Interfaces que gerenciam as operações com o banco de dados (salvar, buscar, deletar).
│   │   │               │   ├── ProjectRepository.java
│   │   │               │   ├── TaskRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               │
│   │   │               ├── security/                   # Classes relacionadas à segurança com JWT (JSON Web Tokens).
│   │   │               │   ├── JwtAuthFilter.java      # Filtro que intercepta cada requisição para validar o token JWT.
│   │   │               │   ├── JwtTokenProvider.java   # Classe responsável por gerar e validar os tokens.
│   │   │               │   └── UserDetailsServiceImpl.java # Serviço que busca os detalhes do usuário para autenticação.
│   │   │               │
│   │   │               └── services/                   # (Camada de Serviço/Negócio) - Onde fica a lógica principal da aplicação.
│   │   │                   ├── ProjectService.java
│   │   │                   ├── TaskService.java
│   │   │                   └── UserService.java
│   │   │
│   │   └── resources/
│   │       ├── static/       # (Vazio) - Para arquivos estáticos como CSS, JS, imagens (mais usado em apps web).
│   │       ├── templates/    # (Vazio) - Para templates de páginas web (mais usado em apps web).
│   │       └── application.properties  # Arquivo principal de configuração (porta do servidor, conexão com BD, etc).
│   │
│   └── test/                 # Código-fonte para os testes da aplicação.
│       └── java/
│           └── ...
│
├── .ignore                # Lista de arquivos e pastas que o Git deve ignorar.
├── mvnw                      # Script do Maven Wrapper para Linux/Mac.
├── mvnw.cmd                  # Script do Maven Wrapper para Windows.
└── pom.xml                   # Arquivo de configuração do Maven (dependências, plugins, etc).
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

