package controller;

import model.ItemVendaModels;

public class ItemVendaController {
    public double calcularSubtotal(ItemVendaModels item) {
        return item.calcularSubtotal();
    }

    public void alterarItem(ItemVendaModels item, int novaQuantidade) {
        item.alterarItem(novaQuantidade);
    }
}