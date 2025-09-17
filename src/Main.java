import adapters.in.LivroController;
import adapters.out.JdbcLivroRepository;
import application.usecases.BuscarLivroPorIdUseCase;
import application.usecases.CadastrarLivroUseCase;
import application.usecases.ListarLivrosUseCase;
import domain.Autor;
import domain.Livro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:livraria;DB_CLOSE_DELAY=-1", "sa", "");
            JdbcLivroRepository livroRepository = new JdbcLivroRepository(connection);

            livroRepository.save(new Livro(null, "Java Avançado", 2020, new Autor(null, "João Silva")));
            livroRepository.save(new Livro(null, "Arquitetura Hexagonal", 2021, new Autor(null, "Maria Souza")));
            livroRepository.save(new Livro(null, "Spring Boot na Prática", 2022, new Autor(null, "Carlos Lima")));
            livroRepository.save(new Livro(null, "Microservices com Java", 2020, new Autor(null, "Ana Pereira")));
            livroRepository.save(new Livro(null, "Design Patterns", 1994, new Autor(null, "Robert Martin")));
            livroRepository.save(new Livro(null, "Clean Code", 2008, new Autor(null, "Robert Martin")));
            livroRepository.save(new Livro(null, "Java Concurrency", 2006, new Autor(null, "Brian Goetz")));
            livroRepository.save(new Livro(null, "Hibernate Essencial", 2015, new Autor(null, "Paulo Oliveira")));
            livroRepository.save(new Livro(null, "Testes Unitários com JUnit", 2018, new Autor(null, "Fernanda Costa")));
            livroRepository.save(new Livro(null, "Kotlin para Java Developers", 2021, new Autor(null, "Rafael Almeida")));

            JdbcLivroRepository repository = new JdbcLivroRepository(connection);

            CadastrarLivroUseCase cadastrar = new CadastrarLivroUseCase(repository);
            BuscarLivroPorIdUseCase buscar = new BuscarLivroPorIdUseCase(repository);
            ListarLivrosUseCase listar = new ListarLivrosUseCase(repository);

            LivroController controller = new LivroController();
            controller.start(cadastrar, buscar, listar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}