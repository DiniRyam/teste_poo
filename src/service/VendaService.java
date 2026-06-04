package service;

import model.ComprovanteModels;
import model.ItemVendaModels;
import model.VendaModels;
import repository.VendaRepository;

import java.util.List;

public class VendaService {

    private final VendaRepository repository;

    public VendaService() {
        this(new VendaRepository());
    }

    public VendaService(VendaRepository repository) {
        this.repository = repository;
    }

    public void editarVenda() {
    }

    public void editarVenda(VendaModels venda) {
        repository.editarVenda(venda);
    }

    public void adicionarItem(ItemVendaModels itemVenda) {
    }

    public void adicionarItem(VendaModels venda, ItemVendaModels item) {
        venda.adicionarItem(item);
    }

    public void removerItem(ItemVendaModels itemVenda) {
    }

    public void removerItem(VendaModels venda, ItemVendaModels item) {
        venda.getItens().remove(item);
    }

    public double calcularTotal() {
        return 0;
    }

    public double calcularSubtotal() {
        return 0;
    }

    public void alterarItem() {
    }

    public void alterarItem(ItemVendaModels itemVenda, int novaQuantidade) {
        itemVenda.alterarItem(novaQuantidade);
    }

    public void finalizarVenda() {
    }

    public void finalizarVenda(VendaModels venda) {
        venda.finalizarVenda();
        repository.finalizarVenda(venda);
    }

    public String gerarRelatorioVendas() {
        StringBuilder sb = new StringBuilder();
        for (VendaModels venda : repository.listarVendas()) {
            sb.append(venda.gerarRelatorioVendas()).append("\n");
        }
        return sb.toString();
    }

    public String gerarRelatorioVendas(VendaModels venda) {
        return venda.gerarRelatorioVendas();
    }

    public String emitirComprovante() {
        if (repository.listarVendas().isEmpty()) {
            return "Sem comprovante disponível.";
        }
        VendaModels venda = repository.listarVendas().get(repository.listarVendas().size() - 1);
        return venda.emitirComprovante();
    }

    public String emitirComprovante(VendaModels venda) {
        return venda.emitirComprovante();
    }

    public List<VendaModels> listarVendas() {
        return repository.listarVendas();
    }
}