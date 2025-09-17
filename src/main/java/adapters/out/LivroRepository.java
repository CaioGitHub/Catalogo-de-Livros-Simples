package adapters.out;

import domain.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository {
    void save(Livro livro);
    Optional<Livro> findById(Long id);
    List<Livro> findAll();
}
