package view;

import model.PessoaModels;

public class PessoaView {
    public void mostrarPessoa(PessoaModels pessoa) {
        System.out.println(pessoa.getNome());
    }
}