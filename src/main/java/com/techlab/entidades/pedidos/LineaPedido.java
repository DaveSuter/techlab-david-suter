package com.techlab.entidades.pedidos;

import com.techlab.entidades.productos.Producto;

public class LineaPedido {
    private int id;
    private Object producto;
    private int cantidad;
    private double subTotal;

    public LineaPedido() {
    }

    public LineaPedido(int id, Producto producto, int cantidad, double subTotal) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getProducto() {
        return producto;
    }

    public void setProducto(Object producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
