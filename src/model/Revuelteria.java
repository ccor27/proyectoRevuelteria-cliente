package model;

import controller.ModelFactoryController;
import java.io.IOException;
import java.io.Serializable;
import services.IRevuelteria;
//import com.sun.prism.j2d.J2DPipeline;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Revuelteria implements IRevuelteria, Serializable {

    /**
     * variables de la clase
     */
    private String nombre;
    private Administrador admin;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Venta> listaVentas;
    private ArrayList<Usuario> listaUsuarios;
    private static final long serialVersionUID = 1L;

    /**
     * constructor
     *
     * @param nombre
     * @param admin
     */
    public Revuelteria(String nombre, Administrador admin) {
        super();
        this.nombre = nombre;
        this.admin = admin;
        admin.setRevuelteria(this);
        listaClientes = new ArrayList<>();
        listaEmpleados = new ArrayList<>();
        listaProductos = new ArrayList<>();
        listaVentas = new ArrayList<>();
        listaUsuarios = new ArrayList<>();
    }

    public Revuelteria() {
    }

    /**
     * metodos accesores
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Administrador getAdmin() {
        return admin;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(ArrayList<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * metodos implementados de la interface
     * @return 
     */
    @Override
    public boolean crearEmpleados(String nombre, int cedula, int edad, double salario, int codigo) {

        if (verificarExistenciaEmpleado(cedula, codigo)) {
  
        	 //JOptionPane.showMessageDialog(null, "el empleado con la cedula: "+cedula +" ya existe");
        	return false;

        } else {

            Empleado empleado = new Empleado(nombre, cedula, edad, salario, codigo);
            listaEmpleados.add(empleado);

            try {
                persistence.Persistencia.guardarEmpleados(listaEmpleados);
                persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                //JOptionPane.showMessageDialog(null, "empleado creado con exito.");
            } catch (IOException ex) {
                Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

    }

    public boolean verificarExistenciaEmpleado(int cedula, int codigo) {

        boolean centinela = false;
      
        if(listaEmpleados==null){
        	return centinela;
        }else{
        	for (Empleado empleado : listaEmpleados) {
				if(empleado.getCedula()==cedula || empleado.getCodigo()==codigo){
				  centinela=true;
		          break;
				}
			}
        	return centinela;
        }
//
//        if (listaEmpleados == null) {
//
//        } else {
//
//            for (int i = 0; i < listaEmpleados.size() && centinela == false; i++) {
//
//                if (listaEmpleados.get(i).getCedula() == cedula) {
//                    empleado = listaEmpleados.get(i);
//                    centinela = true;
//                }
//                
//            }
//        }
//
//        return empleado;
    }

    public boolean eliminarEmpleado(String nombre) {

        Empleado empleadoEliminar = obtenerEmpleado(nombre);

        if (empleadoEliminar != null) {
            if (!(validacionVentaEmpleado(empleadoEliminar.getCedula()))) {
                listaEmpleados.remove(empleadoEliminar);
                try {
                	persistence.Persistencia.guardarEmpleados(listaEmpleados);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            } else {
            	return false;
                //JOptionPane.showMessageDialog(null, "empleado "+ empleadoEliminar.getNombre()+" esta  asociado a una venta, no se puede eliminar");
            }

        } else {

        	return false;
            //JOptionPane.showMessageDialog(null, "el empleado no existe");

        }
    }

    public boolean validacionVentaEmpleado(int cedula) {

        boolean verificador = false;

//        for (int i = 0; i < listaVentas.size(); i++) {
//            if (listaVentas.get(i).getEmpleado().getCedula() == cedula) {
//                verificador = true;
//                return verificador;
//            }
        if(listaVentas==null){
        	return verificador;
        }else{
            for (Venta venta : listaVentas) {
				if(venta.getEmpleado().getCedula()==cedula){
					verificador = true;
					break;
				}
			}
            return verificador;
        }

        }
      
    

    public Empleado obtenerEmpleado(String nombre) {

        Empleado emp = null;
//        boolean centinela = false;
//        for (int i = 0; i < listaEmpleados.size() && centinela == false; i++) {
//
//            if (listaEmpleados.get(i).getNombre().equalsIgnoreCase(nombre)) {
//                emp = listaEmpleados.get(i);
//                centinela = true;
//            }
//        }
        if(listaEmpleados==null){
        	return emp;
        }else{
            for (Empleado empleado : listaEmpleados) {
    			if(empleado.getNombre().equalsIgnoreCase(nombre)){
    				emp =empleado;
    				break;
    			}
    		}

            return emp;
        }
        
 
    }

    public void modificarEmpleado(String nombreEmpModif, String nombre, int cedula, int edad, double salario, int codigo) {

        boolean centinela = false;
        Empleado empleado;

        for (int i = 0; i < listaEmpleados.size() && centinela == false; i++) {

            empleado = listaEmpleados.get(i);

            if (empleado.getNombre().equalsIgnoreCase(nombreEmpModif)) {

                empleado.setNombre(nombre);
                empleado.setCedula(cedula);
                empleado.setEdad(edad);
                empleado.setSalario(salario);
                empleado.setCodigo(codigo);
                centinela = true;
                try {
                	persistence.Persistencia.guardarEmpleados(listaEmpleados);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);

                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
    /////7

    @Override
    public boolean crearCliente(String nombre, int cedula, int edad, String direccion, String email) {
        // TODO Auto-generated method stub
        
        if (verificarExistenciaCliente(cedula)) {
        	
        	return false;
            
        } else {

            Cliente cliente = new Cliente(nombre, cedula, edad, direccion, email);
            listaClientes.add(cliente);
            try {
            	persistence.Persistencia.guardarClientes(listaClientes);
            	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
            	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
            	
            	
            } catch (IOException ex) {
                Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        }

    }

    public boolean verificarExistenciaCliente(int cedula) {

        boolean centinela = false;
        //Cliente clienteVerificado = null;

        if (listaClientes == null) {
        	return centinela;
        } else {
//
//            for (int i = 0; i < listaClientes.size() && centinela == false; i++) {
//
//                if (listaClientes.get(i).getCedula() == cedula) {
//                    clienteVerificado = listaClientes.get(i);
//                    centinela = true;
//                }
//                
//            }
            
            for (Cliente cliente : listaClientes) {
				if(cliente.getCedula()==cedula){
					centinela = true;
					break;
				}
			}
            
            return centinela;
        }

        
    }

    public Cliente obtenerCliente(String nombre) {

        Cliente cliente = null;
        //boolean centinela = false;
        
        if(listaClientes==null){
        	return cliente;
        }else{
        	for (Cliente client : listaClientes) {
				if(client.getNombre().equalsIgnoreCase(nombre)){
					cliente = client;
					break;
				}
			}
        	return cliente;
        }
//        for (int i = 0; i < listaClientes.size() && centinela == false; i++) {
//
//            if (listaClientes.get(i).getNombre().equalsIgnoreCase(nombre)) {
//                cliente = listaClientes.get(i);
//                centinela = true;
//            }
//        }

        
    }

    public void modificarCliente(String nombreEmpModif, String nombre, int cedula, int edad, String direccion, String email) {

        boolean centinela = false;
        Cliente cliente;

        for (int i = 0; i < listaClientes.size() && centinela == false; i++) {

            cliente = listaClientes.get(i);

            if (cliente.getNombre().equalsIgnoreCase(nombreEmpModif)) {

                cliente.setNombre(nombre);
                cliente.setCedula(cedula);
                cliente.setEdad(edad);
                cliente.setDireccion(direccion);
                cliente.setEmail(email);
                centinela = true;
                try {
                	persistence.Persistencia.guardarClientes(listaClientes);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public boolean eliminarCliente(String nombre) {

        Cliente clienteEliminar = obtenerCliente(nombre);

        if (clienteEliminar != null) {
            if (!validacionVentaCliente(clienteEliminar.getCedula())) {
                listaClientes.remove(clienteEliminar);
                try {
                	persistence.Persistencia.guardarClientes(listaClientes);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                } 
                return true;
            } else {
                //JOptionPane.showMessageDialog(null, "cliente asociado a una venta");
            	return false;
            }
        } else {

            //JOptionPane.showMessageDialog(null, "cliente no existe");
        	return false;

        }
    }

    public boolean validacionVentaCliente(int cedula) {

        boolean verificador = false;
        if(listaVentas==null){
        	return verificador;
        }else{
        	for (Venta venta : listaVentas) {
				if(venta.getCliente().getCedula()==cedula){
					verificador=true;
					break;
				}
			}
        	return verificador;
        }
//
//        for (int i = 0; i < listaVentas.size(); i++) {
//            if (listaVentas.get(i).getCliente().getCedula() == cedula) {
//                verificador = true;
//                return verificador;
//            }
//        }

    }

    @Override
    public boolean crearVerdura(int cantidad, String nombre, double precio) {

        if (existenciaProdcuto(nombre)) {
             return false;
        } else {

            Verdura verdura = new Verdura(precio, nombre, cantidad);
            listaProductos.add(verdura);
            try {
            	persistence.Persistencia.guardarProductos(listaProductos);
            	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
            	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
            } catch (IOException ex) {
                Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }

    }

    public boolean existenciaProdcuto(String nombre) {

        boolean verificador = false;

        if(listaProductos==null){
        	return verificador;
        }else{
        	for (Producto producto : listaProductos) {
				if(producto.getNombre().equalsIgnoreCase(nombre)){
					verificador=true;
					break;
				}
			}
        	return verificador;
        }
        
//        for (int i = 0; i < listaProductos.size() && centinela == false; i++) {
//
//            Producto prod = listaProductos.get(i);
//
//            if (prod.getNombre().equalsIgnoreCase(nombre)) {
//                verificador = true;
//                centinela = true;
//            }
//        }
//
//        return verificador;

    }

    @Override
    public boolean crearFruta(int cantidad, String nombre, double precio, int tipoFruta) {

        if (existenciaProdcuto(nombre)) {
             return false;
        } else {
            Fruta fruta = new Fruta(precio, tipoFruta, nombre, cantidad);
            listaProductos.add(fruta);
            try {
            	persistence.Persistencia.guardarProductos(listaProductos);
            	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
            	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
            } catch (IOException ex) {
                Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
            }
          return true;
        }
    }

    public boolean eliminarProducto(String nombre) {
    	
    	Producto prod = obtenerProducto(nombre);
    	if(prod!=null){
    		
          if (!(validacionVentaProducto(nombre))) {
          listaProductos.remove(prod);
          try {
          	persistence.Persistencia.guardarProductos(listaProductos);
          	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
          	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
          } catch (IOException ex) {
              Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
          }
         return true;
      }else{
 //          JOptionPane.showMessageDialog(null, "Producto asociado a una venta")
    	  return false;
      }
    	}else{
    		return false;
    	}

//        boolean centinela = false;
//        for (int i = 0; i < listaProductos.size() && centinela == false; i++) {
//
//            Producto prod = listaProductos.get(i);
//            if (prod.getNombre().equalsIgnoreCase(nombre)) {
//                if (!(validacionVentaProducto(prod.getNombre()))) {
//                    listaProductos.remove(i);
//                    try {
//                    	persistence.Persistencia.guardarProductos(listaProductos);
//                    	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
//                    	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
//                    } catch (IOException ex) {
//                        Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    centinela = true;
//                }else{
//                    JOptionPane.showMessageDialog(null, "Producto asociado a una venta");
//                }
//            }
//        }
    }

    public boolean validacionVentaProducto(String nombre) {

        boolean verificador = false;

        if(listaVentas==null){
        	return verificador;
        }else{
        	for (Venta venta : listaVentas) {
				if(validacionVentaProducto2(nombre, venta)){
					verificador=true;
					break;
				}
			}
        	return verificador;
        }
//        for (int i = 0; i < listaVentas.size(); i++) {
//            if (listaVentas.get(i).getListaCompra().get(i).getNombre().equals(nombre)) {
//                verificador = true;
//                return verificador;
//            }
//        }
        
    }
    
    private boolean validacionVentaProducto2(String nombre, Venta venta){
    	
    	ArrayList<Producto> listProd = venta.getListaCompra();
    	boolean verificador = false;
    	if(listProd==null){
    		return verificador;
    	}else{
    		for (Producto producto : listProd) {
				if(producto.getNombre().equalsIgnoreCase(nombre)){
					verificador=true;
					break;
				}
			}
    		return verificador;
    	}
    }

    public Producto obtenerProducto(String nombre) {

        //boolean centinela = false;
        Producto producto = null;
        if(listaProductos==null){
        	return producto;
        }else{
        	for (Producto prod : listaProductos) {
				if(prod.getNombre().equalsIgnoreCase(nombre)){
					producto = prod;
					break;
				}
			}
        	return producto;
        }
//        for (int i = 0; i < listaProductos.size() && centinela == false; i++) {
//
//            prod = listaProductos.get(i);
//            if (prod.getNombre().equalsIgnoreCase(nombre)) {
//                prodModificar = listaProductos.get(i);
//                centinela = true;
//            }
//        }
//        return prodModificar;
    }

    public void modificarVerdura(String nombreModificar, int cantidad, String nombre, double precio) {

        Producto pord;
        boolean centinela = false;
        for (int i = 0; i < listaProductos.size() && centinela == false; i++) {

            pord = listaProductos.get(i);
            if (pord.getClass() == Verdura.class && pord.getNombre().equalsIgnoreCase(nombreModificar)) {

                Verdura verdura = (Verdura) pord;
                verdura.setCantidad(cantidad);
                verdura.setNombre(nombre);
                verdura.setPrecio(precio);
                centinela = true;
                try {
                	persistence.Persistencia.guardarProductos(listaProductos);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public void modificarFruta(String nombreModificar, int cantidad, String nombre, double precio, int tipoFruta) {

        Producto pord;
        boolean centinela = false;
        for (int i = 0; i < listaProductos.size() && centinela == false; i++) {

            pord = listaProductos.get(i);
            if (pord.getClass() == Fruta.class && pord.getNombre().equalsIgnoreCase(nombreModificar)) {

                Fruta fruta = (Fruta) pord;
                fruta.setCantidad(cantidad);
                fruta.setNombre(nombre);
                fruta.setPrecio(precio);
                fruta.setTipoFruta(nombre);
                centinela = true;
                try {
                	persistence.Persistencia.guardarProductos(listaProductos);
                	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
                	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
                } catch (IOException ex) {
                    Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    @Override
    public boolean crearVenta(Empleado empleado, Cliente cliente, ArrayList<Producto> listaCompras, double precio) {

        if (existenciaVenta(empleado, cliente, listaCompras, precio)) {

        	//JOptionPane.showMessageDialog(null, "esta venta ya esta registrada, por lo tanto no se puede hacer");
        	return false;
        } else {
            Venta venta = new Venta(cliente, empleado, listaCompras, precio);
            listaVentas.add(venta);
            try {

            	persistence.Persistencia.guardarVentas(listaVentas);
            	persistence.Persistencia.guardarRecursoRevuelteriaXML(this);
            	persistence.Persistencia.guardarRecursoRevuelteriaBinario(this);
            } catch (IOException ex) {
                Logger.getLogger(Revuelteria.class.getName()).log(Level.SEVERE, null, ex);
            }
          return true;
        }
    }

    public boolean existenciaVenta(Empleado empleado, Cliente cliente, ArrayList<Producto> listaCompras, double precio) {

    	boolean verificador = false;
    	if(listaVentas==null){
    	return verificador;	
    	}else{
    	    for (Venta venta : listaVentas) {
				if(venta.getCliente().equals(cliente) && venta.getEmpleado().equals(empleado) &&
				   venta.getListaCompra().equals(listaCompras) && venta.getPrecio()==precio){
					
					verificador = true;
				}
			}
    	    return verificador;
    	}
//        boolean verificador = true, centinela = false;
//
//        for (int i = 0; i < listaCompras.size() && centinela == false; i++) {
//
//            Producto venta = listaCompras.get(i);
//
//            if (venta != null) {
//                verificador = false;
//                centinela = true;
//            }
//        }
//
//        return verificador;

    }

}
