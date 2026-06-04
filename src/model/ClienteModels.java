package model;

import java.util.ArrayList;
import java.util.List;

public class ClienteModels extends PessoaModels {

    private String telefone;
    private String cpf;
    private List<VendaModels> historicoCompras;

    public ClienteModels(int id, String nome, String email, String senha, String telefone, String cpf) {
        super(id, nome, email, senha, false);
        this.telefone = telefone;
        this.cpf = cpf;
        this.historicoCompras = new ArrayList<>();
    }

    public void adicionarCompra(VendaModels venda) {
        historicoCompras.add(venda);
    }

    public List<VendaModels> getHistoricoCompras() {
        return historicoCompras;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void visualizarHistoricoCompras() {
        for (VendaModels venda : historicoCompras) {
            System.out.println(venda);
        }
    }

    @Override
    public String toString() {
        return nome + " - " + cpf;
    }
}