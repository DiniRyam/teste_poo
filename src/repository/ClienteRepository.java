package repository;

import model.ClienteModels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private List<ClienteModels> clientes = new ArrayList<>();
    private final String ARQUIVO = "clientes.json"; // Ficheiro para os clientes
    private final Gson gson;

    public ClienteRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        carregarDados(); // Carrega os dados guardados ao iniciar
    }

    // --- MÉTODOS DE PERSISTÊNCIA ---

    private void salvarDados() {
        try (Writer writer = new FileWriter(ARQUIVO)) {
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File file = new File(ARQUIVO);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<ClienteModels>>(){}.getType();
                clientes = gson.fromJson(reader, listType);
                if (clientes == null) clientes = new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Erro ao carregar clientes: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS CRUD (Com salvamento automático) ---

    public void cadastrarCliente(ClienteModels cliente) {
        clientes.add(cliente);
        salvarDados(); // Guarda no JSON
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
            salvarDados(); // Guarda no JSON
        }
    }

    public void removerCliente(int id) {
        ClienteModels cliente = buscarClientePorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
            salvarDados(); // Guarda no JSON
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