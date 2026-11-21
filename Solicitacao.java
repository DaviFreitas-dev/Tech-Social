import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Classe Solicitacao
public class Solicitacao {
    // Encapsulamento
    private int id;
    private String descricao;
    private String categoria;
    private LocalDate data;
    private StatusSolicitacao status;
    // Associação
    private Solicitante solicitante;
    // Associação com o prestador que aceitou
    private Prestador prestador;

    // Construtor
    public Solicitacao(String descricao, String categoria, Solicitante solicitante) {
        validarDescricao(descricao);
        validarCategoria(categoria);
        if (solicitante == null) {
            throw new IllegalArgumentException("A solicitação precisa de um solicitante.");
        }
        this.descricao = descricao.trim();
        this.categoria = categoria.trim();
        this.solicitante = solicitante;
        this.data = LocalDate.now();
        this.status = null;
        abrir();
    }

    // Ciclo de vida do status
    public void abrir() {
        if (status != null) {
            throw new IllegalStateException("A solicitação já foi aberta.");
        }
        status = StatusSolicitacao.ABERTA;
    }

    public void aceitar(Prestador prestador) {
        if (prestador == null || !prestador.isDisponivel()
                || !prestador.getEspecialidade().equalsIgnoreCase(categoria)) {
            throw new IllegalArgumentException("Prestador inválido ou indisponível para essa solicitação.");
        }
        mudarStatus(StatusSolicitacao.ACEITA);
        this.prestador = prestador;
        prestador.setDisponivel(false);
    }

    public void concluir() {
        mudarStatus(StatusSolicitacao.CONCLUIDA);
        prestador.setDisponivel(true);
    }

    public void cancelar() {
        mudarStatus(StatusSolicitacao.CANCELADA);
        if (prestador != null) {
            prestador.setDisponivel(true);
        }
    }

    // Encapsulamento (único lugar que muda o status)
    private void mudarStatus(StatusSolicitacao novo) {
        if (status == null || !status.podeIrPara(novo)) {
            throw new IllegalStateException(
                    "Transição inválida: " + status + " -> " + novo);
        }
        status = novo;
    }

    // Validações
    private static void validarDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }
    }

    private static void validarCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia.");
        }
    }

    private void exigirAberta() {
        if (status != StatusSolicitacao.ABERTA) {
            throw new IllegalStateException(
                    "Só é possível editar uma solicitação ABERTA (atual: " + status + ").");
        }
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        exigirAberta();
        validarDescricao(descricao);
        this.descricao = descricao.trim();
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        exigirAberta();
        validarCategoria(categoria);
        this.categoria = categoria.trim();
    }

    public LocalDate getData() {
        return data;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    // Sobrescrita de método
    @Override
    public String toString() {
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return "Solicitação #" + id
                + " | Categoria: " + categoria
                + " | Descrição: " + descricao
                + " | Data: " + dataFormatada
                + " | Status: " + status + " (" + status.getDescricao() + ")"
                + " | Solicitante: " + solicitante.getNome()
                + (prestador != null ? " | Prestador: " + prestador.getNome() : "");
    }
}
