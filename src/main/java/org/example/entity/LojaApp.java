package org.example.entity;

import org.example.controller.PedidoController;
import org.example.repository.PedidoRepository;
import org.example.repository.PedidoRepositoryMemory;
import org.example.service.PedidoService;

public class LojaApp {
    public static void main(String[] args) {
        PedidoRepository repository = new PedidoRepositoryMemory();
        PedidoService service = new PedidoService(repository);
        PedidoController controller = new PedidoController(service);

       controller.executar();

    }
}