package view;

import model.ProdutoModels;
import java.util.List;

public class ProdutoView {
    public void mostrarProduto(ProdutoModels produto) {
        System.out.println(produto);
    }

    public void listarProdutos(List<ProdutoModels> produtos) {
        for (ProdutoModels produto : produtos) {
            System.out.println(produto);
        }
    }
}