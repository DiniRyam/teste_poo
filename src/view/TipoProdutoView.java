package view;

import model.TipoProdutoModels;

public class TipoProdutoView {
    public void mostrarTipos() {
        for (TipoProdutoModels tipo : TipoProdutoModels.values()) {
            System.out.println(tipo);
        }
    }
}