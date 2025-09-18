# Livraria Digital - Catálogo de Livros

## Descrição do Projeto

Este projeto é um microserviço simples de **catálogo de livros** para uma livraria digital.
O sistema foi desenvolvido seguindo a **Arquitetura Hexagonal (Ports & Adapters)**, com foco em separação de responsabilidades e facilidade de manutenção.

O projeto permite:

* Cadastrar livros com **entidade Autor agregada**;
* Listar livros cadastrados;
* Buscar livro por ID;
* Enviar notificação por e-mail sempre que um novo livro é cadastrado (usando a ferramenta **Resend**);
* Configurar qual adapter estará ativo via `application.properties`.

---

## Tecnologias Utilizadas

* **Java 17**
* **Javalin 6.1.3** (framework web leve para REST API)
* **H2 Database** (banco de dados em memória para testes)
* **Resend** (serviço de envio de e-mails)
* **Maven** (ou Gradle, caso deseje adaptar)
* Estrutura baseada em **Arquitetura Hexagonal**

---

## Estrutura de Pastas

```
src
└── main
    └── java
        ├── adapters
        │   ├── in
        │   │    └── LivroController.java
        │   └── out
        │        ├── JdbcLivroRepository.java
        │        ├── LivroRepository.java
        │        └── ResendEmailService.java
        ├── application
        │   ├── ports
        │   │    └── EmailSenderPort.java
        │   └── usecases
        │        ├── BuscarLivroPorIdUseCase.java
        │        ├── CadastrarLivroUseCase.java
        │        └── ListarLivrosUseCase.java
        ├── config
        │    └── ApplicationProperties.java
        └── domain
             ├── Autor.java
             └── Livro.java
    └── resources
         └── application.properties
    └── Main.java
```

---

## Configuração do Projeto

1. **Configurar o `application.properties`**

```properties
# Adapter de persistência
adapter.persistence=jdbc

# Configuração do Resend
resend.apiKey=SUA_API_KEY
resend.fromEmail=onboarding@resend.dev
resend.toEmail=seuemail@gmail.com
```

> Observação: o e-mail remetente precisa estar **verificado no Resend**.
> Para testes, você pode usar `onboarding@resend.dev`.

2. **Build e execução**

Se estiver usando Maven:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="Main"
```

Se estiver usando Gradle:

```bash
./gradlew build
./gradlew run
```

3. **Acessar o serviço**

* O projeto está configurado para rodar em: http://localhost:8080
* Endpoints disponíveis:

  * `GET /livros` → lista todos os livros
  * `GET /livros/:id` → busca livro por ID
  * `POST /livros` → cadastra um novo livro e envia notificação por e-mail

---

## Funcionalidades

* **Cadastro de livros** com título, ano de publicação e autor.
* **Envio de e-mail automático** ao cadastrar um novo livro (via Resend).
* **Consulta de livros**: listar todos ou buscar por ID.
* **Configuração externa** de adapter ativo e parâmetros do e-mail (`application.properties`).
* **População inicial** de livros para testes.

---

## Observações

* O projeto é modular e segue a **Arquitetura Hexagonal**, separando **domínio**, **casos de uso**, **adapters de entrada** e **adapters de saída**.
* Evita hardcodes de configuração, usando **properties externas**.
* Pode ser facilmente estendido para novos adapters (ex: banco diferente, outro serviço de e-mail).

