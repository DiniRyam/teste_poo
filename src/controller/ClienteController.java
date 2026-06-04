package controller;

import model.ClienteModels;
import repository.ClienteRepository;
import service.ClienteService;

import java.util.List;

public class ClienteController {

    private final ClienteService service;

    public ClienteController() {
        this(new ClienteService(new ClienteRepository()));
    }

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    public boolean login(String email, String senha) {
        return service.login(email, senha);
    }

    public void cadastrarCliente() {
        service.cadastrarCliente();
    }

    public void cadastrarCliente(ClienteModels cliente) {
        service.cadastrarCliente(cliente);
    }

    public void consultarTodosClientes() {
        service.consultarTodosClientes();
    }

    public List<ClienteModels> listarClientes() {
        return service.listarClientes();
    }

    public void alterarCadastro() {
        service.alterarCadastro();
    }

    public void alterarCadastro(ClienteModels cliente) {
        service.alterarCadastro(cliente);
    }

    public void removerCliente() {
        service.removerCliente();
    }

    public void removerCliente(int id) {
        service.removerCliente(id);
    }

    public ClienteModels buscarClientePorId(int id) {
        return service.buscarClientePorId(id);
    }

    public void visualizarHistoricoCompras() {
        service.visualizarHistoricoCompras();
    }
}