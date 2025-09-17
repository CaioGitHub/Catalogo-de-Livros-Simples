package application.usecases;

import adapters.out.LivroRepository;
import domain.Livro;

public class CadastrarLivroUseCase {
    private final LivroRepository repository;

    public CadastrarLivroUseCase(LivroRepository repository) {
        this.repository = repository;
    }

    public void execute(Livro livro) {
        repository.save(livro);
    }
}
