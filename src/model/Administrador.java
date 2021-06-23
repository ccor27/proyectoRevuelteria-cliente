
package model;

import java.io.Serializable;


public class Administrador extends Persona implements Serializable{
    
    
	private double salario;
	private Revuelteria revuelteria;
        private static final long serialVersionUID = 1L;


	public Administrador(String nombre, int edad, int cedula, double salario) {
		super(nombre, edad, cedula);
		this.salario = salario;
		
	}

    public Administrador() {
        super(null, 0, 0);
    }
        
        

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Revuelteria getRevuelteria() {
		return revuelteria;
	}

	public void setRevuelteria(Revuelteria revuelteria) {
		this.revuelteria = revuelteria;
	}
	
}
