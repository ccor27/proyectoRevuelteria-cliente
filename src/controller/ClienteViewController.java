package controller;

import model.Revuelteria;

public class ClienteViewController {

	private ModelFactoryController modelFactoryController;
	private Revuelteria revuelteria;
	
	public ClienteViewController() {
		 modelFactoryController = ModelFactoryController.getInstance();
		 revuelteria = modelFactoryController.getRevuelteria();
	}
	
	public Revuelteria getRevuelteria(){
		return revuelteria;
	}

	public boolean crearCliente(String nombre, int cedula, int edad, String direccion, String email) {
		return modelFactoryController.crearCliente(nombre,cedula,edad,direccion,email);
	}

	public void modificarCliente(String nombreClientModif, String nombre, int cedula, int edad, String direccion, String email) {
      modelFactoryController.modificarCliente(nombreClientModif,nombre,cedula,edad,direccion,email);
	}

	public boolean eliminarCliente(String nombre) {
		
		return modelFactoryController.eliminarCliente(nombre);
	}
}
