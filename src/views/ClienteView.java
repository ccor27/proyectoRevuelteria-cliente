package views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.omg.CORBA.portable.ValueOutputStream;

import controller.ClienteViewController;
import model.Revuelteria;
import persistence.Persistencia;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
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
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ClienteView extends Composite {
	private DataBindingContext m_bindingContext;
	private Text txtNombre;
	private Text txtCedula;
	private Text txtEdad;
	private Text txtDireccion;
	private Text txtEmail;
	private Text txtBuscar;
	private Table tblClientes;
	
	private ClienteViewController clienteViewController = new ClienteViewController();
	private Revuelteria revuelteria = clienteViewController.getRevuelteria();
	private TableViewer tableViewer;//para el binding
	private Cliente clienteSeleccionado;
	private String busqueda = "";
	private RevuelteriaView revuelteriaView;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ClienteView(Composite parent, int style, RevuelteriaView revuelteriaView) {
		super(parent, style);
		this.revuelteriaView=revuelteriaView;
		
		Group grpDatosDelCliente = new Group(this, SWT.NONE);
		grpDatosDelCliente.setText("Datos del cliente");
		grpDatosDelCliente.setBounds(10, 10, 700, 161);
		
		Label lblNombre = new Label(grpDatosDelCliente, SWT.NONE);
		lblNombre.setBounds(10, 39, 55, 15);
		lblNombre.setText("Nombre");
		
		Label lblCadelua = new Label(grpDatosDelCliente, SWT.NONE);
		lblCadelua.setBounds(10, 77, 55, 15);
		lblCadelua.setText("Cedula");
		
		Label lblEdad = new Label(grpDatosDelCliente, SWT.NONE);
		lblEdad.setBounds(10, 116, 55, 15);
		lblEdad.setText("Edad");
		
		txtNombre = new Text(grpDatosDelCliente, SWT.BORDER);
		txtNombre.setBounds(68, 39, 210, 21);
		
		txtCedula = new Text(grpDatosDelCliente, SWT.BORDER);
		txtCedula.setBounds(68, 74, 210, 21);
		
		txtEdad = new Text(grpDatosDelCliente, SWT.BORDER);
		txtEdad.setBounds(68, 116, 210, 21);
		
		Label lblDireccion = new Label(grpDatosDelCliente, SWT.NONE);
		lblDireccion.setBounds(317, 39, 55, 15);
		lblDireccion.setText("Direccion");
		
		Label lblCorreo = new Label(grpDatosDelCliente, SWT.NONE);
		lblCorreo.setBounds(317, 74, 55, 15);
		lblCorreo.setText("Email");
		
		Label lblBuscar = new Label(grpDatosDelCliente, SWT.NONE);
		lblBuscar.setBounds(317, 116, 55, 15);
		lblBuscar.setText("Buscar");
		
		txtDireccion = new Text(grpDatosDelCliente, SWT.BORDER);
		txtDireccion.setBounds(378, 36, 210, 21);
		
		txtEmail = new Text(grpDatosDelCliente, SWT.BORDER);
		txtEmail.setBounds(378, 74, 210, 21);
		
		Group grpListaDeClientes = new Group(this, SWT.NONE);
		grpListaDeClientes.setText("Lista de clientes");
		grpListaDeClientes.setBounds(10, 178, 700, 237);
		
		tableViewer = new TableViewer(grpListaDeClientes, SWT.BORDER | SWT.FULL_SELECTION);
		tblClientes = tableViewer.getTable();
		tblClientes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				clienteSeleccionado = (Cliente) tblClientes.getItem(tblClientes.getSelectionIndex()).getData();
				
				txtNombre.setText(clienteSeleccionado.getNombre());
				txtCedula.setText(String.valueOf(clienteSeleccionado.getCedula()));
				txtEdad.setText(String.valueOf(clienteSeleccionado.getEdad()));
				txtDireccion.setText(clienteSeleccionado.getDireccion());
				txtEmail.setText(clienteSeleccionado.getEmail());
				
			}
		});
		
		tblClientes.setLinesVisible(true);
		tblClientes.setHeaderVisible(true);
		tblClientes.setBounds(10, 28, 680, 199);
		
		txtBuscar = new Text(grpDatosDelCliente, SWT.BORDER);
		txtBuscar.setBounds(378, 116, 210, 21);
		
		
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

				Cliente clienteBusquedad = (Cliente) element;
				String cedulaString = String.valueOf(clienteBusquedad.getCedula());

				try {

					return  
							clienteBusquedad.getNombre().contains(busqueda) ||
							clienteBusquedad.getNombre().toLowerCase().contains(busqueda.toLowerCase()) ||
							clienteBusquedad.getNombre().toUpperCase().contains(busqueda.toUpperCase()) ||

							clienteBusquedad.getEmail().contains(busqueda) ||
							clienteBusquedad.getEmail().toLowerCase().contains(busqueda.toLowerCase()) ||
							clienteBusquedad.getEmail().toUpperCase().contains(busqueda.toUpperCase()) ||

							cedulaString.contains(busqueda) ||
							cedulaString.toLowerCase().contains(busqueda.toLowerCase()) ||
							cedulaString.toUpperCase().contains(busqueda.toUpperCase()) ||

							clienteBusquedad.getDireccion().contains(busqueda) ||
							clienteBusquedad.getDireccion().toLowerCase().contains(busqueda.toLowerCase()) ||
							clienteBusquedad.getDireccion().toUpperCase().contains(busqueda.toUpperCase()) ;

				} catch (Exception e) {
					// TODO: handle exception
					return false;
				}
			}
		});
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(133);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCedula = tableViewerColumn_1.getColumn();
		tblclmnCedula.setWidth(119);
		tblclmnCedula.setText("Cedula");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCorreo = tableViewerColumn_2.getColumn();
		tblclmnCorreo.setWidth(93);
		tblclmnCorreo.setText("Edad");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnDireccion = tableViewerColumn_3.getColumn();
		tblclmnDireccion.setWidth(165);
		tblclmnDireccion.setText("Direccion");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnEmail = tableViewerColumn_4.getColumn();
		tblclmnEmail.setWidth(165);
		tblclmnEmail.setText("Email");
		
		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 421, 700, 99);
		
		Button btnCrear = new Button(grpAcciones, SWT.NONE);
		
		btnCrear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(validarCamposTexto()){

					if(clienteViewController.crearCliente(txtNombre.getText(),Integer.parseInt(txtCedula.getText()),
							Integer.parseInt(txtEdad.getText()), txtDireccion.getText(),txtEmail.getText())){
						JOptionPane.showMessageDialog(null, "Cliente creado con exito");
						vaciarCamposTexto();
						initDataBindings();
					}else{
						JOptionPane.showMessageDialog(null, "El cliente con la cedula: "+Integer.parseInt(txtCedula.getText())+" ya existe");
						vaciarCamposTexto();
					}
					
				}
			}
		});
		
		btnCrear.setBounds(10, 34, 110, 25);
		btnCrear.setText("Crear");
		
		Button btnModificar = new Button(grpAcciones, SWT.NONE);
		btnModificar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(validarCamposTexto()){

					String nombreClientModif = txtNombre.getText();
					
					clienteViewController.modificarCliente(nombreClientModif,txtNombre.getText(),Integer.parseInt(txtCedula.getText()),
							Integer.parseInt(txtEdad.getText()), txtDireccion.getText(),txtEmail.getText());
					
					JOptionPane.showMessageDialog(null, "modificacion con exito");
					initDataBindings();
					vaciarCamposTexto();
				}
			}
		});
		btnModificar.setBounds(143, 34, 110, 25);
		btnModificar.setText("Modificar");
		
		Button btnEliminar = new Button(grpAcciones, SWT.NONE);
		btnEliminar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!txtNombre.getText().equalsIgnoreCase("")){
					
					if(clienteViewController.eliminarCliente(txtNombre.getText())){
						JOptionPane.showMessageDialog(null, "cliente eliminado con exito.");
						initDataBindings();
						vaciarCamposTexto();
					}else{
						JOptionPane.showMessageDialog(null, "cliente asociado a una venta o no existe");
						vaciarCamposTexto();
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "debe seleccionar un cliente.");
					
				}
			}
		});
		btnEliminar.setBounds(282, 34, 110, 25);
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
				Persistencia.guardarReporteTxtClientes(archivoSeleccionado, "cristian", clienteViewController.getRevuelteria().getListaClientes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		btnGenerarReporte.setBounds(417, 34, 110, 25);
		btnGenerarReporte.setText("Generar reporte");
		
		Button btnSalir = new Button(grpAcciones, SWT.NONE);
		btnSalir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				revuelteriaView.close();
			}
		});
		btnSalir.setBounds(556, 34, 110, 25);
		btnSalir.setText("Salir");
		m_bindingContext = initDataBindings();

	}
	
	public boolean validarCamposTexto(){
		
		if(txtNombre.getText().equalsIgnoreCase("") || txtDireccion.getText().equalsIgnoreCase("") ||
				txtEmail.getText().equalsIgnoreCase("") || txtCedula.getText().equalsIgnoreCase("") || txtEdad.getText().equalsIgnoreCase("")){
			//el usuario no a escrito nada en los campos de texto
			JOptionPane.showMessageDialog(null, "Debe llenar los campos.");
			return false;
			
		}else{
			
			if(esNumero(txtCedula.getText()) && esNumero(txtEdad.getText())){

				if(txtEmail.getText().contains("@gmail.com")){
					//todo correcto
					return true;
				}else{
					JOptionPane.showMessageDialog(null, "el correo debe ser valido (@gmail.com) .");
					txtEmail.setText("");
					return false;
				}
			}else{
				//no escribio numeros en los campos de cedual y edad
				JOptionPane.showMessageDialog(null, "En el campo Cedula y Edad, solo puede ingresar NUMEROS.");
				txtCedula.setText("");
				txtEdad.setText("");
				return false;
			}
			
		}
	}	
	
		private boolean esNumero(String cadena){
			try {
				Integer.parseInt(cadena);
				return true;
			} catch (NumberFormatException nfe){
				return false;
			}
		}
		
		public void vaciarCamposTexto(){
			txtNombre.setText("");
			txtCedula.setText("");
			txtEdad.setText("");
			txtDireccion.setText("");
			txtEmail.setText("");
		}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Cliente.class, new String[]{"nombre", "cedula", "edad", "direccion", "email"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList listaClientesRevuelteriaObserveList = PojoProperties.list("listaClientes").observe(revuelteria);
		tableViewer.setInput(listaClientesRevuelteriaObserveList);
		//
		return bindingContext;
	}
}
