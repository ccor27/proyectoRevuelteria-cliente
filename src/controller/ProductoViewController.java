package controller;

import model.Revuelteria;

public class ProductoViewController {

	private ModelFactoryController modelFactoryController;
	private Revuelteria revuelteria;
	
	public ProductoViewController() {
		 modelFactoryController = ModelFactoryController.getInstance();
		 revuelteria = modelFactoryController.getRevuelteria();
	}
	
	public Revuelteria getRevuelteria(){
		return revuelteria;
	}

	public boolean crearVerdura(int cantidad, String nombre, double precio) {
		
		return modelFactoryController.crearVerdura(cantidad, nombre, precio);
	}

	public boolean crearFruta(int cantidad, String nombre, double precio, int tipoFruta) {
		
		return modelFactoryController.crearFruta(cantidad,nombre,precio,tipoFruta);
	}

	public void modificarVerdura(String nombreModif, int cantidad, String nombre, double precio) {
		
		modelFactoryController.modificarVerdura(nombreModif,cantidad,nombre,precio);
		
	}

	public void modificarFruta(String nombreModif, int cantidad, String nombre, double precio, int tipoFruta) {
	
		modelFactoryController.modificarFruta(nombreModif,cantidad,nombre,precio,tipoFruta);
		
	}

	public boolean eliminarProducto(String nombre) {
		
		return modelFactoryController.eliminarProducto(nombre);
	}
}
