package com.techlab.services;

import com.techlab.entidades.productos.Bebida;
import com.techlab.entidades.productos.Comida;
import com.techlab.entidades.productos.Producto;

import java.util.ArrayList;

public class ProductoService {
    private final MenuService menuService;
    private final ArrayList<Object> listaProductos;

    public ProductoService(MenuService menuService, ArrayList<Object> listaProductos) {
        this.menuService = menuService;
        this.listaProductos = listaProductos;
    }

    public void agregarProducto(Comida comida, ArrayList<Object> listaProductos) {
        if (listaProductos==null || listaProductos.isEmpty()){
            comida.setId(0);
        } else {
            comida.setId(listaProductos.size());
        }
        listaProductos.add(comida);
    }

    public void agregarProducto(Bebida bebida, ArrayList<Object> listaProductos) {
        if (listaProductos==null || listaProductos.isEmpty()){
            bebida.setId(0);
        } else {
            bebida.setId(listaProductos.size());
        }
        listaProductos.add(bebida);
    }

    public void listarProductos(ArrayList<Object> listaProductos) {
        for (Object object : listaProductos) {
            Producto producto = (Producto) object;
            System.out.print("Id: " + producto.getId() + " - Nombre: " + producto.getNombre() + " - Precio: " +
                    producto.getPrecio() + " - Stock: " + producto.getStock());
            if (object.getClass()== Comida.class){
                Comida comida = (Comida) object;
                System.out.println(" - Peso(gr): " + comida.getPeso());
            } else {
                Bebida bebida = (Bebida) object;
                System.out.println(" - Volumen(ml): " + bebida.getVolumen());
            }
        }
    }

    public void buscarActualizarProducto(int id, ArrayList<Object> listaProductos) {
        if (listaProductos.isEmpty()) {
            System.out.println("No hay productos cargados.");
        } else {
            Object object = listaProductos.get(id);
            Producto producto = (Producto) object;
            System.out.println("Producto:");
            System.out.println("Id: " + producto.getId() + " - Nombre: " + producto.getNombre() + " - Precio: " +
                    producto.getPrecio() + " - Stock: " + producto.getStock());
            menuService.menuActualizar(object);
            //Valido la clase
            if (listaProductos.get(id).getClass() == Comida.class){
                Comida comida = (Comida) object;
                listaProductos.set(id, comida);
            } else {
                Bebida bebida = (Bebida) object;
                listaProductos.set(id, bebida);
            }
            System.out.println("El producto se modificó con éxito.");
        }
    }

    public void eliminarProducto(int id, ArrayList<Object>  listaProductos) {
        listaProductos.remove(id);
        if (!listaProductos.isEmpty()){
            resetearIds(listaProductos);
        }
    }

    private void resetearIds(ArrayList<Object> listaProductos) {
        for(int i=0; i<listaProductos.size(); i++){
            if (listaProductos.get(i).getClass() == Comida.class){
                Comida comida = (Comida) listaProductos.get(i);
                comida.setId(i);
                listaProductos.set(i, comida);
            } else {
                Bebida bebida = (Bebida) listaProductos.get(i);
                bebida.setId(i);
                listaProductos.set(i, bebida);
            }
        }
    }
}