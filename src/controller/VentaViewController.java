package controller;

import java.util.ArrayList;

import model.Cliente;
import model.Empleado;
import model.Producto;
import model.Revuelteria;

public class VentaViewController {

	private ModelFactoryController modelFactoryController;
	private Revuelteria revuelteria;
	
	public VentaViewController() {
		 modelFactoryController = ModelFactoryController.getInstance();
		 revuelteria = modelFactoryController.getRevuelteria();
	}
	
	public Revuelteria getRevuelteria(){
		return revuelteria;
	}

	public ArrayList<String> obtenerNombresEmpleados() {
		
		return modelFactoryController.obtenerNombresEmpleados();
	}
	
	public ArrayList<String> obtenerNombresClientes() {
		
		return modelFactoryController.obtenerNombresClientes();
	}

	public Empleado obtenerEmpleado(String nombre) {
		
		return modelFactoryController.obtenerEmpleado(nombre);
		
	}

	public Cliente obtenerCliente(String nombre) {
		
		return modelFactoryController.obtenerCliente(nombre);
	}

	public Producto obtenerProducto(String nombre) {
		
		return modelFactoryController.obtenerProducto(nombre);
	}

	public boolean crearVenta(Empleado empleado, Cliente cliente,
			ArrayList<Producto> listaCompras, double precio) {
		
		return modelFactoryController.crearVenta(empleado,cliente,listaCompras,precio);
	}
}
