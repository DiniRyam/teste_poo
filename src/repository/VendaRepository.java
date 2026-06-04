package repository;

import model.VendaModels;

import java.util.ArrayList;
import java.util.List;

public class VendaRepository {

    private final List<VendaModels> vendas = new ArrayList<>();

    public void editarVenda(VendaModels venda) {
        VendaModels vendaExistente = buscarVendaPorId(venda.getId());
        if (vendaExistente != null) {
            vendas.remove(vendaExistente);
            vendas.add(venda);
        }
    }

    public void finalizarVenda(VendaModels venda) {
        if (!vendas.contains(venda)) {
            vendas.add(venda);
        }
    }

    public List<VendaModels> listarVendas() {
        return vendas;
    }

    public VendaModels buscarVendaPorId(int id) {
        for (VendaModels venda : vendas) {
            if (venda.getId() == id) {
                return venda;
            }
        }
        return null;
    }
}