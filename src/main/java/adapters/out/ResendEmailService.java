package adapters.out;

import application.ports.EmailSenderPort;
import domain.Livro;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResendEmailService implements EmailSenderPort {
    private final String apiKey;
    private final String from;
    private final String to;

    public ResendEmailService(String apiKey, String from, String to) {
        this.apiKey = apiKey;
        this.from = from;
        this.to = to;
    }

    @Override
    public void enviarEmailNovoLivro(Livro livro) {
        try {
            URL url = new URL("https://api.resend.com/emails");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String body = String.format(
                    "{ \"from\": \"%s\", \"to\": [\"%s\"], \"subject\": \"Novo livro cadastrado\", \"html\": \"<strong>%s</strong> (%d) foi adicionado ao catálogo.\" }",
                    from,
                    to,
                    livro.getTitulo(),
                    livro.getAnoPublicacao()
            );

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = body.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int status = conn.getResponseCode();
            if (status != 200 && status != 202) {
                throw new RuntimeException("Erro ao enviar email: HTTP " + status);
            }

            conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
