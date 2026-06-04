package repository;

import model.VendaModels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository {

    private List<VendaModels> vendas = new ArrayList<>();
    private final String ARQUIVO = "vendas.json"; // Ficheiro para as vendas gerais
    private final Gson gson;

    public VendaRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        carregarDados(); // Carrega as vendas ao iniciar
    }

    // --- MÉTODOS DE PERSISTÊNCIA ---

    private void salvarDados() {
        try (Writer writer = new FileWriter(ARQUIVO)) {
            gson.toJson(vendas, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar vendas: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File file = new File(ARQUIVO);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<VendaModels>>(){}.getType();
                vendas = gson.fromJson(reader, listType);
                if (vendas == null) vendas = new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Erro ao carregar vendas: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS DE GESTÃO DE VENDAS ---

    public void editarVenda(VendaModels venda) {
        VendaModels vendaExistente = buscarVendaPorId(venda.getId());
        if (vendaExistente != null) {
            vendas.remove(vendaExistente);
            vendas.add(venda);
            salvarDados(); // Guarda no JSON
        }
    }

    public void finalizarVenda(VendaModels venda) {
        if (!vendas.contains(venda)) {
            vendas.add(venda);
            salvarDados(); // Guarda no JSON
        }
    }

    public List<VendaModels> listarVendas() {
        return vendas;
    }

    public VendaModels buscarVendaPorId(int id) {
        for (VendaModels venda : vendas) {
            if (venda.getId() == id) {
                return venda;
            }
        }
        return null;
    }
}