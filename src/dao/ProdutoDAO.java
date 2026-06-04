package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.ClienteModels;
import model.VendaModels;
import java.io.*;
import java.nio.file.*;

public class ProdutoDAO {
    private static final String BASE_DIR = "cliente";
    private final Gson gson;

    public ProdutoDAO() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }

    private Path getClienteDir(int id) { return Paths.get(BASE_DIR + id); }
    private Path getClienteFilePath(int id) { return getClienteDir(id).resolve("dados.json"); }

    public boolean create(ClienteModels cliente) {
        try {
            Files.createDirectories(getClienteDir(cliente.getId()));
            try (FileWriter writer = new FileWriter(getClienteFilePath(cliente.getId()).toFile())) {
                gson.toJson(cliente, writer);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao criar cliente: " + e.getMessage());
            return false;
        }
    }

    public ClienteModels read(int id) {
        Path filePath = getClienteFilePath(id);
        if (!Files.exists(filePath)) return null;

        try (FileReader reader = new FileReader(filePath.toFile())) {
            ClienteModels cliente = gson.fromJson(reader, ClienteModels.class);
            // Restaura a referência circular em memória após a leitura
            if (cliente != null && cliente.getHistoricoCompras() != null) {
                for (VendaModels venda : cliente.getHistoricoCompras()) {
                    venda.setCliente(cliente);
                }
            }
            return cliente;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean update(ClienteModels cliente) {
        return create(cliente); // Sobrescreve o arquivo, funcionando como update
    }

    public boolean delete(int id) {
        Path dir = getClienteDir(id);
        if (!Files.exists(dir)) return false;
        try {
            Files.walk(dir).sorted((a, b) -> -a.compareTo(b)).forEach(path -> {
                try { Files.delete(path); } catch (IOException ignored) {}
            });
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}