package repository;

import model.ClienteModels;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private final List<ClienteModels> clientes = new ArrayList<>();

    public void cadastrarCliente(ClienteModels cliente) {
        clientes.add(cliente);
    }

    public void consultarTodosClientes() {
        for (ClienteModels cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void alterarCadastro(ClienteModels clienteAtualizado) {
        ClienteModels clienteExistente = buscarClientePorId(clienteAtualizado.getId());
        if (clienteExistente != null) {
            clientes.remove(clienteExistente);
            clientes.add(clienteAtualizado);
        }
    }

    public void removerCliente(int id) {
        ClienteModels cliente = buscarClientePorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
        }
    }

    public ClienteModels buscarClientePorId(int id) {
        for (ClienteModels cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public List<ClienteModels> listarClientes() {
        return clientes;
    }
}