package service;

import model.ClienteModels;
import repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService() {
        this(new ClienteRepository());
    }

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public boolean login(String email, String senha) {
        for (ClienteModels cliente : repository.listarClientes()) {
            if (cliente.login(email, senha)) {
                return true;
            }
        }
        return false;
    }

    public void verificarConta(String email, String senha) {
        if (login(email, senha)) {
            System.out.println("Login realizado com sucesso.");
        } 
        else {
            System.out.println("Login inválido.");
        }
    }

    public void cadastrarCliente() {
    }

    public void cadastrarCliente(ClienteModels cliente) {
        repository.cadastrarCliente(cliente);
    }

    public void consultarTodosClientes() {
        repository.consultarTodosClientes();
    }

    public List<ClienteModels> listarClientes() {
        return repository.listarClientes();
    }

    public void alterarCadastro() {
    }

    public void alterarCadastro(ClienteModels cliente) {
        repository.alterarCadastro(cliente);
    }

    public void removerCliente() {
    }

    public void removerCliente(int id) {
        repository.removerCliente(id);
    }

    public ClienteModels buscarClientePorId(int id) {
        return repository.buscarClientePorId(id);
    }

    public void visualizarHistoricoCompras() {
    }
}