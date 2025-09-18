package application.ports;

import domain.Livro;

public interface EmailSenderPort {
    void enviarEmailNovoLivro(Livro livro);
}
