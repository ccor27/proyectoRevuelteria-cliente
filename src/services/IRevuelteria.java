/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import model.Cliente;
import model.Empleado;
import model.Producto;

/**
 *
 * @author cristian
 * @author juanj
 */
public interface IRevuelteria {
    
    
	public boolean crearEmpleados(String nombre, int cedula, int edad, double salario,int codigo);
	public boolean crearCliente(String nombe, int cedula, int edad, String direccion, String email);
	public boolean crearVerdura(int cantidad, String nombre, double precio);
	public boolean crearFruta(int cantidad, String nombre, double precio, int tipoFruta);
	public boolean crearVenta(Empleado empleado, Cliente cliente, ArrayList<Producto> listaCompras, double precio);
	
	
	

}
