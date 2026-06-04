package controller;

import model.TipoProdutoModels;

public class TipoProdutoController {
    public void listarTipos() {
        for (TipoProdutoModels tipo : TipoProdutoModels.values()) {
            System.out.println(tipo);
        }
    }
}