package views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import controller.EmpleadoViewController;
import model.Revuelteria;
import persistence.Persistencia;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;

import model.Cliente;
import model.Empleado;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;

public class EmpleadoView extends Composite {
	private DataBindingContext m_bindingContext;
	private Text txtNombre;
	private Text txtCedula;
	private Text txtEdad;
	private Text txtSalario;
	private Text txtCodigo;
	private Text txtBuscar;
	private Table tblEmpleados;

	private EmpleadoViewController empleadoViewController = new EmpleadoViewController();
	private Revuelteria revuelteria = empleadoViewController.getRevuelteria();
	private TableViewer tableViewer;
	private Empleado empleadoSeleccionado;
	private String busqueda="";
	private RevuelteriaView revuelteriaView;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EmpleadoView(Composite parent, int style,RevuelteriaView revuelteriaView) {
		super(parent, style);
		this.revuelteriaView=revuelteriaView;
		Group grpDatosDelEmpleado = new Group(this, SWT.NONE);
		grpDatosDelEmpleado.setText("Datos del empleado");
		grpDatosDelEmpleado.setBounds(10, 10, 700, 165);
		
		Label lblNombre = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblNombre.setBounds(10, 32, 55, 15);
		lblNombre.setText("Nombre");
		
		Label lblCedula = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblCedula.setBounds(10, 68, 55, 15);
		lblCedula.setText("Cedula");
		
		Label lblEdad = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblEdad.setBounds(10, 114, 55, 15);
		lblEdad.setText("Edad");
		
		txtNombre = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtNombre.setBounds(71, 32, 212, 21);
		
		txtCedula = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtCedula.setBounds(71, 68, 212, 21);
		
		txtEdad = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtEdad.setBounds(71, 108, 212, 21);
		
		Label lblSalario = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblSalario.setBounds(333, 32, 55, 15);
		lblSalario.setText("Salario");
		
		Label lblCodigo = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblCodigo.setBounds(333, 68, 55, 15);
		lblCodigo.setText("Codigo");
		
		Label lblBuscar = new Label(grpDatosDelEmpleado, SWT.NONE);
		lblBuscar.setBounds(333, 111, 55, 15);
		lblBuscar.setText("Buscar");
		
		txtSalario = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtSalario.setBounds(394, 29, 212, 21);
		
		txtCodigo = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtCodigo.setBounds(394, 65, 212, 21);
		

		
		Group grpListaDeEmpleados = new Group(this, SWT.NONE);
		grpListaDeEmpleados.setText("Lista de empleados");
		grpListaDeEmpleados.setBounds(10, 178, 700, 224);
		
		tableViewer = new TableViewer(grpListaDeEmpleados, SWT.BORDER | SWT.FULL_SELECTION);
		tblEmpleados = tableViewer.getTable();
		tblEmpleados.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				empleadoSeleccionado = (Empleado) tblEmpleados.getItem(tblEmpleados.getSelectionIndex()).getData();
				txtNombre.setText(empleadoSeleccionado.getNombre());
				txtCedula.setText(String.valueOf(empleadoSeleccionado.getCedula()));
				txtEdad.setText(String.valueOf(empleadoSeleccionado.getEdad()));
				txtSalario.setText(String.valueOf(empleadoSeleccionado.getSalario()));
				txtCodigo.setText(String.valueOf(empleadoSeleccionado.getCodigo()));
				
			}
		});
		tblEmpleados.setHeaderVisible(true);
		tblEmpleados.setLinesVisible(true);
		tblEmpleados.setBounds(10, 24, 680, 190);
		
		txtBuscar = new Text(grpDatosDelEmpleado, SWT.BORDER);
		txtBuscar.setBounds(394, 108, 212, 21);
		
		// Filter at every keystroke
		txtBuscar.addModifyListener(new ModifyListener() {


			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				busqueda = source.getText();
				// Trigger update in the viewer
				tableViewer.refresh();
			}
		});

		
		txtBuscar.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text text = (Text) e.getSource();
					text.setText("");
					//
				}
			}
		});
		
		
		tableViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				Empleado empleadoBusqueda = (Empleado) element;
				String cedula = String.valueOf(empleadoBusqueda.getCedula());
						String edad = String.valueOf(empleadoBusqueda.getEdad());
						String salario = String.valueOf(empleadoBusqueda.getSalario());
						String codigo = String.valueOf(empleadoBusqueda.getCodigo());
				try {

					return  
							empleadoBusqueda.getNombre().contains(busqueda) ||
							empleadoBusqueda.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
							empleadoBusqueda.getNombre().toUpperCase().contains(busqueda.toUpperCase()) ||

							cedula.contains(busqueda) ||
							cedula.toLowerCase().contains(busqueda.toLowerCase()) ||
							cedula.toUpperCase().contains(busqueda.toUpperCase()) ||

							edad.contains(busqueda) ||
							edad.toLowerCase().contains(busqueda.toLowerCase()) ||
							edad.toUpperCase().contains(busqueda.toUpperCase()) ||

							salario.contains(busqueda) ||
							salario.toLowerCase().contains(busqueda.toLowerCase()) ||
 							salario.toUpperCase().contains(busqueda.toUpperCase()) ||
							
							codigo.contains(busqueda) ||
							codigo.toLowerCase().contains(busqueda.toLowerCase()) ||
							codigo.toUpperCase().contains(busqueda.toUpperCase());

				} catch (Exception e) {
					// TODO: handle exception
					return false;
				}
			}
		});
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(179);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCedula = tableViewerColumn_1.getColumn();
		tblclmnCedula.setWidth(145);
		tblclmnCedula.setText("Cedula");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEdad = tableViewerColumn_2.getColumn();
		tblclmnEdad.setWidth(87);
		tblclmnEdad.setText("Edad");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnSalario = tableViewerColumn_3.getColumn();
		tblclmnSalario.setWidth(143);
		tblclmnSalario.setText("Salario");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCodigo = tableViewerColumn_4.getColumn();
		tblclmnCodigo.setWidth(122);
		tblclmnCodigo.setText("Codigo");
		
		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 408, 700, 112);
		
		Button btnCrear = new Button(grpAcciones, SWT.NONE);
		btnCrear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(verificarCampos()){
					
					if(empleadoViewController.crearEmpleado(txtNombre.getText(),Integer.parseInt(txtCedula.getText()), Integer.parseInt(txtEdad.getText()),
							Integer.parseInt(txtSalario.getText()), Integer.parseInt(txtCodigo.getText()))){
						
						JOptionPane.showMessageDialog(null, "empleado creado con exito.");
						vaciarCampos();
						initDataBindings();
						
					}else{
						JOptionPane.showMessageDialog(null, "el empleado con la cedula: "+Integer.parseInt(txtCedula.getText())+
								" y con el codigo: "+Integer.parseInt(txtCodigo.getText())+" ya existe. \n" +" recuerde que los empleados no pueden compartir cedula o codigo.");
						vaciarCampos();
					}
				}
				
			}
		});
		btnCrear.setBounds(10, 41, 117, 25);
		btnCrear.setText("Crear");
		
		Button btnModificar = new Button(grpAcciones, SWT.NONE);
		btnModificar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(verificarCampos()){
					
				String nombreEmpModif = empleadoSeleccionado.getNombre();//obtenemos el nombre del cliente a modificar
				double salario = Double.parseDouble(txtSalario.getText());
				
				empleadoViewController.modificarEmpleado(nombreEmpModif,txtNombre.getText(),Integer.parseInt(txtCedula.getText()), Integer.parseInt(txtEdad.getText()),
						salario, Integer.parseInt(txtCodigo.getText()));
				
				JOptionPane.showMessageDialog(null, "modificacion con exito.");
				
				initDataBindings();
				vaciarCampos();
				
				}
				
			}
		});
		btnModificar.setBounds(150, 41, 117, 25);
		btnModificar.setText("Modificar");
		
		Button btnEliminar = new Button(grpAcciones, SWT.NONE);
		btnEliminar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!txtNombre.getText().equalsIgnoreCase("")){
					
					if(empleadoViewController.eliminarEmpleado(txtNombre.getText())){
						JOptionPane.showMessageDialog(null, "empleado eliminado con exito.");
						initDataBindings();
						vaciarCampos();
					}else{
						JOptionPane.showMessageDialog(null, "empleado asociado a una venta o no existe");
						vaciarCampos();
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "debe seleccionar un empleado.");
				}
			}
		});
		btnEliminar.setBounds(288, 41, 117, 25);
		btnEliminar.setText("Eliminar");
		
		Button btnGenerarReporte = new Button(grpAcciones, SWT.NONE);
		btnGenerarReporte.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		            File archivoSeleccionado;
		            JFileChooser seleccionarArchivo = new JFileChooser();
		            seleccionarArchivo.showSaveDialog(null);
		            archivoSeleccionado = seleccionarArchivo.getSelectedFile();
		           try {
					Persistencia.guardarReporteTxtEmpleados(archivoSeleccionado, "cristian", empleadoViewController.getRevuelteria().getListaEmpleados());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGenerarReporte.setBounds(428, 41, 117, 25);
		btnGenerarReporte.setText("Generar reporte");
		
		Button btnSalir = new Button(grpAcciones, SWT.NONE);
		btnSalir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				revuelteriaView.close();
			}
		});
		btnSalir.setBounds(573, 41, 117, 25);
		btnSalir.setText("Salir");
		m_bindingContext = initDataBindings();

	}
	
	public boolean verificarCampos(){
		if(txtNombre.getText().equalsIgnoreCase("") || txtCedula.getText().equalsIgnoreCase("") || txtEdad.getText().equalsIgnoreCase("")
				|| txtSalario.getText().equalsIgnoreCase("") || txtCodigo.getText().equalsIgnoreCase("")){
			
			JOptionPane.showMessageDialog(null, "debe llenar todos los campos");
			return false;
		}else{
			
			if(verificarCampos2(txtCedula.getText()) && verificarCampos2(txtEdad.getText()) && 
	             	verificarCampos3(txtSalario.getText()) && verificarCampos2( txtCodigo.getText()) ){
				
				return true;
				
			}else{
				
				JOptionPane.showMessageDialog(null, "en los campos: Cedula, Edad, Salario y Codigo. \n solo debe ingresar NUMEROS. ");
				txtCedula.setText("");
				txtEdad.setText("");
				txtSalario.setText("");
				txtCodigo.setText("");
				return false;
			}
		}
	}
	
	private boolean verificarCampos2(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
  private boolean verificarCampos3(String cadena){
	  
	  String cadenaNueva = cadena.replace('.',' ');
	  cadenaNueva = cadenaNueva.replace(" ", "");
	  return verificarCampos2(cadenaNueva);
  }
   

	public void vaciarCampos(){
		txtNombre.setText("");
		txtCedula.setText("");
		txtEdad.setText("");
		txtSalario.setText("");
		txtCodigo.setText("");
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Empleado.class, new String[]{"nombre", "cedula", "edad", "salario", "codigo"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList listaEmpleadosRevuelteriaObserveList = PojoProperties.list("listaEmpleados").observe(revuelteria);
		tableViewer.setInput(listaEmpleadosRevuelteriaObserveList);
		//
		return bindingContext;
	}
}
