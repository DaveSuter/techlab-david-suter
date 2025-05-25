package com.techlab.services;

import com.techlab.entidades.pedidos.Pedido;
import com.techlab.entidades.productos.Bebida;
import com.techlab.entidades.productos.Comida;
import com.techlab.entidades.productos.Producto;
import com.techlab.exceptions.PedidoCanceladoException;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuService {
    private final Scanner scanner;
    private final ArrayList<Object> listaProductos;
    private final ArrayList<Pedido> pedidos;
    private final ProductoService productoService;
    private final PedidoService pedidoService;
    private boolean opcionValida;
    private int opcion=0;

    public MenuService(Scanner scanner, ArrayList<Object> listaProductos, ArrayList<Pedido> pedidos) {
        this.scanner = scanner;
        this.listaProductos = listaProductos;
        this.pedidos = pedidos;
        this.productoService = new ProductoService(this, listaProductos);
        this.pedidoService = new PedidoService(scanner, listaProductos, pedidos, this);
    }

    public void mostrarMenu(){
        while (opcion!=7){
            System.out.println("===================================");
            System.out.println("SISTEMA DE GESTIÓN - TECHLAB");
            System.out.println("===================================");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar/Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear un pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");
            System.out.println(" ");
            System.out.println("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            opcionValida = validarValorOpcion(opcion, 1, 7);
            if (opcionValida){
                realizarAccion(opcion);
            } else {
                System.out.println("Ingresó un valor incorrecto. Por favor, vuelva a intentarlo.");
            }
            System.out.println(" ");
        }
    }

    protected boolean validarValorOpcion(int opcion, int menor, int mayor) {
        boolean opcionValida = false;
        //TODO: validar numeros en test
        if (opcion>=menor && opcion<=mayor){
            opcionValida = true;
        }
        return opcionValida;
    }

    public void realizarAccion(int opcion){
        switch (opcion){
            case 1:
                agregarProducto();
                break;
            case 2:
                listarProductos();
                break;
            case 3:
                buscarActualizarProducto();
                break;
            case 4:
                eliminarProducto();
                break;
            case 5:
                crearPedido();
                break;
            case 6:
                listarPedidos();
                break;
            case 7: break;
        }
    }

    private void agregarProducto() {
        int opcionAgregar;
        System.out.println("Si desea agregar una comida INGRESE 1.");
        System.out.println("Si desea agregar una bebida INGRESE 2.");
        System.out.println(" ");
        opcionAgregar = scanner.nextInt();
        scanner.nextLine();
        if (opcionAgregar==1){
            mostrarMenuComida(listaProductos);
        } else if (opcionAgregar==2){
            mostrarMenuBebida(listaProductos);
        } else {
            System.out.println("Ingresó un valor incorrecto!!");
        }
        System.out.println(" ");
    }

    private void mostrarMenuComida(ArrayList<Object> listaProductos) {
        Producto productoComida = new Comida();
        Comida comida = (Comida) mostrarMenuProducto(productoComida);
        System.out.println("Ingrese el peso en gramos: ");
        comida.setPeso(scanner.nextDouble());
        scanner.nextLine();
        productoService.agregarProducto(comida, listaProductos);
        System.out.println("El producto se ingresó con éxito.");
    }

    private void mostrarMenuBebida(ArrayList<Object> listaProductos) {
        Producto productoBebida = new Bebida();
        Bebida bebida = (Bebida) mostrarMenuProducto(productoBebida); //así o que sea void?? lo pensaré
        System.out.println("Ingrese el volumen en mililitros: ");
        bebida.setVolumen(scanner.nextDouble());
        scanner.nextLine();
        productoService.agregarProducto(bebida, listaProductos);
        System.out.println("El producto se ingresó con éxito.");
    }

    private Producto mostrarMenuProducto(Producto producto) {
        System.out.println("Ingrese nombre: ");
        String nombreComida = scanner.nextLine();
        producto.setNombre(nombreComida);
        setearPrecioStock(producto);
        return producto;
    }

    private void setearPrecioStock(Producto producto){
        double precio;
        int stock;
        System.out.println("Ingrese precio: ");
        precio = validarValorPositivo(scanner.nextDouble());
        scanner.nextLine();
        producto.setPrecio(precio);
        System.out.println("Ingrese stock: ");
        stock = (int) validarValorPositivo(scanner.nextInt());
        scanner.nextLine();
        producto.setStock(stock);
    }

    private double validarValorPositivo(double valor) {
        while (valor < 0){
            System.out.println("Ingrese un valor que no sea negativo: ");
            valor = scanner.nextDouble();
            scanner.nextLine();
        }
        return valor;
    }

    private void listarProductos() {
        System.out.println("Lista de productos: ");
        if (listaProductos==null || listaProductos.isEmpty()){
            System.out.println("No hay ningún producto cargado");
        } else {
            productoService.listarProductos(listaProductos);
        }
        System.out.println(" ");
    }

    //Se tiene en cuenta que para ver el ID se hace previamente con el métod0 listarProductos()
    private void buscarActualizarProducto(){
        int id;
        System.out.println("Ingrese el id del producto que quiere modificar: ");
        id = scanner.nextInt();
        scanner.nextLine();
        try {
            productoService.buscarActualizarProducto(id, listaProductos);
        } catch (IndexOutOfBoundsException e){
            System.out.println("No existe ningún producto con el id ingresado.");
        } catch (NullPointerException e){ //No creo que se use
            System.out.println("No hay productos cargados.");
        }
        System.out.println(" ");
    }

    protected void menuActualizar(Object producto) {
        System.out.println("Actualizaremos el producto con los siguientes datos que ingrese.");
        setearPrecioStock((Producto) producto);
    }

    private void eliminarProducto(){
        if (!listaProductos.isEmpty()){
            int id;
            boolean eliminar;
            opcionValida = false;
            System.out.println("Ingrese el id del producto que quiere eliminar: ");
            id = scanner.nextInt();
            scanner.nextLine();
            Producto producto = (Producto) listaProductos.get(id);
            while (!opcionValida){
                System.out.println("Está seguro que desea eliminar el producto " + producto.getNombre() + " ?");
                System.out.println("Ingrese 1 si desea eliminar el producto. De lo contrario ingrese 2: ");
                opcion = scanner.nextInt();
                scanner.nextLine();
                opcionValida = validarValorOpcion(opcion, 1, 2);
                if (!opcionValida){
                    System.out.println("Ingresó un valor incorrecto. Por favor, vuelva a intentarlo.");
                }
            }
            eliminar = opcion==1;
            if (eliminar){
                try {
                    productoService.eliminarProducto(id, listaProductos);
                    System.out.println("El producto se eliminó con éxito.");
                } catch (IndexOutOfBoundsException e){
                    System.out.println("No existe ningún producto con el id ingresado.");
                }
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("No hay productos cargados.");
        }
        System.out.println(" ");
    }

    private void crearPedido() {
        listarProductos();
        if (!listaProductos.isEmpty()){
            try {
                Pedido pedido = pedidoService.crearPedido(listaProductos);
                pedidoService.setearIdPedido(pedido, pedidos);
                pedidos.add(pedido);
                System.out.println("Pedido creado con éxito.");
            } catch (PedidoCanceladoException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(" ");
    }

    private void listarPedidos() {
        System.out.println("Lista de pedidos.");
        if (pedidos == null || pedidos.isEmpty()){
            System.out.println("No hay ningún pedido cargado");
        } else {
            pedidoService.listarPedidos(pedidos);
        }
        System.out.println(" ");
    }
}