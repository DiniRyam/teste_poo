package model;

import java.util.Date;

public class ComprovanteModels {

    private int id;
    private Date data;
    private double valorTotal;

    public ComprovanteModels(int id, double valorTotal) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.data = new Date();
    }

    public void emitirComprovante() {
        System.out.println(exibirComprovante());
    }

    public String exibirComprovante() {
        return "Comprovante #" + id +
              "\nData: " + data +
               "\nTotal: R$ " + valorTotal;
    }
}