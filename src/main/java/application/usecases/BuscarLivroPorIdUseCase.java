package application.usecases;

import adapters.out.LivroRepository;
import domain.Livro;

import java.util.Optional;

public class BuscarLivroPorIdUseCase {
    private final LivroRepository repository;

    public BuscarLivroPorIdUseCase(LivroRepository repository) {
        this.repository = repository;
    }

    public Optional<Livro> execute(Long id) {
        return repository.findById(id);
    }
}
