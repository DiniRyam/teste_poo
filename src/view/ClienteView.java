package view;

import model.ClienteModels;
import java.util.List;

public class ClienteView {
    public void mostrarCliente(ClienteModels cliente) {
        System.out.println(cliente);
    }

    public void listarClientes(List<ClienteModels> clientes) {
        for (ClienteModels cliente : clientes) {
            System.out.println(cliente);
        }
    }
}