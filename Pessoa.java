// Abstração (classe abstrata)
public abstract class Pessoa {
    // Encapsulamento (atributos privados)
    private String nome;
    private String cpf;
    private String telefone;

    // Construtor
    public Pessoa(String nome, String cpf, String telefone) {
        setNome(nome);
        setCpf(cpf);
        setTelefone(telefone);
    }

    // Método abstrato (polimorfismo)
    public abstract String getTipo();

    // Getters
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }

    // Setters com validação (encapsulamento)
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    // Sobrescrita de método
    @Override
    public String toString() {
        return getTipo() + ": " + nome + " | CPF: " + cpf + " | Tel: " + telefone;
    }
}
