package com.techlab.entidades.productos;

public class Bebida extends Producto {
    private double volumen;

    public Bebida() {
    }

    public Bebida(double volumen) {
        super();
        this.volumen = volumen;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }
}
