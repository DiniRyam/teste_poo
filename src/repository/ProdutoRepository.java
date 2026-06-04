package repository;

import model.ProdutoModels;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private final List<ProdutoModels> produtos = new ArrayList<>();

    public void cadastrarProduto(ProdutoModels produto) {
        produtos.add(produto);
    }

    public void editarProduto(ProdutoModels produtoAtualizado) {
        ProdutoModels produtoExistente = buscarProdutoPorId(produtoAtualizado.getId());
        if (produtoExistente != null) {
            produtos.remove(produtoExistente);
            produtos.add(produtoAtualizado);
        }
    }

    public void excluirProduto(int id) {
        ProdutoModels produto = buscarProdutoPorId(id);
        if (produto != null) {
            produtos.remove(produto);
        }
    }

    public ProdutoModels buscarProdutoPorId(int id) {
        for (ProdutoModels produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public List<ProdutoModels> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        List<ProdutoModels> resultado = new ArrayList<>();
        for (ProdutoModels produto : produtos) {
            boolean combinaNome = nomeBusca == null || nomeBusca.isBlank() || produto.getNome().toLowerCase().contains(nomeBusca.toLowerCase());
            boolean combinaArtista = artistaBusca == null || artistaBusca.isBlank() || produto.getArtista().toLowerCase().contains(artistaBusca.toLowerCase());
            boolean combinaGenero = generoBusca == null || generoBusca.isBlank() || produto.getGenero().toLowerCase().contains(generoBusca.toLowerCase());
            if (combinaNome && combinaArtista && combinaGenero) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    public List<ProdutoModels> listarProdutos() {
        return produtos;
    }
}