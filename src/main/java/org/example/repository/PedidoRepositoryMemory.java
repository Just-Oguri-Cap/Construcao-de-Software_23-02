package org.example.repository;

import org.example.entity.Pedido;

import java.util.*;

public class PedidoRepositoryMemory implements PedidoRepository {
    private final Map<Integer, Pedido> pedidos = new HashMap<>();

    private int geradorDeId = 1;

    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(geradorDeId);
            geradorDeId++;
        }
        pedidos.put(pedido.getId(),pedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> buscarPorId(int id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return pedidos.values().stream().toList();
    }





    @Override
    public boolean atualizar(Pedido pedido) {

        Pedido pedido1 = pedidos.get(pedido.getId());
        if (pedido1 != null) {
            pedidos.replace(pedido1.getId(), pedido);
            return true;
        }

        return false;
    }

    @Override
    public boolean deletar(int id) {

        Pedido pedido1 = pedidos.get(id);
        if (pedido1 != null) {
            pedidos.remove(pedido1.getId());
            return true;
        }

        return false;

    }

    @Override
    public int contar() {
        return pedidos.size();
    }


}
