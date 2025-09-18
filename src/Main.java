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
            String apiKey = config.ApplicationProperties.get("resend.apiKey");
            String fromEmail = config.ApplicationProperties.get("resend.fromEmail");
            String toEmail = config.ApplicationProperties.get("resend.toEmail");

            ResendEmailService emailService = new ResendEmailService(apiKey, fromEmail, toEmail);

            String adapter = config.ApplicationProperties.get("adapter.persistence");
            JdbcLivroRepository livroRepository;

            if ("jdbc".equalsIgnoreCase(adapter)) {
                Connection connection = DriverManager.getConnection(
                        "jdbc:h2:mem:livraria;DB_CLOSE_DELAY=-1", "sa", "");
                livroRepository = new JdbcLivroRepository(connection);
            } else {
                throw new RuntimeException("Adapter não suportado: " + adapter);
            }

            CadastrarLivroUseCase cadastrar = new CadastrarLivroUseCase(livroRepository, emailService);
            BuscarLivroPorIdUseCase buscar = new BuscarLivroPorIdUseCase(livroRepository);
            ListarLivrosUseCase listar = new ListarLivrosUseCase(livroRepository);

            LivroController controller = new LivroController();
            controller.start(cadastrar, buscar, listar);

            cadastrar.execute(new Livro(null, "Java Avançado", 2020, new Autor(null, "João Silva")));
            cadastrar.execute(new Livro(null, "Arquitetura Hexagonal", 2021, new Autor(null, "Maria Souza")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}