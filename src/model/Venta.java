/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author cristian
 * @author juanj
 */
public class Venta implements Serializable{
    
    private static final long serialVersionUID = 1L;
    Empleado empleado;
    Cliente cliente;
    ArrayList<Producto> listaCompra;
    double precio;

    public Venta(Cliente cliente, Empleado empleado, ArrayList<Producto> listaCompra, double precio) {
        
        this.cliente = cliente;
        this.empleado = empleado;
        this.listaCompra = listaCompra;
        this.precio = precio;
                
    }
    
    public Venta(){
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Producto> getListaCompra() {
        return listaCompra;
    }

    public void setListaCompra(ArrayList<Producto> listaCompra) {
        this.listaCompra = listaCompra;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Venta{" + "empleado=" + empleado + ", cliente=" + cliente + ", listaCompra=" + listaCompra + ", precio=" + precio + '}';
    }

 

    
    
    
}
