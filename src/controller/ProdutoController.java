package controller;

import model.ProdutoModels;
import service.ProdutoService;
import repository.ProdutoRepository;

import java.util.List;

public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController() {
        this(new ProdutoService(new ProdutoRepository()));
    }

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    public void cadastrarProduto() {
        service.cadastrarProduto();
    }

    public void cadastrarProduto(ProdutoModels produto) {
        service.cadastrarProduto(produto);
    }

    public void exibirInformacoes() {
        service.listarProdutos().forEach(System.out::println);
    }

    public void exibirTodosProdutos() {
        service.listarProdutos().forEach(System.out::println);
    }

    public void editarProduto() {
        service.editarProduto();
    }

    public void editarProduto(ProdutoModels produto) {
        service.editarProduto(produto);
    }

    public void excluirProduto() {
        service.excluirProduto();
    }

    public void excluirProduto(int id) {
        service.excluirProduto(id);
    }

    public ProdutoModels buscarProdutoPorId(int id) {
        return service.buscarProdutoPorId(id);
    }

    public List<ProdutoModels> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        return service.buscarProdutos(nomeBusca, artistaBusca, generoBusca);
    }

    public List<ProdutoModels> listarProdutos() {
        return service.listarProdutos();
    }
}