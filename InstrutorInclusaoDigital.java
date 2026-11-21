// Herança
public class InstrutorInclusaoDigital extends Prestador {
    // Encapsulamento
    private String nivelCurso;

    // Construtor
    public InstrutorInclusaoDigital(String nome, String cpf, String telefone, String nivelCurso) {
        super(nome, cpf, telefone, INCLUSAO_DIGITAL);
        setNivelCurso(nivelCurso);
    }

    // Polimorfismo
    @Override
    public String getTipo() {
        return "Instrutor de Inclusão Digital";
    }

    // Getters e setters
    public String getNivelCurso() { return nivelCurso; }

    public void setNivelCurso(String nivelCurso) {
        if (nivelCurso == null || nivelCurso.isBlank()) {
            throw new IllegalArgumentException("Nível do curso não pode ser vazio.");
        }
        this.nivelCurso = nivelCurso.trim();
    }

    // Polimorfismo (versão do instrutor)
    @Override
    public void realizarAtendimento() {
        System.out.println("Instrutor " + getNome() + " iniciou uma aula de inclusão digital (nível " + nivelCurso + "):");
        switch (nivelCurso.toLowerCase()) {
            case "básico":
            case "basico":
                System.out.println(" - Ligar o computador e usar mouse e teclado");
                System.out.println(" - Apresentação do celular e da internet");
                break;
            case "intermediário":
            case "intermediario":
                System.out.println(" - Criar e usar e-mail");
                System.out.println(" - Pesquisar na internet com segurança");
                break;
            default:
                System.out.println(" - Serviços online e documentos digitais");
                System.out.println(" - Segurança, senhas e golpes na internet");
                break;
        }
        System.out.println(" - Atividade prática e tira-dúvidas finais");
    }
}
