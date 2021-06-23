 
package model;

import java.io.Serializable;

public class Fruta extends Producto implements Serializable{
 
 
    private String tipoFruta;
    private static final long serialVersionUID = 1L;

    public Fruta(double precio, int tipoFruta, String nombre, int cantidad) {
        super(nombre, cantidad, precio);
        isTipo(tipoFruta);
    }

    public Fruta() {
        super(null, 0, 0);
    }
    
    
    
    public void isTipo(int tipoFruta){
        
        if (tipoFruta == 1) {
            this.tipoFruta = "dulce";
        } else {
            this.tipoFruta = "acida";
        }
    }



    public String getTipoFruta() {
        return tipoFruta;
    }

    public void setTipoFruta(String tipoFruta) {
        this.tipoFruta = tipoFruta;
    }

    @Override
    public String toString() {
        return "Fruta{" + "nombre=" + getNombre() + ", cantidad=" + getCantidad() + ", precio=" + getPrecio()  + "tipoFruta=" + tipoFruta + '}';
    }
    
    
}
