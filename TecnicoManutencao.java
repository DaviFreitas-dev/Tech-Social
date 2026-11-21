// Herança
public class TecnicoManutencao extends Prestador {
    // Encapsulamento
    private String tipoEquipamento;

    // Construtor
    public TecnicoManutencao(String nome, String cpf, String telefone, String tipoEquipamento) {
        super(nome, cpf, telefone, MANUTENCAO);
        setTipoEquipamento(tipoEquipamento);
    }

    // Polimorfismo
    @Override
    public String getTipo() {
        return "Técnico de Manutenção";
    }

    // Getters e setters
    public String getTipoEquipamento() { return tipoEquipamento; }

    public void setTipoEquipamento(String tipoEquipamento) {
        if (tipoEquipamento == null || tipoEquipamento.isBlank()) {
            throw new IllegalArgumentException("Tipo de equipamento não pode ser vazio.");
        }
        this.tipoEquipamento = tipoEquipamento.trim();
    }

    // Polimorfismo (versão do técnico)
    @Override
    public void realizarAtendimento() {
        System.out.println("Técnico " + getNome() + " iniciou a manutenção de " + tipoEquipamento + ":");
        System.out.println(" - Diagnóstico do defeito");
        System.out.println(" - Limpeza e reparo ou troca de peças");
        System.out.println(" - Teste final do equipamento");
    }
}
