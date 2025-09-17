package adapters.out;



import domain.Autor;
import domain.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcLivroRepository implements LivroRepository {
    private final Connection connection;

    public JdbcLivroRepository(Connection connection) {
        this.connection = connection;
        criarTabelas();
    }

    private void criarTabelas() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS autor (id BIGINT AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(255))");
            stmt.execute("CREATE TABLE IF NOT EXISTS livro (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "titulo VARCHAR(255), " +
                    "anoPublicacao INT, " +
                    "autor_id BIGINT, " +
                    "FOREIGN KEY (autor_id) REFERENCES autor(id))");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Livro livro) {
        try {
            PreparedStatement stmtAutor = connection.prepareStatement(
                    "INSERT INTO autor (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmtAutor.setString(1, livro.getAutor().getNome());
            stmtAutor.executeUpdate();
            ResultSet autorKeys = stmtAutor.getGeneratedKeys();
            autorKeys.next();
            Long autorId = autorKeys.getLong(1);

            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO livro (titulo, anoPublicacao, autor_id) VALUES (?, ?, ?)");
            stmt.setString(1, livro.getTitulo());
            stmt.setInt(2, livro.getAnoPublicacao());
            stmt.setLong(3, autorId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Livro> findById(Long id) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT l.id, l.titulo, l.anoPublicacao, a.id as autorId, a.nome as autorNome " +
                            "FROM livro l JOIN autor a ON l.autor_id = a.id WHERE l.id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Autor autor = new Autor(rs.getLong("autorId"), rs.getString("autorNome"));
                Livro livro = new Livro(rs.getLong("id"), rs.getString("titulo"), rs.getInt("anoPublicacao"), autor);
                return Optional.of(livro);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Livro> findAll() {
        List<Livro> livros = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT l.id, l.titulo, l.anoPublicacao, a.id as autorId, a.nome as autorNome " +
                            "FROM livro l JOIN autor a ON l.autor_id = a.id");
            while (rs.next()) {
                Autor autor = new Autor(rs.getLong("autorId"), rs.getString("autorNome"));
                Livro livro = new Livro(rs.getLong("id"), rs.getString("titulo"), rs.getInt("anoPublicacao"), autor);
                livros.add(livro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }
}
