package adapters.in;

import application.usecases.BuscarLivroPorIdUseCase;
import application.usecases.CadastrarLivroUseCase;
import application.usecases.ListarLivrosUseCase;
import com.google.gson.Gson;
import domain.Autor;
import domain.Livro;
import io.javalin.Javalin;



public class LivroController {
    private final Gson gson = new Gson();

    public void start(CadastrarLivroUseCase cadastrar, BuscarLivroPorIdUseCase buscar, ListarLivrosUseCase listar) {
        Javalin app = Javalin.create().start(8080);

        app.post("/livros", ctx -> {
            LivroDTO dto = gson.fromJson(ctx.body(), LivroDTO.class);
            Livro livro = new Livro(null, dto.titulo, dto.anoPublicacao, new Autor(null, dto.autor));
            cadastrar.execute(livro);
            ctx.status(201).result("Livro cadastrado!");
        });

        app.get("/livros/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            var livro = buscar.execute(id);
            if (livro.isPresent()) {
                ctx.json(livro.get());
            } else {
                ctx.status(404).result("Livro não encontrado");
            }
        });

        app.get("/livros", ctx -> ctx.json(listar.execute()));
    }

    private static class LivroDTO {
        String titulo;
        String autor;
        int anoPublicacao;
    }
}
