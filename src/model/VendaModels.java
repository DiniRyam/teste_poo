package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaModels {
    private int id;
    private Date data;
    private double valorTotal;
    
    // TRANSIENT impede que o Gson salve este objeto, evitando loop infinito (StackOverflow)
    private transient ClienteModels cliente; 
    private List<ItemVendaModels> itens;
    private boolean finalizada;

    public VendaModels(int id, ClienteModels cliente) {
        this.id = id;
        this.cliente = cliente;
        this.data = new Date();
        this.itens = new ArrayList<>();
        this.finalizada = false;
    }

    public void adicionarItem(ItemVendaModels item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemVendaModels item) {
        itens.remove(item);
        calcularTotal();
    }

    public double calcularTotal() {
        valorTotal = 0;
        for (ItemVendaModels item : itens) {
            valorTotal += item.getSubtotal();
        }
        return valorTotal;
    }

    public void finalizarVenda() {
        for (ItemVendaModels item : itens) {
            ProdutoModels produto = item.getProduto();
            produto.setQtdEstoque(produto.getQtdEstoque() - item.getQuantidade());
        }
        this.finalizada = true;
        if (this.cliente != null) {
            this.cliente.adicionarCompra(this);
        }
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public double getValorTotal() { return valorTotal; }
    public ClienteModels getCliente() { return cliente; }
    public void setCliente(ClienteModels cliente) { this.cliente = cliente; }
    public List<ItemVendaModels> getItens() { return itens; }
    public boolean isFinalizada() { return finalizada; }
    public void setFinalizada(boolean finalizada) { this.finalizada = finalizada; }

    public String gerarRelatorioVendas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda: ").append(id).append("\n");
        for (ItemVendaModels item : itens) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("Total: R$ ").append(calcularTotal());
        return sb.toString();
    }

    public String emitirComprovante() {
        return "=== COMPROVANTE ===\n" +
               "Venda ID: " + id + "\n" +
               "Data: " + data + "\n" +
               "Cliente: " + (cliente != null ? cliente.getNome() : "N/A") + "\n" +
               gerarRelatorioVendas() + "\n===================";
    }

    @Override
    public String toString() {
        return "Venda #" + id + " | Total: R$ " + valorTotal + " | Finalizada: " + finalizada;
    }
}