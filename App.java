import controller.*;
import repository.*;
import service.*;
import model.*;
import view.*;

import java.util.Scanner;
import java.util.List;

public class App {

    // Instâncias Globais para Uso nos Menus
    private static ClienteController clienteController;
    private static ProdutoController produtoController;
    private static VendaController vendaController;
    
    private static Scanner scanner = new Scanner(System.in);

    // Controle de Estado da Sessão Atual
    private static ClienteModels clienteLogado = null;
    private static boolean adminLogado = false;
    private static VendaModels carrinhoAtual = null; // Guarda a venda em andamento do cliente

    public static void main(String[] args) {
        // 1. Inicialização do Sistema (Padrão do seu projeto)
        ClienteRepository clienteRepository = new ClienteRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaRepository vendaRepository = new VendaRepository();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        VendaService vendaService = new VendaService(vendaRepository);

        clienteController = new ClienteController(clienteService);
        produtoController = new ProdutoController(produtoService);
        vendaController = new VendaController(vendaService);

        // Massa de dados inicial para testes
        inicializarDadosFicticios();

        // 2. Loop Principal da Máquina de Estados
        while (true) {
            if (!adminLogado && clienteLogado == null) {
                menuPrincipal();
            } else if (adminLogado) {
                menuAdmin();
            } else {
                menuCliente();
            }
        }
    }

    // ==========================================
    // MENU PRINCIPAL (DESLOGADO)
    // ==========================================
    private static void menuPrincipal() {
        System.out.println("\n===== LOJA DE DISCOS =====");
        System.out.println("1. Login Cliente");
        System.out.println("2. Login Admin");
        System.out.println("3. Cadastrar-se (Criar Conta)");
        System.out.println("0. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1:
                realizarLoginCliente();
                break;
            case 2:
                realizarLoginAdmin();
                break;
            case 3:
                crudCriarCliente();
                break;
            case 0:
                System.out.println("Encerrando aplicação... Até logo!");
                System.exit(0);
            default:
                System.out.println("Opção inválida!");
        }
    }

    // ==========================================
    // MENU DO ADMINISTRADOR
    // ==========================================
    private static void menuAdmin() {
        System.out.println("\n===== PAINEL ADMINISTRATIVO =====");
        System.out.println("1. Cadastrar Produto (C)");
        System.out.println("2. Listar Todos os Produtos (R)");
        System.out.println("3. Excluir Produto (D)");
        System.out.println("4. Listar Todos os Clientes");
        System.out.println("5. Remover Conta de Cliente");
        System.out.println("6. Ver Relatório Geral de Vendas");
        System.out.println("0. Logoff (Sair da Conta)");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1:
                crudCadastrarProduto();
                break;
            case 2:
                System.out.println("\n--- PRODUTOS EM ESTOQUE ---");
                produtoController.listarProdutos().forEach(p -> 
                    System.out.println("ID: " + p.getId() + " | " + p.getNome() + " - " + p.getArtista() + " | R$ " + p.getPreco() + " | Qtd: " + p.getQtdEstoque())
                );
                break;
            case 3:
                crudExcluirProduto();
                break;
            case 4:
                System.out.println("\n--- CLIENTES CADASTRADOS ---");
                clienteController.listarClientes().forEach(c -> 
                    System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | CPF: " + c.getCpf())
                );
                break;
            case 5:
                crudRemoverCliente();
                break;
            case 6:
                System.out.println("\n--- RELATÓRIO GERAL DE VENDAS ---");
                System.out.println(vendaController.gerarRelatorioVendas());
                break;
            case 0:
                adminLogado = false;
                System.out.println("Logoff administrativo concluído.");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    // ==========================================
    // MENU DO CLIENTE
    // ==========================================
    private static void menuCliente() {
        System.out.println("\n===== ÁREA DO CLIENTE - Olá, " + clienteLogado.getNome() + " =====");
        System.out.println("1. Ver Catálogo de Discos / Adicionar ao Carrinho");
        System.out.println("2. Ver Meu Carrinho / Finalizar Compra");
        System.out.println("3. Visualizar Histórico de Compras");
        System.out.println("4. Alterar Meus Dados de Cadastro (U)");
        System.out.println("0. Logoff (Sair da Conta)");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcaoInteira();
        switch (opcao) {
            case 1:
                adicionarItemAoCarrinho();
                break;
            case 2:
                gerenciarEFinalizarCarrinho();
                break;
            case 3:
                System.out.println("\n--- SEU HISTÓRICO DE COMPRAS ---");
                clienteLogado.visualizarHistoricoCompras();
                break;
            case 4:
                crudAlterarDadosCliente();
                break;
            case 0:
                clienteLogado = null;
                carrinhoAtual = null;
                System.out.println("Logoff efetuado com sucesso.");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    // ==========================================
    // LÓGICAS AUXILIARES E OPERAÇÕES CRUD
    // ==========================================
    
    private static void realizarLoginCliente() {
        System.out.print("Digite seu E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua Senha: ");
        String senha = scanner.nextLine();

        if (clienteController.login(email, senha)) {
            // Varre a lista do controlador para encontrar a referência do objeto logado
            for (ClienteModels c : clienteController.listarClientes()) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    clienteLogado = c;
                    break;
                }
            }
            System.out.println("Login efetuado com sucesso!");
        } else {
            System.out.println("E-mail ou senha incorretos.");
        }
    }

    private static void realizarLoginAdmin() {
        System.out.print("E-mail do Administrador: ");
        String email = scanner.nextLine();
        System.out.print("Senha do Administrador: ");
        String senha = scanner.nextLine();

        // Autenticação fixa simples para propósitos de teste do admin
        if (email.equals("admin@email.com") && senha.equals("123456")) {
            adminLogado = true;
            System.out.println("Autenticado como Administrador!");
        } else {
            System.out.println("Credenciais administrativas inválidas.");
        }
    }

    private static void crudCriarCliente() {
        System.out.println("\n--- CADASTRO DE NOVO CLIENTE ---");
        int id = clienteController.listarClientes().size() + 1;
        System.out.print("Nome Completo: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha de Acesso: ");
        String senha = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        ClienteModels novoCliente = new ClienteModels(id, nome, email, senha, telefone, cpf);
        clienteController.cadastrarCliente(novoCliente);
        System.out.println("Sua conta foi criada com sucesso! Faça login para continuar.");
    }

    private static void crudAlterarDadosCliente() {
        System.out.println("\n--- ATUALIZAR CADASTRO ---");
        System.out.print("Novo Nome (" + clienteLogado.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo Telefone (" + clienteLogado.getTelefone() + "): ");
        String telefone = scanner.nextLine();

        if (!nome.isBlank()) clienteLogado.setNome(nome);
        if (!telefone.isBlank()) clienteLogado.setTelefone(telefone);

        clienteController.alterarCadastro(clienteLogado);
        System.out.println("Dados atualizados com sucesso!");
    }

    private static void crudRemoverCliente() {
        System.out.print("Digite o ID do cliente que deseja remover: ");
        int id = lerOpcaoInteira();
        if (clienteController.buscarClientePorId(id) != null) {
            clienteController.removerCliente(id);
            System.out.println("Cliente removido do sistema.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void crudCadastrarProduto() {
        System.out.println("\n--- CADASTRAR NOVO PRODUTO ---");
        int id = produtoController.listarProdutos().size() + 1;
        System.out.print("Título do Álbum/Item: ");
        String nome = scanner.nextLine();
        System.out.print("Gênero Musical: ");
        String genero = scanner.nextLine();
        System.out.print("Artista/Banda: ");
        String artista = scanner.nextLine();
        System.out.print("Ano de Lançamento: ");
        int ano = lerOpcaoInteira();
        System.out.print("Preço Unitário: R$ ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantidade em Estoque Inicial: ");
        int qtd = lerOpcaoInteira();

        System.out.println("Selecione o Tipo de Produto:");
        System.out.println("1. VINIL | 2. CD | 3. FITA CASSETE | 4. CAMISETA | 5. POSTER");
        int tipoOpcao = lerOpcaoInteira();
        TipoProdutoModels tipo = TipoProdutoModels.VINIL;
        if (tipoOpcao == 2) tipo = TipoProdutoModels.CD;
        else if (tipoOpcao == 3) tipo = TipoProdutoModels.FITA_CASSETE;
        else if (tipoOpcao == 4) tipo = TipoProdutoModels.CAMISETA;
        else if (tipoOpcao == 5) tipo = TipoProdutoModels.POSTER;

        ProdutoModels novoProduto = new ProdutoModels(id, nome, genero, artista, ano, preco, qtd, tipo);
        produtoController.cadastrarProduto(novoProduto);
        System.out.println("Produto cadastrado com sucesso no catálogo!");
    }

    private static void crudExcluirProduto() {
        System.out.print("Digite o ID do produto a ser excluído do estoque: ");
        int id = lerOpcaoInteira();
        if (produtoController.buscarProdutoPorId(id) != null) {
            produtoController.excluirProduto(id);
            System.out.println("Produto removido com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void adicionarItemAoCarrinho() {
        System.out.println("\n--- CATÁLOGO DE DISCOS E PRODUTOS ---");
        List<ProdutoModels> produtos = produtoController.listarProdutos();
        produtos.forEach(p -> System.out.println("[" + p.getId() + "] " + p.getNome() + " - " + p.getArtista() + " (R$ " + p.getPreco() + ") | Estoque: " + p.getQtdEstoque()));

        System.out.print("\nDigite o ID do item que deseja comprar (ou 0 para voltar): ");
        int idProd = lerOpcaoInteira();
        if (idProd == 0) return;

        ProdutoModels produtoSelecionado = produtoController.buscarProdutoPorId(idProd);
        if (produtoSelecionado == null) {
            System.out.println("Produto inválido.");
            return;
        }

        System.out.print("Quantidade desejada: ");
        int quantidade = lerOpcaoInteira();
        if (quantidade > produtoSelecionado.getQtdEstoque()) {
            System.out.println("Quantidade indisponível! Estoque máximo atual: " + produtoSelecionado.getQtdEstoque());
            return;
        }

        // Se o cliente não tem um carrinho ativo nesta sessão, cria uma nova Venda
        if (carrinhoAtual == null) {
            int idVenda = vendaController.listarVendas().size() + 1;
            carrinhoAtual = new VendaModels(idVenda, clienteLogado);
        }

        ItemVendaModels novoItem = new ItemVendaModels(quantidade, produtoSelecionado);
        vendaController.adicionarItem(carrinhoAtual, novoItem);
        System.out.println(quantidade + "x '" + produtoSelecionado.getNome() + "' adicionado ao carrinho!");
    }

    private static void gerenciarEFinalizarCarrinho() {
        if (carrinhoAtual == null || carrinhoAtual.getItens().isEmpty()) {
            System.out.println("\nSeu carrinho está vazio no momento.");
            return;
        }

        System.out.println("\n===== SEU CARRINHO ATUAL =====");
        System.out.println(vendaController.gerarRelatorio(carrinhoAtual));
        System.out.println("==============================");
        System.out.println("1. Finalizar e Pagar a Compra");
        System.out.println("2. Esvaziar / Cancelar Carrinho");
        System.out.println("0. Voltar ao Menu");
        System.out.print("Opção: ");

        int opcao = lerOpcaoInteira();
        if (opcao == 1) {
            vendaController.finalizarVenda(carrinhoAtual);
            System.out.println("\n--- COMPRA CONCLUÍDA COM SUCESSO! ---");
            System.out.println(vendaController.emitirComprovante(carrinhoAtual));
            carrinhoAtual = null; // Zera o carrinho para a próxima compra
        } else if (opcao == 2) {
            carrinhoAtual = null;
            System.out.println("Carrinho cancelado e esvaziado.");
        }
    }

    // Auxiliares para evitar quebras de entrada do scanner
    private static int lerOpcaoInteira() {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void inicializarDadosFicticios() {
        // Cadastra um cliente administrador inicial padrão
        clienteController.cadastrarCliente(new ClienteModels(1, "Admin Global", "admin@email.com", "123456", "8499999", "000.000.000-00"));
        
        // Cadastra discos de exemplo para teste do catálogo
        produtoController.cadastrarProduto(new ProdutoModels(1, "Thriller", "Pop", "Michael Jackson", 1982, 120.0, 10, TipoProdutoModels.VINIL));
        produtoController.cadastrarProduto(new ProdutoModels(2, "Back In Black", "Rock", "AC/DC", 1980, 90.0, 5, TipoProdutoModels.CD));
    }
}