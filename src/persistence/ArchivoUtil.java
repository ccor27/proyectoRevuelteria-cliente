/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author cristian
 * @author juanj
 */
public class ArchivoUtil {

    static String fechaSistema = "";

    /**
     * Este metodo recibe una cadena con el contenido que se quiere guardar en
     * el archivo
     *
     * @param ruta es la ruta o path donde esta ubicado el archivo
     * @throws IOException
     */
    public static void guardarArchivo(String ruta, String contenido, Boolean flagSobreEscribir) throws IOException {

        FileWriter fw = new FileWriter(ruta, flagSobreEscribir);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
        //System.out.println("el archivo se guardo");
    }

    /**
     * ESte metodo retorna el contendio del archivo ubicado en una ruta,con la
     * lista de cadenas.
     *
     * @param ruta
     * @return
     * @throws IOException
     */
    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String> contenido = new ArrayList<>();
        FileReader fr = new FileReader(ruta);
        BufferedReader bfr = new BufferedReader(fr);
        String linea;
        while ((linea = bfr.readLine()) != null) {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }

    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        String log = "";
        Logger LOGGER = Logger.getLogger(ArchivoUtil.class.getName());
        FileHandler fileHandler = null;

        cargarFechaSistema();

        try {

            fileHandler = new FileHandler(rutaArchivo, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO, mensajeLog + ", " + accion + ", " + fechaSistema);
                    break;

                case 2:
                    LOGGER.log(Level.WARNING, mensajeLog + ", " + accion + ", "  + fechaSistema);
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE, mensajeLog + ", " + accion + ", " + fechaSistema);
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {

            fileHandler.close();
        }

    }

    private static void cargarFechaSistema() {

        String diaN = "";
        String mesN = "";
        String anoN = "";

        Calendar cal1 = Calendar.getInstance();

        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int ano = cal1.get(Calendar.YEAR);
        int hora = cal1.get(Calendar.HOUR);
        int minuto = cal1.get(Calendar.MINUTE);

        if (dia < 10) {
            diaN += "0" + dia;
        } else {
            diaN += "" + dia;
        }
        if (mes < 10) {
            mesN += "0" + mes;
        } else {
            mesN += "" + mes;
        }

        //		fecha_Actual+= año+"-"+mesN+"-"+ diaN;
        //		fechaSistema = año+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;
        fechaSistema = ano + "-" + mesN + "-" + diaN;
        //		horaFechaSistema = hora+"-"+minuto;
    }

    //------------------------------------SERIALIZACIÓN  y XML
    /**
     * Escribe en el fichero que se le pasa el objeto que se le envia
     *
     * @param rutaArchivo path del fichero que se quiere escribir
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static Object cargarRecursoSerializado(String rutaArchivo) throws Exception {
        Object aux = null;
//		Empresa empresa = null;
        ObjectInputStream ois = null;
        try {
            // Se crea un ObjectInputStream
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

            aux = ois.readObject();

        } catch (Exception e2) {
            throw e2;
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        return aux;
    }

    public static void salvarRecursoSerializado(String rutaArchivo, Object object) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;

    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

    }

    //---------------------- copia de seguridad
    public static void respaldo() {

        try {
            Path fuenteUsuarios = Paths.get("/home/cristian/td/persistencia/archivos/usuarios.txt");
            Path destinoUsuarios = Paths.get("/home/cristian/td/persistencia/respaldo/txt/usuariosR.txt "+cargarFechaSistema2());
            
            Path fueteCliente = Paths.get("/home/cristian/td/persistencia/archivos/clientes.txt");
            Path destinoCliente = Paths.get("/home/cristian/td/persistencia/respaldo/txt/clientesR.txt "+cargarFechaSistema2());
            
            Path fueteEmpleado = Paths.get("/home/cristian/td/persistencia/archivos/empleados.txt");
            Path destinoEmpleado = Paths.get("/home/cristian/td/persistencia/respaldo/txt/empleadosR.txt "+cargarFechaSistema2());
            
            Path fueteFruta = Paths.get("/home/cristian/td/persistencia/archivos/frutas.txt");
            Path destinoFruta = Paths.get("/home/cristian/td/persistencia/respaldo/txt/frutaR.txt "+cargarFechaSistema2());
            
            Path fueteVerdura = Paths.get("/home/cristian/td/persistencia/archivos/verduras.txt");
            Path destinoVerdura = Paths.get("/home/cristian/td/persistencia/respaldo/txt/verduraR.txt "+cargarFechaSistema2());
            
            Path fueteVenta = Paths.get("/home/cristian/td/persistencia/archivos/ventas.txt");
            Path destinoVenta = Paths.get("/home/cristian/td/persistencia/respaldo/txt/ventasR.txt "+cargarFechaSistema2());
            
            Path fuenteBinario = Paths.get("/home/cristian/td/persistencia/model.dat");
            Path destinoBinario = Paths.get("/home/cristian/td/persistencia/respaldo/xml_binario/modelBinarioR.dat "+cargarFechaSistema2());
            
            Path fuenteModelXML = Paths.get("/home/cristian/td/persistencia/model.xml");
            Path destinoXML = Paths.get("/home/cristian/td/persistencia/respaldo/xml_binario/modelXmlR.xml "+cargarFechaSistema2());
            
            Path fuenteLog = Paths.get("/home/cristian/td/persistencia/log/revuelteriaLog.txt");
            Path destinoLog = Paths.get("/home/cristian/td/persistencia/respaldo/log/revuelteriaLogR.txt "+cargarFechaSistema2());
            
            Files.copy(fuenteUsuarios, destinoUsuarios, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fueteCliente, destinoCliente, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fueteEmpleado, destinoEmpleado, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fueteFruta, destinoFruta, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fueteVerdura, destinoVerdura, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fueteVenta, destinoVenta, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fuenteModelXML, destinoXML, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fuenteBinario, destinoBinario, StandardCopyOption.COPY_ATTRIBUTES);
            Files.copy(fuenteLog, destinoLog, StandardCopyOption.COPY_ATTRIBUTES);

        } catch (IOException e) {
            System.err.println("Error al hacer el respaldo --> " + e.getMessage());
        }

    }
    
    public static String cargarFechaSistema2() {

		String diaN = "";
		String mesN = "";
		String anoN = "";

		Calendar cal1 = Calendar.getInstance();


		int dia = cal1.get(Calendar.DATE);
		int mes = cal1.get(Calendar.MONTH)+1;
		int ano = cal1.get(Calendar.YEAR);
		int hora = cal1.get(Calendar.HOUR);
		int minuto = cal1.get(Calendar.MINUTE);
                int segundo = cal1.get(Calendar.SECOND);


		if(dia < 10){
			diaN+="0"+dia;
		}
		
		else{
			diaN+=""+dia;
		}
		if(mes < 10){
			mesN+="0"+mes;
		}
		else{
			mesN+=""+mes;
		}

		//		fecha_Actual+= año+"-"+mesN+"-"+ diaN;
		//		fechaSistema = año+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto;
	//	fechaSistema = año+"-"+mesN+"-"+diaN;
		//		horaFechaSistema = hora+"-"+minuto;
		
		return fechaSistema = ano+"-"+mesN+"-"+diaN+"-"+hora+"-"+minuto+"-"+segundo;
	}
}
