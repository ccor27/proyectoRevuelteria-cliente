package controller;

import model.Revuelteria;

public class EmpleadoViewController {

	private ModelFactoryController modelFactoryController;
	private Revuelteria revuelteria;
	
	public EmpleadoViewController() {
		 modelFactoryController = ModelFactoryController.getInstance();
		 revuelteria = modelFactoryController.getRevuelteria();
	}
	
	public Revuelteria getRevuelteria(){
		return revuelteria;
	}

	public boolean crearEmpleado(String nombre, int cedula, int edad, int salario, int codigo) {
		
		return modelFactoryController.crearEmpleado(nombre,cedula,edad,salario,codigo);
	}

	public void modificarEmpleado(String nombreEmpModif, String nombre, int cedula, int edad, double salario,
			int codigo) {
	
		modelFactoryController.modificarEmpleado(nombreEmpModif,nombre,cedula,edad,salario,codigo);
		
	}

	public boolean eliminarEmpleado(String nombre) {
		
		return modelFactoryController.eliminarEmpleado(nombre);
	}
}
