package controller;

import java.util.ArrayList;

import model.Administrador;
import model.Cliente;
import model.Empleado;
import model.Fruta;
import model.Producto;
import model.Revuelteria;
import model.Usuario;
import model.Verdura;

public class ModelFactoryController {

    private Administrador admin;
    private Revuelteria revuelteria;
    
	//------------------------------  Singleton ------------------------------------------------
	// Clase estatica oculta. Tan solo se instanciara el singleton una vez
	private static class SingletonHolder { 
		// El constructor de Singleton puede ser llamado desde aquí al ser protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	}

	// Método para obtener la instancia de nuestra clase
	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}
	
	public ModelFactoryController() {
		iniciarDatos();
	}
	
	private void iniciarDatos(){
		
		    admin = new Administrador("javier", 35, 1234, 500.000);
	        revuelteria = new Revuelteria("Revuelteria", admin);
	        
	        Usuario usuario = new Usuario("juan", "123");
	        Usuario usuario1 = new Usuario("cristian", "123");
	        
	        Usuario usuario2 = new Usuario("eduardo", "123");
	        Usuario usuario3 = new Usuario("luis", "123");
	        Usuario usuario4 = new Usuario("aurelio", "123");
	        
	        admin.getRevuelteria().getListaUsuarios().add(usuario);
	        admin.getRevuelteria().getListaUsuarios().add(usuario1);
	        admin.getRevuelteria().getListaUsuarios().add(usuario2);
	        admin.getRevuelteria().getListaUsuarios().add(usuario3);
	        admin.getRevuelteria().getListaUsuarios().add(usuario4);

	        //cada que se crea un empleado, cliente y product
	        //este se agrega auntomaticamente a la lista correspondiente
	        admin.getRevuelteria().crearEmpleados("eduardo", 1234, 15, 200.000, 64);
	        admin.getRevuelteria().crearEmpleados("luis", 15476, 20, 200.000, 63);
	        admin.getRevuelteria().crearEmpleados("aurelio", 4846, 20, 200.000, 62);

	        admin.getRevuelteria().crearCliente("cristian", 123456, 18, "armenia", "cristian@gmail.com");
	        admin.getRevuelteria().crearCliente("juan jose", 456, 19, "armenia", "juan@gmail.com");
	        admin.getRevuelteria().crearCliente("aurelio", 785, 17, "armenia", "aurelio@gmail.com");

	        admin.getRevuelteria().crearFruta(30, "manzana", 1000, 1);
	        admin.getRevuelteria().crearFruta(40, "naranja", 500, 2);
	        admin.getRevuelteria().crearVerdura(20, "papa", 2000);
	        admin.getRevuelteria().crearVerdura(30, "cilantro", 2000);

	        //creamos una venta
	        Empleado empleado = admin.getRevuelteria().obtenerEmpleado("eduardo");
	        Cliente cliente = admin.getRevuelteria().obtenerCliente("aurelio");
	        ArrayList<Producto> listaCompra = new ArrayList<>();
	        Verdura verdura1 = new Verdura(2000, "cebolla", 3);
	        listaCompra.add(verdura1);
	        Verdura verdura2 = new Verdura(2000, "remolacha", 3);
	        listaCompra.add(verdura2);
	        Fruta fruat1 = new Fruta(1000, 1, "sandia", 4);
	        listaCompra.add(fruat1);
	        Fruta fruat2 = new Fruta(1000, 1, "guanabana", 3);
	        listaCompra.add(fruat2);
	        double precioVenta = 0;
	        for (Producto producto : listaCompra) {
	            precioVenta = (producto.getCantidad() * producto.getPrecio());
	        }

	        admin.getRevuelteria().crearVenta(empleado, cliente, listaCompra, precioVenta);

	        Empleado empleado2 = admin.getRevuelteria().obtenerEmpleado("eduardo");
	        Cliente cliente2 = admin.getRevuelteria().obtenerCliente("juan jose");
	        ArrayList<Producto> listaCompra2 = new ArrayList<>();
	        Verdura verdura12 = new Verdura(3000, "Cebolla", 3);
	        listaCompra2.add(verdura12);
	        Verdura verdura3 = new Verdura(3000, "Remolacha", 3);
	        listaCompra2.add(verdura3);
	        Fruta fruat12 = new Fruta(2000, 1, "Sandia", 4);
	        listaCompra2.add(fruat12);
	        Fruta fruat3 = new Fruta(2000, 1, "Guanabana", 3);
	        listaCompra2.add(fruat3);
	        double precioVenta2 = 0;
	        for (Producto producto : listaCompra2) {
	            precioVenta = (producto.getCantidad() * producto.getPrecio());
	        }

	        admin.getRevuelteria().crearVenta(empleado2, cliente2, listaCompra2, precioVenta2);
	}

	public Revuelteria getRevuelteria(){
	
		return revuelteria;
	}

	public boolean crearCliente(String nombre, int cedula, int edad, String direccion, String email) {
      return revuelteria.crearCliente(nombre, cedula, edad, direccion, email);	
	}

	public void modificarCliente(String nombreClientModif, String nombre, int cedula, int edad, String direccion,
			String email) {
		revuelteria.modificarCliente(nombreClientModif, nombre, cedula, edad, direccion, email);
		
	}

	public boolean eliminarCliente(String nombre) {
		
		return revuelteria.eliminarCliente(nombre);
	}

	public boolean crearEmpleado(String nombre, int cedula, int edad, int salario, int codigo) {
		
		return revuelteria.crearEmpleados(nombre, cedula, edad, salario, codigo);
	}

	public void modificarEmpleado(String nombreEmpModif, String nombre, int cedula, int edad, double salario, int codigo) {
		
		revuelteria.modificarEmpleado(nombreEmpModif, nombre, cedula, edad, salario, codigo);
		
	}

	public boolean eliminarEmpleado(String nombre) {

		return revuelteria.eliminarEmpleado(nombre);
	}

	public boolean crearVerdura(int cantidad, String nombre, double precio) {
		
		return revuelteria.crearVerdura(cantidad, nombre, precio);
	}

	public boolean crearFruta(int cantidad, String nombre, double precio, int tipoFruta) {
		
		return revuelteria.crearFruta(cantidad, nombre, precio, tipoFruta);
	}

	public void modificarVerdura(String nombreModif, int cantidad, String nombre, double precio) {
		
		revuelteria.modificarVerdura(nombreModif, cantidad, nombre, precio);
		
	}

	public void modificarFruta(String nombreModif, int cantidad, String nombre, double precio, int tipoFruta) {

		revuelteria.modificarFruta(nombreModif, cantidad, nombre, precio, tipoFruta);
		
	}

	public boolean eliminarProducto(String nombre) {
		
		return revuelteria.eliminarProducto(nombre);
	}

	public ArrayList<String> obtenerNombresEmpleados() {
		
		ArrayList<Empleado> listEmp = revuelteria.getListaEmpleados();
		ArrayList<String> listNomEmp = new ArrayList();
		
		for (Empleado emp : listEmp) {
			listNomEmp.add(emp.getNombre());
		}
	    
		return listNomEmp;
	}
	
	public ArrayList<String> obtenerNombresClientes() {
		
		ArrayList<Cliente> listClient = revuelteria.getListaClientes();
		ArrayList<String> listNomClient = new ArrayList();
		
		for (Cliente client : listClient) {
			listNomClient.add(client.getNombre());
		}
	    
		return listNomClient;
	}

	public Empleado obtenerEmpleado(String nombre) {
		
		return revuelteria.obtenerEmpleado(nombre);
	}

	public Cliente obtenerCliente(String nombre) {
		
		return revuelteria.obtenerCliente(nombre);
	}

	public Producto obtenerProducto(String nombre) {
		
		return revuelteria.obtenerProducto(nombre);
	}

	public boolean crearVenta(Empleado empleado, Cliente cliente, ArrayList<Producto> listaCompras, double precio) {
		
		return revuelteria.crearVenta(empleado, cliente, listaCompras, precio);
	}
}


