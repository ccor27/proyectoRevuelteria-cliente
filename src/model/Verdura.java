
package model;

import java.io.Serializable;

public class Verdura extends Producto implements Serializable{
    
    
    //private double precio;
    private static final long serialVersionUID = 1L;

    public Verdura(double precio, String nombre, int cantidad) {
        super(nombre, cantidad,precio);
        //this.precio = precio;
    }

    public Verdura() {
    }
    

//    public double getPrecio() {
//        return precio;
//    }
//
//    public void setPrecio(double precio) {
//        this.precio = precio;
//    }

    @Override
    public String toString() {
        return "Verdura{" + "nombre=" + getNombre() + ", cantidad=" + getCantidad() + ", precio=" + getPrecio() + "precio=" + '}';
    }

    
}
