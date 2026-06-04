package view;

import model.VendaModels;
import java.util.List;

public class VendaView {
    public void mostrarVenda(VendaModels venda) {
        System.out.println(venda);
    }

    public void listarVendas(List<VendaModels> vendas) {
        for (VendaModels venda : vendas) {
            System.out.println(venda);
        }
    }

    public void mostrarRelatorio(String relatorio) {
        System.out.println(relatorio);
    }
}