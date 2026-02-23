package org.example.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Pedido {
    private Integer id;//auto incrementado
    private String cliente;//min 3 char
    private String descricao;//min 3 char
    private Double valor;//maior que 0
    private StatusPedido status;
    private LocalDateTime dataCriacao;


    public Pedido(){
    }

    public Pedido(String cliente, Integer id, String descricao, Double valor, StatusPedido status, LocalDateTime dataCriacao) {
        this.cliente = cliente;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCliente() { return cliente; }

    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getValor() {return valor;}

    public void setValor(Double valor) { this.valor = valor; }

    public StatusPedido getStatus() { return status; }

    public void setStatus(StatusPedido status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }

    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    @Override
    public String toString(){
        return "Pedido{" +
                "id=" + id +
                ", cliente='" + cliente + '\'' +
                ", descricao=" + descricao +
                ", valor='" + valor + '\'' +
                ", status='" + status + '\'' +
                ", data de criacao='" + dataCriacao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido that = (Pedido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}