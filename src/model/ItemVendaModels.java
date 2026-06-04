package model;

public class ItemVendaModels {

    private int quantidade;
    private ProdutoModels produto;
    private double subtotal;

    public ItemVendaModels(int quantidade, ProdutoModels produto) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.subtotal = calcularSubtotal();
    }

    public double calcularSubtotal() {
        subtotal = quantidade * produto.getPreco();
        return subtotal;
    }

    public void alterarItem(int novaQuantidade) {
        this.quantidade = novaQuantidade;
        calcularSubtotal();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public ProdutoModels getProduto() {
        return produto;
    }

    public void setProduto(ProdutoModels produto) {
        this.produto = produto;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    @Override
    public String toString() {
        return produto.getNome() +
                " x" + quantidade +
                " = R$ " + subtotal;
    }
}