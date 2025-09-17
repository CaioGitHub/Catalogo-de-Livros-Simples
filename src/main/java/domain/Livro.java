package domain;

public class Livro {
    private Long id;
    private String titulo;
    private int anoPublicacao;
    private Autor autor;

    public Livro(Long id, String titulo, int anoPublicacao, Autor autor) {
        this.id = id;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public Autor getAutor() {
        return autor;
    }
}
