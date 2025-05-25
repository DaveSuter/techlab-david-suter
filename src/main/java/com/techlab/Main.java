package com.techlab;

import com.techlab.entidades.pedidos.Pedido;
import com.techlab.services.MenuService;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Object> listaProductos = new ArrayList<>();
        ArrayList<Pedido> pedidos = new ArrayList<>();
        MenuService menu = new MenuService(scanner, listaProductos, pedidos);
        menu.mostrarMenu();

    }
}