import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Classe Atendimento
public class Atendimento {
    // Encapsulamento e associação
    private final Solicitacao solicitacao;
    private final Prestador prestador;
    private final LocalDateTime dataHora;

    // Construtor
    public Atendimento(Solicitacao solicitacao, Prestador prestador) {
        if (solicitacao == null || prestador == null) {
            throw new IllegalArgumentException("Atendimento exige solicitação e prestador.");
        }
        this.solicitacao = solicitacao;
        this.prestador = prestador;
        this.dataHora = LocalDateTime.now();
    }

    // Getters
    public Solicitacao getSolicitacao() { return solicitacao; }
    public Prestador getPrestador() { return prestador; }
    public LocalDateTime getDataHora() { return dataHora; }

    // Sobrescrita de método
    @Override
    public String toString() {
        return "Atendimento em " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + " | Solicitação #" + solicitacao.getId()
                + " | Prestador: " + prestador.getNome()
                + " | Status: " + solicitacao.getStatus();
    }
}
