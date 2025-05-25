package com.techlab.entidades.productos;

public class Comida extends Producto {
    private double peso;

    public Comida() {
    }

    public Comida(double peso) {
        super();
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
