package controller;

import model.ItemVendaModels;
import model.VendaModels;
import service.VendaService;
import repository.VendaRepository;

import java.util.List;

public class VendaController {

    private final VendaService service;

    public VendaController() {
        this(new VendaService(new VendaRepository()));
    }

    public VendaController(VendaService service) {
        this.service = service;
    }

    public void editarVenda() {
        service.editarVenda();
    }

    public void editarVenda(VendaModels venda) {
        service.editarVenda(venda);
    }

    public void adicionarItem(ItemVendaModels itemVenda) {
        service.adicionarItem(itemVenda);
    }

    public void adicionarItem(VendaModels venda, ItemVendaModels item) {
        service.adicionarItem(venda, item);
    }

    public void removerItem(ItemVendaModels itemVenda) {
        service.removerItem(itemVenda);
    }

    public void removerItem(VendaModels venda, ItemVendaModels item) {
        service.removerItem(venda, item);
    }

    public void finalizarVenda() {
        service.finalizarVenda();
    }

    public void finalizarVenda(VendaModels venda) {
        service.finalizarVenda(venda);
    }

    public String gerarRelatorioVendas() {
        return service.gerarRelatorioVendas();
    }

    public String gerarRelatorio(VendaModels venda) {
        return service.gerarRelatorioVendas(venda);
    }

    public String emitirComprovante() {
        return service.emitirComprovante();
    }

    public String emitirComprovante(VendaModels venda) {
        return service.emitirComprovante(venda);
    }

    public void alterarItem() {
        service.alterarItem();
    }

    public void alterarItem(ItemVendaModels itemVenda, int novaQuantidade) {
        service.alterarItem(itemVenda, novaQuantidade);
    }

    public List<VendaModels> listarVendas() {
        return service.listarVendas();
    }
}