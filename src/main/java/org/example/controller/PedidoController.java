package org.example.controller;

import org.example.entity.Pedido;
import org.example.service.PedidoService;

import java.util.List;
import java.util.Scanner;

public class PedidoController {

    private final PedidoService service;
    private final Scanner scanner;

    public PedidoController(PedidoService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void executar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInt("Escolha uma opção");

            switch (opcao) {
                case 1 -> cadastrarPedido();
                case 2 -> listarPedidos();
                case 3 -> buscarPedido();
                case 4 -> atualizarPedido();
                case 5 -> marcarComoPago();
                case 6 -> marcarComoEnviado();
                case 7 -> cancelarPedido();
                case 8 -> removerPedido();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);
    }


    // MENU
    private void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GESTÃO DE PEDIDOS ===");
        System.out.println("1. Cadastrar Pedido");
        System.out.println("2. Listar Todos os Pedidos");
        System.out.println("3. Buscar Pedido por ID");
        System.out.println("4. Atualizar Pedido");
        System.out.println("5. Marcar como Pago");
        System.out.println("6. Marcar como Enviado");
        System.out.println("7. Cancelar Pedido");
        System.out.println("8. Remover Pedido");
        System.out.println("0. Sair");
    }


    // AÇÕES
    private void cadastrarPedido() {
        System.out.println("\n--- Cadastrar Pedido ---");
        String cliente = lerTexto("Cliente");
        String descricao = lerTexto("Descrição");
        double valor = lerDouble("Valor");

        try {
            service.cadastrarPedido(cliente, descricao, valor);
            System.out.println("Pedido cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        List<Pedido> pedidos = service.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s %-20s%n",
                "ID", "Cliente", "Valor", "Status", "Data");
        System.out.println("-".repeat(70));

        for (Pedido pedido : pedidos) {
            System.out.printf("%-5d %-20s %-10.2f %-10s %-20s%n",
                    pedido.getId(),
                    pedido.getCliente(),
                    pedido.getValor(),
                    pedido.getStatus(),
                    pedido.getDataCriacao().toLocalDate());
        }
    }

    private void buscarPedido() {
        System.out.println("\n--- Buscar Pedido ---");
        int id = lerInt("ID do pedido");

        try {
            Pedido pedido = service.buscarPedidoPorId(id);
            exibirPedido(pedido);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void atualizarPedido() {
        System.out.println("\n--- Atualizar Pedido ---");
        int id = lerInt("ID do pedido");
        String novoCliente = lerTexto("Novo cliente");
        String novaDescricao = lerTexto("Nova descrição");
        double novoValor = lerDouble("Novo valor");

        try {
            service.atualizarPedido(id, novoCliente, novaDescricao, novoValor);
            System.out.println("Pedido atualizado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerPedido() {
        System.out.println("\n--- Remover Pedido ---");
        int id = lerInt("ID do pedido");

        System.out.print("Tem certeza que deseja remover o pedido #" + id + "? (s/n): ");
        String confirmacao = scanner.nextLine().trim().toLowerCase();

        if (!confirmacao.equals("s")) {
            System.out.println("Remoção cancelada.");
            return;
        }

        try {
            service.removerPedido(id);
            System.out.println("Pedido removido com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void marcarComoPago() {
        System.out.println("\n--- Marcar como PAGO ---");
        int id = lerInt("ID do pedido");

        try {
            service.marcarComoPago(id);
            System.out.println("Pedido marcado como PAGO!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void marcarComoEnviado() {
        System.out.println("\n--- Marcar como ENVIADO ---");
        int id = lerInt("ID do pedido");

        try {
            service.marcarComoEnviado(id);
            System.out.println("Pedido marcado como ENVIADO!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelarPedido() {
        System.out.println("\n--- Cancelar Pedido ---");
        int id = lerInt("ID do pedido");

        try {
            service.cancelarPedido(id);
            System.out.println("Pedido cancelado com sucesso!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // HELPERS DE LEITURA
    private String lerTexto(String campo) {
        System.out.print(campo + ": ");
        return scanner.nextLine();
    }

    private int lerInt(String campo) {
        System.out.print(campo + ": ");
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido para " + campo + ": ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private double lerDouble(String campo) {
        System.out.print(campo + ": ");
        while (!scanner.hasNextDouble()) {
            System.out.print("Digite um valor válido para " + campo + ": ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    // EXIBIÇÃO
    private void exibirPedido(Pedido pedido) {
        System.out.println("---------------------------");
        System.out.println("ID:        " + pedido.getId());
        System.out.println("Cliente:   " + pedido.getCliente());
        System.out.println("Descrição: " + pedido.getDescricao());
        System.out.println("Valor:     R$ " + pedido.getValor());
        System.out.println("Status:    " + pedido.getStatus());
        System.out.println("Criado em: " + pedido.getDataCriacao());
        System.out.println("---------------------------");
    }
}