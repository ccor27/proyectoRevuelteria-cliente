/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author cristian
 * @author juanj
 */
public class Cliente extends Persona implements Serializable{

    private String direccion;
    private String email;
    private static final long serialVersionUID = 1L;
    
    public Cliente(String nombre, int cedula, int edad, String direccion, String email){
        super(nombre, cedula, edad);
        this.direccion=direccion;
        this.email=email;
    }

    public Cliente() {
        super(null, 0, 0);
    }
    
    

    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }

    
    
    
}
