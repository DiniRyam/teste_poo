package controller;

import model.ComprovanteModels;

public class ComprovanteController {
    public void emitirComprovante(ComprovanteModels comprovante) {
        comprovante.emitirComprovante();
    }

    public String exibirComprovante(ComprovanteModels comprovante) {
        return comprovante.exibirComprovante();
    }
}