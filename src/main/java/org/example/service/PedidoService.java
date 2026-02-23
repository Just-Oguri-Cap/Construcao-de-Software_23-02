package org.example.service;

import org.example.entity.Pedido;
import org.example.entity.StatusPedido;
import org.example.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    // CADASTRAR
    public void cadastrarPedido(String cliente, String descricao, double valor) {

        validarCliente(cliente);
        validarDescricao(descricao);
        validarValor(valor);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente.trim());
        pedido.setDescricao(descricao.trim());
        pedido.setValor(valor);
        pedido.setStatus(StatusPedido.CRIADO);       // corrigido: enum usa CRIADO
        pedido.setDataCriacao(LocalDateTime.now());

        repository.salvar(pedido);
    }


    // LISTAR
    public List<Pedido> listarPedidos() {
        return repository.listarTodos();
    }


    // BUSCAR POR ID
    public Pedido buscarPedidoPorId(int id) {              // retorna Pedido, não Optional
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado para o id: " + id));
        // orElseThrow: se o Optional estiver vazio, lança a exceção automaticamente
    }


    // ATUALIZAR (cliente, descrição e valor)
    public void atualizarPedido(int id, String novoCliente, String novaDescricao, double novoValor) {

        Pedido pedido = buscarPedidoPorId(id);

        validarStatusNaoCancelado(pedido);
        validarCliente(novoCliente);
        validarDescricao(novaDescricao);
        validarValor(novoValor);

        pedido.setCliente(novoCliente.trim());
        pedido.setDescricao(novaDescricao.trim());
        pedido.setValor(novoValor);

        repository.atualizar(pedido);
    }


    // REMOVER
    public void removerPedido(int id) {
        buscarPedidoPorId(id);          // garante que existe antes de tentar deletar
        repository.deletar(id);
    }


    // MARCAR COMO PAGO
    public void marcarComoPago(int id) {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatus() != StatusPedido.CRIADO) {
            throw new IllegalStateException("O pedido só pode ser marcado como PAGO se estiver CRIADO");
        }

        pedido.setStatus(StatusPedido.PAGO);
        repository.atualizar(pedido);
    }


    // MARCAR COMO ENVIADO
    public void marcarComoEnviado(int id) {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatus() != StatusPedido.PAGO) {
            throw new IllegalStateException("O pedido só pode ser enviado após estar PAGO");
        }

        pedido.setStatus(StatusPedido.ENVIADO);
        repository.atualizar(pedido);
    }

    // CANCELAR
    public void cancelarPedido(int id) {
        Pedido pedido = buscarPedidoPorId(id);

        if (pedido.getStatus() == StatusPedido.ENVIADO) {
            throw new IllegalStateException("Não é possível cancelar um pedido que já foi ENVIADO");
        }

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("O pedido já está CANCELADO");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        repository.atualizar(pedido);
    }

    // VALIDAÇÕES PRIVADAS
    private void validarCliente(String cliente) {
        if (cliente == null || cliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Cliente é obrigatório");
        }
        if (cliente.trim().length() < 3) {
            throw new IllegalArgumentException("Cliente deve ter no mínimo 3 caracteres");
        }
    }

    private void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (descricao.trim().length() < 5) {
            throw new IllegalArgumentException("Descrição deve ter no mínimo 5 caracteres");
        }
    }

    private void validarValor(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }

    private void validarStatusNaoCancelado(Pedido pedido) {
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Não é possível alterar um pedido CANCELADO");
        }
    }
}