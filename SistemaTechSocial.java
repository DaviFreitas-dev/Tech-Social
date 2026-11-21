import java.util.List;
import java.util.Scanner;

// Classe principal (menu)
public class SistemaTechSocial {
    // Encapsulamento
    private final Scanner scanner = new Scanner(System.in);

    // Associação com o Repositorio
    private final Repositorio repositorio = new Repositorio();

    // Método main
    public static void main(String[] args) {
        SistemaTechSocial sistema = new SistemaTechSocial();
        try {
            sistema.executar();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("\nEntrada encerrada.");
        }
    }

    // Menu (do-while e switch)
    public void executar() {
        String opcao;

        do {
            exibirMenu();
            opcao = lerTexto("Escolha uma opção");

            // Tratamento de exceções
            try {
                switch (opcao) {
                    case "1": cadastrarSolicitante(); break;
                    case "2": cadastrarPrestador(); break;
                    case "3": solicitarAjuda(); break;
                    case "4": consultarSolicitacoes(); break;
                    case "5": realizarAtendimento(); break;
                    case "6": cancelarSolicitacao(); break;
                    case "7": alterarDados(); break;
                    case "8": consultarHistorico(); break;
                    case "9": System.out.println("\nEncerrando o sistema..."); break;
                    default: System.out.println("\nOpção inválida!"); break;
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("\nErro: " + e.getMessage());
            }

        } while (!opcao.equals("9"));
    }

    private void exibirMenu() {
        System.out.println("\n==================================");
        System.out.println("      OXENTE, DEU ERRO?");
        System.out.println("       SISTEMA TECH SOCIAL");
        System.out.println("==================================");
        System.out.println("1 - Cadastrar solicitante");
        System.out.println("2 - Cadastrar prestador");
        System.out.println("3 - Solicitar ajuda");
        System.out.println("4 - Consultar solicitações");
        System.out.println("5 - Realizar atendimento");
        System.out.println("6 - Cancelar solicitação");
        System.out.println("7 - Alterar dados");
        System.out.println("8 - Histórico de atendimentos");
        System.out.println("9 - Sair");
        System.out.println("==================================");
    }

    // Opção 1
    private void cadastrarSolicitante() {
        System.out.println("\n[Cadastro de solicitante]");
        String nome = lerTexto("Nome");
        String cpf = lerCpf();
        String telefone = lerTexto("Telefone");
        String endereco = lerTexto("Endereço");
        String bairro = lerTexto("Bairro");

        Solicitante s = new Solicitante(nome, cpf, telefone, endereco, bairro);
        repositorio.adicionarSolicitante(s);
        System.out.println("\nSolicitante cadastrado com sucesso!");
    }

    // Opção 2
    private void cadastrarPrestador() {
        System.out.println("\n[Cadastro de prestador]");
        System.out.println("1 - Técnico de manutenção");
        System.out.println("2 - Instrutor de inclusão digital");
        String tipo = lerTexto("Tipo");
        if (!tipo.equals("1") && !tipo.equals("2")) {
            throw new IllegalArgumentException("Tipo de prestador inválido.");
        }

        String nome = lerTexto("Nome");
        String cpf = lerCpf();
        String telefone = lerTexto("Telefone");

        // Polimorfismo
        Prestador p;
        if (tipo.equals("1")) {
            String equipamento = lerTexto("Tipo de equipamento (ex.: notebooks, desktops)");
            p = new TecnicoManutencao(nome, cpf, telefone, equipamento);
        } else {
            p = new InstrutorInclusaoDigital(nome, cpf, telefone, escolherNivel());
        }
        repositorio.adicionarPrestador(p);
        System.out.println("\nPrestador cadastrado com sucesso!");
    }

    // Opção 3
    private void solicitarAjuda() {
        System.out.println("\n[Solicitação de ajuda]");
        Solicitante solicitante = repositorio.buscarSolicitantePorCpf(lerCpf());
        if (solicitante == null) {
            throw new IllegalArgumentException("Solicitante não encontrado. Cadastre-o primeiro (opção 1).");
        }

        System.out.println("1 - " + Prestador.MANUTENCAO);
        System.out.println("2 - " + Prestador.INCLUSAO_DIGITAL);
        String escolha = lerTexto("Categoria");
        String categoria;
        if (escolha.equals("1")) {
            categoria = Prestador.MANUTENCAO;
        } else if (escolha.equals("2")) {
            categoria = Prestador.INCLUSAO_DIGITAL;
        } else {
            throw new IllegalArgumentException("Categoria inválida.");
        }

        String descricao = lerTexto("Descreva o que você precisa");
        Solicitacao s = solicitante.solicitarAjuda(descricao, categoria);
        repositorio.adicionarSolicitacao(s);
        System.out.println("\nSolicitação #" + s.getId() + " aberta com sucesso!");
    }

    // Opção 4
    private void consultarSolicitacoes() {
        System.out.println("\n[Solicitações]");
        List<Solicitacao> lista = repositorio.listarSolicitacoes();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma solicitação registrada.");
            return;
        }
        for (Solicitacao s : lista) {
            System.out.println(s);
        }
    }

    // Opção 5
    private void realizarAtendimento() {
        System.out.println("\n[Realização de atendimento]");
        Solicitacao solicitacao = buscarSolicitacaoDigitada();

        if (solicitacao.getStatus() == StatusSolicitacao.ABERTA) {
            aceitarSolicitacao(solicitacao);
        } else if (solicitacao.getStatus() == StatusSolicitacao.ACEITA) {
            concluirAtendimento(solicitacao);
        } else {
            throw new IllegalStateException("Esta solicitação não pode ser atendida: "
                    + solicitacao.getStatus() + " (" + solicitacao.getStatus().getDescricao() + ").");
        }
    }

    // Opção 5: aceitar
    private void aceitarSolicitacao(Solicitacao solicitacao) {
        List<Prestador> disponiveis = repositorio.listarPrestadoresDisponiveis(solicitacao.getCategoria());
        if (disponiveis.isEmpty()) {
            throw new IllegalStateException("Nenhum prestador disponível para " + solicitacao.getCategoria() + ".");
        }
        System.out.println("Prestadores disponíveis:");
        for (Prestador p : disponiveis) {
            System.out.println(" - " + p);
        }

        Prestador prestador = repositorio.buscarPrestadorPorCpf(lerCpf());
        solicitacao.aceitar(prestador);
        System.out.println("\nSolicitação #" + solicitacao.getId() + " aceita por " + prestador.getNome()
                + ". Use a opção 5 de novo para concluir o atendimento.");
    }

    // Opção 5: concluir
    private void concluirAtendimento(Solicitacao solicitacao) {
        Prestador prestador = solicitacao.getPrestador();
        System.out.println();
        // Polimorfismo
        prestador.realizarAtendimento();
        solicitacao.concluir();
        repositorio.adicionarAtendimento(new Atendimento(solicitacao, prestador));
        System.out.println("\nAtendimento concluído! Solicitação #" + solicitacao.getId() + " finalizada.");
    }

    // Opção 6
    private void cancelarSolicitacao() {
        System.out.println("\n[Cancelamento de solicitação]");
        Solicitacao solicitacao = buscarSolicitacaoDigitada();
        solicitacao.cancelar();
        System.out.println("\nSolicitação #" + solicitacao.getId() + " cancelada.");
        if (solicitacao.getPrestador() != null) {
            System.out.println(solicitacao.getPrestador().getNome() + " está disponível de novo.");
        }
    }

    // Opção 7
    private void alterarDados() {
        System.out.println("\n[Alteração de dados]");
        System.out.println("1 - Dados de uma pessoa (solicitante ou prestador)");
        System.out.println("2 - Descrição de uma solicitação aberta");
        String opcao = lerTexto("Opção");
        if (opcao.equals("1")) {
            alterarPessoa();
        } else if (opcao.equals("2")) {
            alterarDescricaoSolicitacao();
        } else {
            throw new IllegalArgumentException("Opção inválida.");
        }
    }

    private void alterarPessoa() {
        String cpf = lerCpf();
        // Polimorfismo
        Pessoa pessoa = repositorio.buscarSolicitantePorCpf(cpf);
        if (pessoa == null) {
            pessoa = repositorio.buscarPrestadorPorCpf(cpf);
        }
        if (pessoa == null) {
            throw new IllegalArgumentException("Pessoa não encontrada.");
        }

        System.out.println("\nDados atuais: " + pessoa);
        System.out.println("1 - Nome");
        System.out.println("2 - Telefone");
        // instanceof e cast
        boolean solicitante = pessoa instanceof Solicitante;
        if (solicitante) {
            System.out.println("3 - Endereço");
            System.out.println("4 - Bairro");
        }
        String campo = lerTexto("O que deseja alterar");
        if (campo.equals("1")) {
            pessoa.setNome(lerTexto("Novo nome"));
        } else if (campo.equals("2")) {
            pessoa.setTelefone(lerTexto("Novo telefone"));
        } else if (solicitante && campo.equals("3")) {
            ((Solicitante) pessoa).setEndereco(lerTexto("Novo endereço"));
        } else if (solicitante && campo.equals("4")) {
            ((Solicitante) pessoa).setBairro(lerTexto("Novo bairro"));
        } else {
            throw new IllegalArgumentException("Campo inválido.");
        }
        System.out.println("\nDados atualizados: " + pessoa);
    }

    private void alterarDescricaoSolicitacao() {
        Solicitacao solicitacao = buscarSolicitacaoDigitada();
        solicitacao.setDescricao(lerTexto("Nova descrição"));
        System.out.println("\nSolicitação atualizada: " + solicitacao);
    }

    // Opção 8
    private void consultarHistorico() {
        System.out.println("\n[Histórico de atendimentos]");
        List<Atendimento> lista = repositorio.listarAtendimentos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum atendimento registrado.");
            return;
        }
        for (Atendimento a : lista) {
            System.out.println(a);
        }
    }

    // Métodos auxiliares
    private Solicitacao buscarSolicitacaoDigitada() {
        int id;
        try {
            id = Integer.parseInt(lerTexto("ID da solicitação"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O ID deve ser um número.");
        }
        Solicitacao s = repositorio.buscarSolicitacaoPorId(id);
        if (s == null) {
            throw new IllegalArgumentException("Solicitação #" + id + " não encontrada.");
        }
        return s;
    }

    private String escolherNivel() {
        System.out.println("1 - Básico");
        System.out.println("2 - Intermediário");
        System.out.println("3 - Avançado");
        switch (lerTexto("Nível do curso")) {
            case "1": return "Básico";
            case "2": return "Intermediário";
            case "3": return "Avançado";
            default: throw new IllegalArgumentException("Nível inválido.");
        }
    }

    private String lerTexto(String rotulo) {
        System.out.print(rotulo + ": ");
        return scanner.nextLine().trim();
    }

    private String lerCpf() {
        return lerTexto("CPF").replaceAll("\\D", "");
    }
}
