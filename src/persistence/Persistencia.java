/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

//import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.BufferedWriter;
import java.io.FileWriter;
import model.Cliente;
import model.Empleado;
import model.Producto;
import model.Revuelteria;
import java.io.IOException;
import java.util.ArrayList;
import model.Fruta;
import model.Usuario;
import model.Venta;
import model.Verdura;
//import persistencia.ArchivoUtil;
import persistence.ArchivoUtil;

import java.io.File;

/**
 *
 * @author cristian
 * @author juanj
 */
public class Persistencia {

    public static final String RUTA_ARCHIVO_CLIENTES = "src/resources/clientes.txt";
    public static final String RUTA_ARCHIVO_EMPLEADOS = "src/resources/empleados.txt";
    public static final String RUTA_ARCHIVO_FRUTAS = "src/resources/frutas.txt";
    public static final String RUTA_ARCHIVO_VERDURAS = "src/resources/verduras.txt";
    public static final String RUTA_ARCHIVO_VENTAS = "src/resources/ventas.txt";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/resources/usuarios.txt";
    public static final String RUTA_ARCHIVO_MODELO_REVUELTERIA_XML = "src/resources/model.xml";
    public static final String RUTA_ARCHIVO_MODELO_REVUELTERIA_BINARIO = "src/resources/model.dat";
    public static final String RUTA_ARCHIVO_LOG = "src/resources/revuelteriaLog.txt";
//    public static final String RUTA_ARCHIVO_CLIENTES = "/home/cristian/td/persistencia/archivos/clientes.txt";
//    public static final String RUTA_ARCHIVO_EMPLEADOS = "/home/cristian/td/persistencia/archivos/empleados.txt";
//    public static final String RUTA_ARCHIVO_FRUTAS = "/home/cristian/td/persistencia/archivos/frutas.txt";
//    public static final String RUTA_ARCHIVO_VERDURAS = "/home/cristian/td/persistencia/archivos/verduras.txt";
//    public static final String RUTA_ARCHIVO_VENTAS = "/home/cristian/td/persistencia/archivos/ventas.txt";
//    public static final String RUTA_ARCHIVO_USUARIOS = "/home/cristian/td/persistencia/archivos/usuarios.txt";
//    public static final String RUTA_ARCHIVO_MODELO_REVUELTERIA_XML = "/home/cristian/td/persistencia/model.xml";
//    public static final String RUTA_ARCHIVO_MODELO_REVUELTERIA_BINARIO = "/home/cristian/td/persistencia/model.dat";
//    public static final String RUTA_ARCHIVO_LOG = "/home/cristian/td/persistencia/log/revueleria.log";

    /**
     * metodo para cargar los datos que tiene la revuelteria desde los archivos
     * txt
     *
     * @param revuelteria
     * @throws IOException
     */
    public static void cargarArchivos(Revuelteria revuelteria) throws IOException {

        //cargar usuario
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();
        if (usuariosCargados.size() > 0) {
            revuelteria.getListaUsuarios().addAll(usuariosCargados);
        }

        //cargar clientes
        ArrayList<Cliente> clientesCargados = cargarClientes();
        if (clientesCargados.size() > 0) {
            revuelteria.getListaClientes().addAll(clientesCargados);
        }

        //cargar empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();
        if (empleadosCargados.size() > 0) {
            revuelteria.getListaEmpleados().addAll(empleadosCargados);
        }

        //cargar Productos
        ArrayList<Producto> productosCargados = cargarProductos();
        if (productosCargados.size() > 0) {
            revuelteria.getListaProductos().addAll(productosCargados);
        }

        //cargar historia de productosCompra
        ArrayList<Venta> ventas = cargarVentas();
        if (ventas.size() > 0) {
            revuelteria.getListaVentas().addAll(ventas);
        }

    }

    /**
     * metodo para guardar los clientes en un txt
     *
     * @param clientes
     * @throws IOException
     */
    public static void guardarClientes(ArrayList<Cliente> clientes) throws IOException {

        String contenido = "";
        for (Cliente cliente : clientes) {
            contenido += "<" + cliente.getNombre() + ">" + "@@" + "<" + cliente.getCedula() + ">" + "@@"
                    + "<" + cliente.getEdad() + ">" + "@@" + "<" + cliente.getDireccion() + ">" + "@@" + "<" + cliente.getEmail() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_CLIENTES, contenido, Boolean.FALSE);
    }

    /**
     * metodo para guardar los empleados en un txt
     *
     * @param empleados
     * @throws IOException
     */
    public static void guardarEmpleados(ArrayList<Empleado> empleados) throws IOException {

        String contenido = "";
        for (Empleado empleado : empleados) {
            contenido += "<" + empleado.getNombre() + ">" + "@@" + "<" + empleado.getCedula() + ">" + "@@" + "<" + empleado.getEdad() + ">" + "@@"
                    + "<" + empleado.getSalario() + ">" + "@@" + "<" + empleado.getCodigo() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, Boolean.FALSE);
    }

    /**
     * metodo para guardar los productos en un archivo txr
     *
     * @param productos
     * @throws IOException
     */
    public static void guardarProductos(ArrayList<Producto> productos) throws IOException {

        ArrayList<Fruta> frutas = new ArrayList<>();
        ArrayList<Verdura> verduras = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getClass() == Fruta.class) {
                Fruta fruta = (Fruta) productos.get(i);
                frutas.add(fruta);
            } else {
                Verdura verdura = (Verdura) productos.get(i);
                verduras.add(verdura);
            }
        }

        guardarFrutas(frutas);
        guardarVerduras(verduras);

    }

    /**
     * metodo para guardar las frutas
     *
     * @param frutas
     * @throws IOException
     */
    private static void guardarFrutas(ArrayList<Fruta> frutas) throws IOException {
        String contenido = "";
        for (Fruta fruta : frutas) {
            contenido += "<" + fruta.getPrecio() + ">" + "@@" + "<" + fruta.getTipoFruta() + ">" + "@@" + "<" + fruta.getNombre() + ">" + "@@"
                    + "<" + fruta.getCantidad() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_FRUTAS, contenido, Boolean.FALSE);
    }

    /**
     * metodo para guardar las verduras
     *
     * @param verduras
     * @throws IOException
     */
    private static void guardarVerduras(ArrayList<Verdura> verduras) throws IOException {

        String contenido = "";
        for (Verdura verdura : verduras) {
            contenido += "<" + verdura.getPrecio() + ">" + "@@" + "<" + verdura.getNombre() + ">" + "@@" + "<" + verdura.getCantidad() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_VERDURAS, contenido, Boolean.FALSE);
    }

    /**
     * metodo para guardar la lista de productosCompra en un txt
     *
     * @param ventas
     * @throws IOException
     */
    public static void guardarVentas(ArrayList<Venta> ventas) throws IOException {

        ArrayList<Producto> productosString = new ArrayList<>();
        ArrayList<String> ventasString = new ArrayList<>();
        String contenido = "";
        String listaCompra = "";

        int i = 0;

        //pasar la lista de productosCompra a un strign
        for (int j = 0; j < ventas.size(); j++) {

            productosString = ventas.get(j).getListaCompra();
            //System.out.println(productosString.size());
            for (int k = 0; k < productosString.size(); k++) {
                Producto producto = productosString.get(k);

                if (producto.getClass().equals(Fruta.class)) {
                    Fruta fruta = (Fruta) producto;
                    // System.out.println(fruta);
                    listaCompra += "<" + fruta.getPrecio() + "," + fruta.getTipoFruta() + "," + fruta.getNombre() + "," + fruta.getCantidad() + ">@@";
                } else {

                    //Verdura verdura = (Verdura) producto;
                    listaCompra += "<" + producto.getPrecio() + "," + producto.getNombre() + "," + producto.getCantidad() + ">@@";
                }

            }

            ventasString.add(listaCompra);
        }

        for (Venta venta : ventas) {
            contenido += "<" + venta.getEmpleado().getNombre() + "," + venta.getEmpleado().getCedula() + "," + venta.getEmpleado().getEdad() + ","
                    + venta.getEmpleado().getSalario() + "," + venta.getEmpleado().getCodigo() + ">@@"
                    + "<" + venta.getCliente().getNombre() + "," + venta.getCliente().getCedula() + "," + venta.getCliente().getEdad() + ","
                    + "," + venta.getCliente().getDireccion() + "," + venta.getCliente().getEmail() + ">" + "@@"
                    + ventasString.get(i) + ">" + "\n";
        }
        
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_VENTAS, contenido, Boolean.FALSE);
    }

    /**
     * metodo para cargar los clientes que tiene la revuelteria
     *
     * @return clientes
     * @throws IOException
     */
    private static ArrayList<Cliente> cargarClientes() throws IOException {

        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_CLIENTES);
        String linea;

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            String nombre = linea.split("@@")[0];
            nombre = nombre.replace("<", "");
            nombre = nombre.replace(">", "");

            String cedulaS = linea.split("@@")[1];
            cedulaS = cedulaS.replace("<", "");
            cedulaS = cedulaS.replace(">", "");
            int cedula = Integer.parseInt(cedulaS.trim());

            String edadS = linea.split("@@")[2];
            edadS = edadS.replace("<", "");
            edadS = edadS.replace(">", "");
            int edad = Integer.parseInt(edadS.trim());

            String direccion = linea.split("@@")[3];
            direccion.replace("<", "");
            direccion.replace(">", "");

            String email = linea.split("@@")[4];
            email = email.replace("<", "");
            email = email.replace(">", "");

            Cliente cliente = new Cliente(nombre, cedula, edad, direccion, email);
            clientes.add(cliente);
        }
        return clientes;
    }

    public static void guardarUsuarios(ArrayList<Usuario> usuarios) throws IOException {

        String contenido = "";
        for (Usuario usuario : usuarios) {
            contenido += "<" + usuario.getUser() + ">" + "@@" + "<" + usuario.getPassword() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, Boolean.FALSE);
    }

    public static ArrayList<Usuario> cargarUsuarios() throws IOException {

        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea;

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            String user = linea.split("@@")[0];
            user = user.replace("<", "");
            user = user.replace(">", "");

            String password = linea.split("@@")[1];
            password = password.replace("<", "");
            password = password.replace(">", "");

            Usuario usuario = new Usuario(user, password);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    /**
     * metodo para cargar los empleados que tiene la revuelteria
     *
     * @return
     * @throws IOException
     */
    private static ArrayList<Empleado> cargarEmpleados() throws IOException {

        ArrayList<Empleado> empleados = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
        String linea;

        for (int i = 0; i < contenido.size(); i++) {

            linea = contenido.get(i);

            String nombre = linea.split("@@")[0];
            nombre = nombre.replace("<", "");
            nombre = nombre.replace(">", "");

            String cedulaS = linea.split("@@")[1];
            cedulaS = cedulaS.replace("<", "");
            cedulaS = cedulaS.replace(">", "");
            int cedula = Integer.parseInt(cedulaS.trim());

            String edadS = linea.split("@@")[2];
            edadS = edadS.replace("<", "");
            edadS = edadS.replace(">", "");
            int edad = Integer.parseInt(edadS.trim());

            String salarioS = linea.split("@@")[3];
            salarioS = salarioS.replace("<", "");
            salarioS = salarioS.replace(">", "");
            double salario = Double.parseDouble(salarioS.trim());

            String codigoS = linea.split("@@")[4];
            codigoS = codigoS.replace("<", "");
            codigoS = codigoS.replace(">", "");
            int codigo = Integer.parseInt(codigoS.trim());

            Empleado empleado = new Empleado(nombre, cedula, edad, salario, codigo);
            empleados.add(empleado);

        }

        return empleados;
    }

    /**
     * metodo para cargar los productos que tiene la revuelteria
     *
     * @return
     * @throws IOException
     */
    private static ArrayList<Producto> cargarProductos() throws IOException {

        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Fruta> frutas = cargarFrutas();
        ArrayList<Verdura> verduras = cargarVerduras();

        for (int i = 0; i < frutas.size(); i++) {
            productos.add(frutas.get(i));
        }
        for (int i = 0; i < verduras.size(); i++) {
            productos.add(verduras.get(i));
        }

        return productos;
    }

    /**
     * metodo para cargar las frutas
     *
     * @return
     * @throws IOException
     */
    private static ArrayList<Fruta> cargarFrutas() throws IOException {

        ArrayList<Fruta> frutas = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_FRUTAS);
        String linea;
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            String precioS = linea.split("@@")[0];
            precioS = precioS.replace("<", "");
            precioS = precioS.replace(">", "");
            double precio = Double.parseDouble(precioS.trim());

            String tipoString = linea.split("@@")[1].trim();
            tipoString = tipoString.replace("<", "");
            tipoString = tipoString.replace(">", "");

            int tipo;
            if (tipoString.equalsIgnoreCase("dulce")) {
                tipo = 1;
            } else {
                tipo = 2;
            }

            String nombre = linea.split("@@")[2];
            nombre = nombre.replace("<", "");
            nombre = nombre.replace(">", "");

            String cantidadS = linea.split("@@")[3];
            cantidadS = cantidadS.replace("<", "");
            cantidadS = cantidadS.replace(">", "");

            int cantidad = Integer.parseInt(cantidadS.trim());

            Fruta fruta = new Fruta(precio, tipo, nombre, cantidad);
            frutas.add(fruta);
        }

        return frutas;
    }

    /**
     * metodo para cargar las verduras
     *
     * @return
     * @throws IOException
     */
    private static ArrayList<Verdura> cargarVerduras() throws IOException {

        ArrayList<Verdura> verduras = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_VERDURAS);
        String linea;
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            String precioS = linea.split("@@")[0];
            precioS = precioS.replace("<", "");
            precioS = precioS.replace(">", "");
            double precio = Double.parseDouble(precioS.trim());

            String nombre = linea.split("@@")[1];
            nombre = nombre.replace("<", "");
            nombre = nombre.replace(">", "");

            String cantidadS = linea.split("@@")[2];
            cantidadS = cantidadS.replace("<", "");
            cantidadS = cantidadS.replace(">", "");

            int cantidad = Integer.parseInt(cantidadS.trim());

            Verdura verdura = new Verdura(precio, nombre, cantidad);
            verduras.add(verdura);
        }
        return verduras;
    }

    public static ArrayList<Venta> cargarVentas() throws IOException {

        ArrayList<Producto> productosCompra = new ArrayList<>();
        ArrayList<Venta> listaVentas = new ArrayList<>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_VENTAS);
        String linea;

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            int espacioTotal = linea.length();

            int espaciosEmpleado = linea.split("@@")[0].length();
            int espaciosCliente = linea.split("@@")[1].length();
            int espacioClienEmpl = espaciosEmpleado + espaciosCliente;
            //System.out.println(espacioClienEmpl);

            //empleado
            String empleadoS = linea.split("@@")[0];
            empleadoS = empleadoS.replace("<", "");
            empleadoS = empleadoS.replace(">", "");

            String nombreEmpleado = empleadoS.split(",")[0];
            int cedulaEmpleado = Integer.parseInt(empleadoS.split(",")[1].trim());
            int edadEmpleado = Integer.parseInt(empleadoS.split(",")[2].trim());
            double salarioEmpleado = Double.valueOf(empleadoS.split(",")[3].trim());
            int codigoEmpleado = Integer.parseInt(empleadoS.split(",")[4].trim());

            Empleado empleado = new Empleado(nombreEmpleado, cedulaEmpleado, edadEmpleado, salarioEmpleado, codigoEmpleado);

            //cliente
            String clienteString = linea.split("@@")[1];
            clienteString = clienteString.replace("<", "");
            clienteString = clienteString.replace(">", "");

            String nombreCliente = clienteString.split(",")[0];
            int cedulaCliente = Integer.parseInt(clienteString.split(",")[1].trim());
            int edadCliente = Integer.parseInt(clienteString.split(",")[2].trim());
            String direccionCliente = clienteString.split(",")[3];
            String emailCliente = clienteString.split(",")[4];

            Cliente cliente = new Cliente(nombreCliente, cedulaCliente, edadCliente, direccionCliente, emailCliente);

            //productos
            String productosString = linea.substring(espacioClienEmpl + 2, espacioTotal);
            productosCompra = cargarListaProdcutostxt(productosString);
            double precio = precioCompra(productosCompra);

            //creamos la venta
            Venta venta = new Venta(cliente, empleado, productosCompra, precio);
            listaVentas.add(venta);

        }
        return listaVentas;
    }

    public static ArrayList<Producto> cargarListaProdcutostxt(String linea) {

        ArrayList<Producto> lista = new ArrayList<>();

        linea = linea.substring(2, linea.length());
        int numeroSeparaciones = contarPartes(linea);
        linea = linea.replace("<", "");
        linea = linea.replace(">", "");

        for (int i = 0; i < numeroSeparaciones; i++) {

            String parte = linea.split("@@")[i];

            if (contarComas(parte) == 2) {

                // es una verdura
                double precio = Double.valueOf(parte.split(",")[0].trim());
                String nombre = parte.split(",")[1];
                int cantidad = Integer.parseInt(parte.split(",")[2].trim());

                Verdura verdura = new Verdura(precio, nombre, cantidad);
                lista.add(verdura);
            } else {

                //fruta
                double precio = Double.valueOf(parte.split(",")[0].trim());
                String tip = parte.split(",")[1];
                int tipo;
                if (tip.equals("dulce")) {
                    tipo = 1;
                } else {
                    tipo = 2;
                }
                String nombre = parte.split(",")[2];
                int cantidad = Integer.parseInt(parte.split(",")[3].trim());

                Fruta fruta = new Fruta(precio, tipo, nombre, cantidad);
                lista.add(fruta);

            }
        }
        return lista;
    }

    public static double precioCompra(ArrayList<Producto> lista) {

        double precio = 0;

        for (Producto producto : lista) {
            precio = (producto.getCantidad() * producto.getCantidad());
        }
        return precio;
    }

    public static int contarComas(String linea) {

        int numero = 0;

        for (int i = 0; i < linea.length(); i++) {

            if (linea.charAt(i) == ',') {
                numero++;
            }
        }

        return numero;
    }

    public static int contarPartes(String linea) {

        int numero = 0;

        for (int i = 0; i < linea.length(); i++) {

            if (i + 1 > linea.length()) {

            } else {

                if (linea.charAt(i) == '@' && linea.charAt(i + 1) == '@') {
                    numero++;
                }
            }

        }
        return numero;
    }

    //------------------------------------SERIALIZACIÓN  y XML
    public static Revuelteria cargarRecursoBancoBinario() {

        Revuelteria revuelteria = null;

        try {
            revuelteria = (Revuelteria) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_REVUELTERIA_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return revuelteria;
    }

    public static void guardarRecursoRevuelteriaBinario(Revuelteria revuelteria) {

        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_REVUELTERIA_BINARIO, revuelteria);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Revuelteria cargarRecursoRevuelteriaXML() {

        Revuelteria revuelteria = null;

        try {
            revuelteria = (Revuelteria) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_REVUELTERIA_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return revuelteria;

    }

    public static void guardarRecursoRevuelteriaXML(Revuelteria revuelteria) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_REVUELTERIA_XML, revuelteria);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //---------------------------- LOG
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {

        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }

//    public static void guardarReporteTxtClientes(File archivoSeleccionado, Usuario usuarioActivo, ArrayList<Cliente> listaClientes) throws IOException {
//        String contenido = "";
//        contenido += "                            Reporte de Listado de Clientes             \n";
//        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
//        contenido += "                            Reporte realizado por:" + usuarioActivo.getUser() + "                             \n\n";
//        for (Cliente cliente : listaClientes) {
//            contenido += "<" + cliente.getNombre() + ">" + "@@" + "<" + cliente.getCedula() + ">" + "@@"
//                    + "<" + cliente.getEdad() + ">" + "@@" + "<" + cliente.getDireccion() + ">" + "@@" + "<" + cliente.getEmail() + ">" + "\n";
//
//        }
//        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
//    }
    public static void guardarReporteTxtClientes(File archivoSeleccionado, String nombre, ArrayList<Cliente> listaClientes) throws IOException {
        String contenido = "";
        contenido += "                            Reporte de Listado de Clientes             \n";
        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
        contenido += "                            Reporte realizado por:" + nombre + "                             \n\n";
        for (Cliente cliente : listaClientes) {
            contenido += "<" + cliente.getNombre() + ">" + "@@" + "<" + cliente.getCedula() + ">" + "@@"
                    + "<" + cliente.getEdad() + ">" + "@@" + "<" + cliente.getDireccion() + ">" + "@@" + "<" + cliente.getEmail() + ">" + "\n";

        }
        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
    }
//    public static void guardarReporteTxtEmpleados(File archivoSeleccionado, Usuario usuarioActivo, ArrayList<Empleado> listaEmpleados) throws IOException {
//        String contenido = "";
//        contenido += "                            Reporte de Listado de Empleados             \n";
//        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
//        contenido += "                            Reporte realizado por:" + usuarioActivo.getUser() + "                             \n\n";
//        for (Empleado empleado : listaEmpleados) {
//            contenido += "<" + empleado.getNombre() + ">" + "@@" + "<" + empleado.getCedula() + ">" + "@@" + "<" + empleado.getEdad() + ">" + "@@"
//                    + "<" + empleado.getSalario() + ">" + "@@" + "<" + empleado.getCodigo() + ">" + "\n";
//        }
//        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
//    }
    public static void guardarReporteTxtEmpleados(File archivoSeleccionado, String nombreUsuario, ArrayList<Empleado> listaEmpleados) throws IOException {
        String contenido = "";
        contenido += "                            Reporte de Listado de Empleados             \n";
        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
        contenido += "                            Reporte realizado por:" + nombreUsuario + "                             \n\n";
        for (Empleado empleado : listaEmpleados) {
            contenido += "<" + empleado.getNombre() + ">" + "@@" + "<" + empleado.getCedula() + ">" + "@@" + "<" + empleado.getEdad() + ">" + "@@"
                    + "<" + empleado.getSalario() + ">" + "@@" + "<" + empleado.getCodigo() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
    }

    public static void guardarReporteTxtProductos(File archivoSeleccionado, Usuario usuarioActivo, ArrayList<Producto> listaProductos) throws IOException {
        String contenido = "";
        contenido += "                            Reporte de Listado de Productos             \n";
        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
        contenido += "                            Reporte realizado por:" + usuarioActivo.getUser() + "                             \n\n";
        ArrayList<Fruta> frutas = new ArrayList<>();
        ArrayList<Verdura> verduras = new ArrayList<>();

        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getClass() == Fruta.class) {
                Fruta fruta = (Fruta) listaProductos.get(i);
                frutas.add(fruta);
            } else {
                Verdura verdura = (Verdura) listaProductos.get(i);
                verduras.add(verdura);
            }
        }
        for (Fruta fruta : frutas) {
            contenido += "<" + fruta.getPrecio() + ">" + "@@" + "<" + fruta.getTipoFruta() + ">" + "@@" + "<" + fruta.getNombre() + ">" + "@@"
                    + "<" + fruta.getCantidad() + ">" + "\n";
        }
        for (Verdura verdura : verduras) {
            contenido += "<" + verdura.getPrecio() + ">" + "@@" + "<" + verdura.getNombre() + ">" + "@@" + "<" + verdura.getCantidad() + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
    }

public static void guardarReporteTxtVentas(File archivoSeleccionado, Usuario usuarioActivo, ArrayList<Venta> listaVentas) throws IOException {
        ArrayList<Producto> productosString = new ArrayList<>();
        ArrayList<String> ventasString = new ArrayList<>();
        String contenido = "";
        contenido += "                            Reporte de Listado de Ventas             \n";
        contenido += "                                    " + ArchivoUtil.cargarFechaSistema2() + "                             \n";
        contenido += "                            Reporte realizado por:" + usuarioActivo.getUser() + "                             \n\n";
        String listaCompra = "";

        int i = 0;

        //pasar la lista de productosCompra a un strign
        for (int j = 0; j < listaVentas.size(); j++) {

            productosString = listaVentas.get(j).getListaCompra();
            //System.out.println(productosString.size());
            for (int k = 0; k < productosString.size(); k++) {
                Producto producto = productosString.get(k);

                if (producto.getClass().equals(Fruta.class)) {
                    Fruta fruta = (Fruta) producto;
                    // System.out.println(fruta);
                    listaCompra += "<" + fruta.getPrecio() + "," + fruta.getTipoFruta() + "," + fruta.getNombre() + "," + fruta.getCantidad() + ">@@";
                } else {

                    //Verdura verdura = (Verdura) producto;
                    listaCompra += "<" + producto.getPrecio() + "," + producto.getNombre() + "," + producto.getCantidad() + ">@@";
                }

            }

            ventasString.add(listaCompra);
        }

        for (Venta venta : listaVentas) {
            contenido += "<" + venta.getEmpleado().getNombre() + "," + venta.getEmpleado().getCedula() + "," + venta.getEmpleado().getEdad() + ","
                    + venta.getEmpleado().getSalario() + "," + venta.getEmpleado().getCodigo() + ">@@"
                    + "<" + venta.getCliente().getNombre() + "," + venta.getCliente().getCedula() + "," + venta.getCliente().getEdad() + ","
                    + "," + venta.getCliente().getDireccion() + "," + venta.getCliente().getEmail() + ">" + "@@"
                    + ventasString.get(i) + ">" + "\n";
        }
        ArchivoUtil.guardarArchivo(archivoSeleccionado + ".txt", contenido, Boolean.FALSE);
    }

}
