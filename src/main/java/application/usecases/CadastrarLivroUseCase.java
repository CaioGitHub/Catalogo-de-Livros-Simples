package application.usecases;

import adapters.out.LivroRepository;
import application.ports.EmailSenderPort;
import domain.Livro;

public class CadastrarLivroUseCase {
    private final LivroRepository repository;
    private final EmailSenderPort emailSender;

    public CadastrarLivroUseCase(LivroRepository repository, EmailSenderPort emailSender) {
        this.repository = repository;
        this.emailSender = emailSender;
    }

    public void execute(Livro livro) {
        repository.save(livro);
        emailSender.enviarEmailNovoLivro(livro);
    }
}
