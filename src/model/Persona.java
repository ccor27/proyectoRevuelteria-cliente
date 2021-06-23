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
public class Persona implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    	private String nombre;
	private int edad;
	private int cedula;
	
	public Persona(String nombre, int cedula, int edad) {
		
		this.nombre = nombre;
                this.cedula = cedula;
		this.edad = edad;
		
	}

    public Persona() {
    }
        

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + ", cedula=" + cedula + "]";
	}
}
