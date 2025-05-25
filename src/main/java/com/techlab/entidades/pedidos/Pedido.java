package com.techlab.entidades.pedidos;

import java.util.ArrayList;

public class Pedido {
    private int id;
    private ArrayList<LineaPedido> lineasPedido;
    private double precioTotal;

    public Pedido() {
    }

    public Pedido(int id, ArrayList<LineaPedido> lineasPedido) {
        this.id = id;
        this.lineasPedido = lineasPedido;
    }

    public Pedido(int id, ArrayList<LineaPedido> lineasPedido, double precioTotal) {
        this.id = id;
        this.lineasPedido = lineasPedido;
        this.precioTotal = precioTotal;
    }

    public double calcularTotal(ArrayList<LineaPedido> lineasPedido){
        double total = 0;
        for (LineaPedido lineaPedido : lineasPedido){
            total = total + lineaPedido.getSubTotal();
        }
        return total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(ArrayList<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
}
