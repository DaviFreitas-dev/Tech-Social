// Enum
public enum StatusSolicitacao {
    ABERTA("Aguardando um prestador"),
    ACEITA("Em atendimento"),
    CONCLUIDA("Atendimento finalizado"),
    CANCELADA("Solicitação cancelada");

    // Encapsulamento
    private final String descricao;

    // Construtor do enum
    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    // Getter
    public String getDescricao() {
        return descricao;
    }

    // Regras de mudança de status
    public boolean podeIrPara(StatusSolicitacao novo) {
        switch (this) {
            case ABERTA:
                return novo == ACEITA || novo == CANCELADA;
            case ACEITA:
                return novo == CONCLUIDA || novo == CANCELADA;
            default:
                return false;
        }
    }

    public boolean isFinal() {
        return this == CONCLUIDA || this == CANCELADA;
    }
}
