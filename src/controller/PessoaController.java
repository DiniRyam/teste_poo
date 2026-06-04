package controller;

import model.PessoaModels;

public class PessoaController {
    public boolean login(PessoaModels pessoa, String email, String senha) {
        return pessoa.login(email, senha);
    }

    public void verificarConta(PessoaModels pessoa, String email, String senha) {
        pessoa.verificarConta(email, senha);
    }
}
