import adapters.in.LivroController;
import adapters.out.JdbcLivroRepository;
import adapters.out.ResendEmailService;
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
            ResendEmailService emailService = new ResendEmailService(
                    "apiKey",
                    "onboarding@resend.dev",
                    "seuEmailAqui@example.com"
            );

            Connection connection = DriverManager.getConnection("jdbc:h2:mem:livraria;DB_CLOSE_DELAY=-1", "sa", "");
            JdbcLivroRepository repository = new JdbcLivroRepository(connection);

            CadastrarLivroUseCase cadastrar = new CadastrarLivroUseCase(repository, emailService);
            BuscarLivroPorIdUseCase buscar = new BuscarLivroPorIdUseCase(repository);
            ListarLivrosUseCase listar = new ListarLivrosUseCase(repository);

            LivroController controller = new LivroController();
            controller.start(cadastrar, buscar, listar);

            cadastrar.execute(new Livro(null, "Java Avançado", 2020, new Autor(null, "João Silva")));
            cadastrar.execute(new Livro(null, "Arquitetura Hexagonal", 2021, new Autor(null, "Maria Souza")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}