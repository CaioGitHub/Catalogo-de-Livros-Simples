package application.usecases;

import adapters.out.LivroRepository;
import domain.Livro;

import java.util.List;

public class ListarLivrosUseCase {
    private final LivroRepository repository;

    public ListarLivrosUseCase(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> execute() {
        return repository.findAll();
    }
}
