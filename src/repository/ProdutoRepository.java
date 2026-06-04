package repository;

import model.ProdutoModels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private List<ProdutoModels> produtos = new ArrayList<>();
    private final String ARQUIVO = "produtos.json"; // Nome do ficheiro visível na raiz
    private final Gson gson;

    public ProdutoRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        carregarDados(); // Carrega o JSON assim que o sistema liga
    }

    // --- MÉTODOS DE PERSISTÊNCIA ---

    private void salvarDados() {
        try (Writer writer = new FileWriter(ARQUIVO)) {
            gson.toJson(produtos, writer);
            
            // ISTO VAI DIZER-LHE EXATAMENTE ONDE O FICHEIRO ESTÁ A SER GUARDADO
            File ficheiro = new File(ARQUIVO);
               
        } catch (IOException e) {
            System.err.println("Erro ao salvar produtos: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File file = new File(ARQUIVO);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<ProdutoModels>>(){}.getType();
                produtos = gson.fromJson(reader, listType);
                if (produtos == null) produtos = new ArrayList<>();
            } catch (IOException e) {
                System.err.println("Erro ao carregar produtos: " + e.getMessage());
            }
        }
    }

    // --- MÉTODOS CRUD (Modificados para salvar após cada alteração) ---

    public void cadastrarProduto(ProdutoModels produto) {
        produtos.add(produto);
        salvarDados(); // Guarda no JSON imediatamente
    }

    public void editarProduto(ProdutoModels produtoAtualizado) {
        ProdutoModels produtoExistente = buscarProdutoPorId(produtoAtualizado.getId());
        if (produtoExistente != null) {
            produtos.remove(produtoExistente);
            produtos.add(produtoAtualizado);
            salvarDados(); // Guarda no JSON
        }
    }

    public void excluirProduto(int id) {
        ProdutoModels produto = buscarProdutoPorId(id);
        if (produto != null) {
            produtos.remove(produto);
            salvarDados(); // Guarda no JSON
        }
    }

    // (Mantenha os métodos buscarProdutoPorId, buscarProdutos e listarProdutos iguais ao original, eles apenas lêem a lista)
    public ProdutoModels buscarProdutoPorId(int id) {
        for (ProdutoModels produto : produtos) {
            if (produto.getId() == id) return produto;
        }
        return null;
    }

    public List<ProdutoModels> listarProdutos() {
        return produtos;
    }

    // --- MÉTODO DE BUSCA RESTAURADO ---
    public List<ProdutoModels> buscarProdutos(String nomeBusca, String artistaBusca, String generoBusca) {
        List<ProdutoModels> resultado = new ArrayList<>();
        for (ProdutoModels produto : produtos) {
            boolean combinaNome = nomeBusca == null || nomeBusca.isBlank() || produto.getNome().toLowerCase().contains(nomeBusca.toLowerCase());
            boolean combinaArtista = artistaBusca == null || artistaBusca.isBlank() || produto.getArtista().toLowerCase().contains(artistaBusca.toLowerCase());
            boolean combinaGenero = generoBusca == null || generoBusca.isBlank() || produto.getGenero().toLowerCase().contains(generoBusca.toLowerCase());
            if (combinaNome && combinaArtista && combinaGenero) {
                resultado.add(produto);
            }
        }
        return resultado;
    }
}