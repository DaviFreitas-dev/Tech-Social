// Abstração e herança
public abstract class Prestador extends Pessoa {
    // Constantes
    public static final String MANUTENCAO = "Manutenção de computadores";
    public static final String INCLUSAO_DIGITAL = "Inclusão digital";

    // Encapsulamento
    private String especialidade;
    private boolean disponivel;

    // Construtor
    public Prestador(String nome, String cpf, String telefone, String especialidade) {
        super(nome, cpf, telefone);
        setEspecialidade(especialidade);
        this.disponivel = true;
    }

    // Getters e setters
    public String getEspecialidade() { return especialidade; }

    public void setEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia.");
        }
        this.especialidade = especialidade.trim();
    }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    // Método abstrato (polimorfismo)
    public abstract void realizarAtendimento();

    // Sobrescrita de método
    @Override
    public String toString() {
        return super.toString() + " | " + especialidade
                + (disponivel ? " | Disponível" : " | Ocupado");
    }
}
