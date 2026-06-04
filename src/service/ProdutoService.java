package service;

import model.ProdutoModels;
import repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService() {
        this(new ProdutoRepository());
    }

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void cadastrarProduto() {
    }

    public void cadastrarProduto(ProdutoModels produto) {
        repository.cadastrarProduto(produto);
    }

    public void editarProduto() {
    }

    public void editarProduto(ProdutoModels produto) {
        repository.editarProduto(produto);
    }

    public void excluirProduto() {
    }

    public void excluirProduto(int id) {
        repository.excluirProduto(id);
    }

    public ProdutoModels buscarProdutoPorId(int id) {
        return repository.buscarProdutoPorId(id);
    }

    public List<ProdutoModels> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        return repository.buscarProdutos(nomeBusca, artistaBusca, generoBusca);
    }

    public List<ProdutoModels> listarProdutos() {
        return repository.listarProdutos();
    }
}