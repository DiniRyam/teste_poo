import controller.*;
import repository.*;
import service.*;
import model.*;
import view.*;

public class App {

    public static void main(String[] args) {

        ClienteRepository clienteRepository = new ClienteRepository();
        ProdutoRepository produtoRepository = new ProdutoRepository();
        VendaRepository vendaRepository = new VendaRepository();

        ClienteService clienteService = new ClienteService(clienteRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        VendaService vendaService = new VendaService(vendaRepository);

        ClienteController clienteController = new ClienteController(clienteService);
        ProdutoController produtoController = new ProdutoController(produtoService);
        VendaController vendaController = new VendaController(vendaService);

        ClienteView clienteView = new ClienteView();
        ProdutoView produtoView = new ProdutoView();
        VendaView vendaView = new VendaView();

        ClienteModels cliente = new ClienteModels(
            1,
            "Admin",
            "Admin@email.com",
            "123456",
            "84999999999",
            "123.456.789-00"
        );

        clienteController.cadastrarCliente(cliente);

        ProdutoModels produto1 = new ProdutoModels(
			1,
			"Thriller",
			"Pop",
			"Michael Jackson",
			1982,
			120.00,
			10,
            TipoProdutoModels.VINIL
        );

        ProdutoModels produto2 = new ProdutoModels(
			2,
			"Back In Black",
			"Rock",
			"AC/DC",
			1980,
			90.00,
			5,
			TipoProdutoModels.CD
        );

        produtoController.cadastrarProduto(produto1);
        produtoController.cadastrarProduto(produto2);

        System.out.println("===== CLIENTE =====");
        clienteView.mostrarCliente(cliente);

        System.out.println("\n===== PRODUTOS =====");
        produtoView.listarProdutos(produtoController.listarProdutos());

        VendaModels venda = new VendaModels(1, cliente);

        ItemVendaModels item1 = new ItemVendaModels(2, produto1);
        ItemVendaModels item2 = new ItemVendaModels(1, produto2);

        vendaController.adicionarItem(venda, item1);
        vendaController.adicionarItem(venda, item2);

        System.out.println("\n===== RELATÓRIO DA VENDA =====");
        vendaView.mostrarRelatorio(
                vendaController.gerarRelatorio(venda)
        );

        vendaController.finalizarVenda(venda);

        System.out.println("\n===== COMPROVANTE =====");
        System.out.println(
                vendaController.emitirComprovante(venda)
        );

        System.out.println("\n===== HISTÓRICO DE COMPRAS =====");
        cliente.visualizarHistoricoCompras();

        System.out.println("\n===== ESTOQUE APÓS VENDA =====");
        produtoView.listarProdutos(produtoController.listarProdutos());
    }
}