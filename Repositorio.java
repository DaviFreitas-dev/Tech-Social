import java.util.ArrayList;
import java.util.List;

// Classe Repositorio
public class Repositorio {
    // Agregação (listas) e encapsulamento
    private final List<Solicitante> solicitantes = new ArrayList<>();
    private final List<Prestador> prestadores = new ArrayList<>();
    private final List<Solicitacao> solicitacoes = new ArrayList<>();
    private final List<Atendimento> atendimentos = new ArrayList<>();
    private int proximoId = 1;

    // Cadastros
    public void adicionarSolicitante(Solicitante s) {
        verificarCpfLivre(s.getCpf());
        solicitantes.add(s);
    }

    public void adicionarPrestador(Prestador p) {
        verificarCpfLivre(p.getCpf());
        prestadores.add(p);
    }

    public void adicionarSolicitacao(Solicitacao s) {
        s.setId(proximoId++);
        solicitacoes.add(s);
    }

    public void adicionarAtendimento(Atendimento a) {
        atendimentos.add(a);
    }

    // Listagens
    public List<Solicitacao> listarSolicitacoes() {
        return new ArrayList<>(solicitacoes);
    }

    public List<Atendimento> listarAtendimentos() {
        return new ArrayList<>(atendimentos);
    }

    // Buscas
    public Solicitacao buscarSolicitacaoPorId(int id) {
        for (Solicitacao s : solicitacoes) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public Solicitante buscarSolicitantePorCpf(String cpf) {
        for (Solicitante s : solicitantes) {
            if (s.getCpf().equals(cpf)) return s;
        }
        return null;
    }

    public Prestador buscarPrestadorPorCpf(String cpf) {
        for (Prestador p : prestadores) {
            if (p.getCpf().equals(cpf)) return p;
        }
        return null;
    }

    public List<Prestador> listarPrestadoresDisponiveis(String categoria) {
        List<Prestador> resultado = new ArrayList<>();
        for (Prestador p : prestadores) {
            if (p.isDisponivel() && p.getEspecialidade().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // Validação de CPF único
    private void verificarCpfLivre(String cpf) {
        if (buscarSolicitantePorCpf(cpf) != null || buscarPrestadorPorCpf(cpf) != null) {
            throw new IllegalArgumentException("Já existe uma pessoa cadastrada com esse CPF.");
        }
    }
}
