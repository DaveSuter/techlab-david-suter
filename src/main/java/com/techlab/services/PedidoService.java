package com.techlab.services;

import com.techlab.entidades.pedidos.LineaPedido;
import com.techlab.entidades.pedidos.Pedido;
import com.techlab.entidades.productos.Bebida;
import com.techlab.entidades.productos.Comida;
import com.techlab.entidades.productos.Producto;
import com.techlab.exceptions.PedidoCanceladoException;

import java.util.ArrayList;
import java.util.Scanner;

public class PedidoService {
    private final Scanner scanner;
    private final ArrayList<Object> listaProductos;
    private final ArrayList<Pedido> pedidos;
    private final MenuService menuService;


    public PedidoService(Scanner scanner, ArrayList<Object> listaProductos, ArrayList<Pedido> pedidos, MenuService menuService) {
        this.scanner = scanner;
        this.listaProductos = listaProductos;
        this.pedidos = pedidos;
        this.menuService = menuService;
    }

    public Pedido crearPedido(ArrayList<Object> listaProductos) {
        int idPedido = 0;
        int cantidadProductos;
        int idProducto;
        int cantidad;
        double precioTotal = 0;
        Producto producto;
        ArrayList<LineaPedido> lineasPedido = new ArrayList<>();
        boolean pedidoConfirmado;
        System.out.println("Cuántos productos va a agregar?: ");
        cantidadProductos = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i<cantidadProductos; i++){
            LineaPedido lineaPedido = new LineaPedido();
            System.out.println("Ingrese el ID del producto: ");
            idProducto = scanner.nextInt();
            scanner.nextLine();
            producto = validarId(idProducto, listaProductos);
            System.out.println("Ingrese la cantidad que desea: ");
            cantidad = scanner.nextInt();
            scanner.nextLine();
            cantidad = validarStock(cantidad, producto); //TODO: ver si hago exception
            if (listaProductos.get(producto.getId()).getClass() == Comida.class){
                Comida comida = (Comida) producto;
                lineaPedido.setProducto(comida);
            } else {
                Bebida bebida = (Bebida) producto;
                lineaPedido.setProducto(bebida);
            }
            lineaPedido.setCantidad(cantidad);
            lineaPedido.setId(producto.getId());
            lineaPedido.setSubTotal(cantidad*producto.getPrecio());
            precioTotal = precioTotal + lineaPedido.getSubTotal();
            lineasPedido.add(lineaPedido);
        }
        pedidoConfirmado = confirmarPedido(listaProductos, lineasPedido);
        if (!pedidoConfirmado){
            throw new PedidoCanceladoException("Pedido cancelado.");
        }
        return new Pedido(idPedido, lineasPedido, precioTotal);
    }

    private Producto validarId(int id, ArrayList<Object> listaProductos) {
        Producto producto = new Producto();
        boolean idValidado = false;
        while (!idValidado){
            try {
                producto = (Producto) listaProductos.get(id);
                idValidado = true;
            } catch (IndexOutOfBoundsException  e){
                System.out.println("Ingrese un ID válido: ");
                id = scanner.nextInt();
                scanner.nextLine();
            }
        }
        return producto;
    }

    private int validarStock(int cantidad, Producto producto) {
        while (cantidad > producto.getStock()){
            System.out.println("No hay stock suficiente. Ingrese un valor menor: ");
            cantidad = scanner.nextInt();
            scanner.nextLine();
        }
        return cantidad;
    }

    private boolean confirmarPedido(ArrayList<Object> listaProductos, ArrayList<LineaPedido> lineasPedido) {
        int opcion = 0;
        boolean opcionValida = false;
        while (!opcionValida){
            System.out.println("Desea confirmar el pedido? (1:Si , 2:No)");
            opcion = scanner.nextInt();
            scanner.nextLine();
            opcionValida = menuService.validarValorOpcion(opcion, 1, 2);
            if (!opcionValida){
                System.out.println("Ingresó un valor incorrecto. Por favor, vuelva a intentarlo.");
            }
        }
        if (opcion==1){
            disminuirStock(listaProductos, lineasPedido);
            return true;
        } else {
            return false;
        }
    }

    private void disminuirStock(ArrayList<Object> listaProductos, ArrayList<LineaPedido> lineasPedido) {
        for (int i=0; i<lineasPedido.size(); i++){
            Producto productoPedido = (Producto) lineasPedido.get(i).getProducto();
            for (int y=0; y<listaProductos.size(); y++){
                Producto productoLista = (Producto) listaProductos.get(y);
                if (productoPedido.getId() == productoLista.getId()){
                    if (listaProductos.get(y).getClass() == Comida.class){
                        Comida comida = (Comida) productoLista;
                        comida.setStock(comida.getStock()-lineasPedido.get(i).getCantidad());
                        listaProductos.set(y, comida);
                    } else {
                        Bebida bebida = (Bebida) productoLista;
                        bebida.setStock(bebida.getStock()-lineasPedido.get(i).getCantidad());
                        listaProductos.set(y, bebida);
                    }
                }
            }
        }
    }

    public void listarPedidos(ArrayList<Pedido> pedidos) {
        for(Pedido pedido: pedidos){
            System.out.println("--------------------------");
            System.out.println("Pedido Nro: " + pedido.getId());
            System.out.println("Precio total: " + pedido.getPrecioTotal());
            System.out.println("Productos del pedido:");
            for (LineaPedido lineaPedido: pedido.getLineasPedido()){
                Producto producto = (Producto) lineaPedido.getProducto();
                System.out.println("Producto: " + producto.getNombre() + ". Cantidad: " + lineaPedido.getCantidad() + ". Subtotal: " + lineaPedido.getSubTotal());
            }
            System.out.println("--------------------------");
        }
        System.out.println(" ");
    }

    public void setearIdPedido(Pedido pedido, ArrayList<Pedido> pedidos) {
        if (pedidos.isEmpty()){
            pedido.setId(0);
        } else {
            pedido.setId(pedidos.size());
        }
    }
}
