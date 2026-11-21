// Herança
public class Solicitante extends Pessoa {
    // Encapsulamento
    private String endereco;
    private String bairro;

    // Construtor (super chama o construtor de Pessoa)
    public Solicitante(String nome, String cpf, String telefone,
                       String endereco, String bairro) {
        super(nome, cpf, telefone);
        setEndereco(endereco);
        setBairro(bairro);
    }

    // Polimorfismo (implementa o método abstrato)
    @Override
    public String getTipo() {
        return "Solicitante";
    }

    // Getters
    public String getEndereco() { return endereco; }
    public String getBairro() { return bairro; }

    // Setters com validação
    public void setEndereco(String endereco) {
        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("Endereço não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    public void setBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new IllegalArgumentException("Bairro não pode ser vazio.");
        }
        this.bairro = bairro.trim();
    }

    // Associação (cria uma Solicitacao ligada a este solicitante)
    public Solicitacao solicitarAjuda(String descricao, String categoria) {
        return new Solicitacao(descricao, categoria, this);
    }

    // Sobrescrita de método
    @Override
    public String toString() {
        return super.toString() + " | " + endereco + ", " + bairro;
    }
}
