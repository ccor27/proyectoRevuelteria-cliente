package views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import controller.ProductoViewController;
import model.Revuelteria;
import model.Verdura;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;

import model.Cliente;
import model.Fruta;
import model.Producto;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;

public class ProductoView extends Composite {
	private DataBindingContext m_bindingContext;
	private Text txtNombre;
	private Text txtPrecio;
	private Text txtCantidad;
	private Text txtBuscar;
	private Combo comboBoxTipoFruta;
	private Combo comboBoxTipo;
    private String tipo;
    private int tipoFruta = 0;
    private ProductoViewController productoViewController = new ProductoViewController();
    private Revuelteria revuelteria = productoViewController.getRevuelteria();
    private Table tblProductos;
    private TableViewer tableViewer;
    
    private Producto prod;
    private Fruta frutaSeleccionada;
    private Verdura verduraSeleccionada;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProductoView(Composite parent, int style) {
		super(parent, style);
		
		Group grpDetallesDelProducto = new Group(this, SWT.NONE);
		grpDetallesDelProducto.setText("Detalles del producto");
		grpDetallesDelProducto.setBounds(10, 10, 700, 164);
		
		Label lblNombre = new Label(grpDetallesDelProducto, SWT.NONE);
		lblNombre.setBounds(10, 82, 55, 15);
		lblNombre.setText("Nombre");
		
		Label lblCantidad = new Label(grpDetallesDelProducto, SWT.NONE);
		lblCantidad.setBounds(323, 82, 55, 15);
		lblCantidad.setText("Cantidad");
		
		Label lblPrecio = new Label(grpDetallesDelProducto, SWT.NONE);
		lblPrecio.setBounds(10, 132, 55, 15);
		lblPrecio.setText("Precio");
		
		txtNombre = new Text(grpDetallesDelProducto, SWT.BORDER);
		txtNombre.setBounds(71, 79, 184, 21);
		txtNombre.setEnabled(false);
		
		txtPrecio = new Text(grpDetallesDelProducto, SWT.BORDER);
		txtPrecio.setBounds(71, 129, 184, 21);
		txtPrecio.setEnabled(false);
		
		txtCantidad = new Text(grpDetallesDelProducto, SWT.BORDER);
		txtCantidad.setBounds(400, 79, 184, 21);
		txtCantidad.setEnabled(false);
		
		Label lblBuscar = new Label(grpDetallesDelProducto, SWT.NONE);
		lblBuscar.setBounds(323, 132, 55, 15);
		lblBuscar.setText("Buscar");
		
		txtBuscar = new Text(grpDetallesDelProducto, SWT.BORDER);
		txtBuscar.setBounds(400, 129, 184, 21);
		
		Label lblTipo = new Label(grpDetallesDelProducto, SWT.NONE);
		lblTipo.setBounds(10, 41, 55, 15);
		lblTipo.setText("Tipo");
		
		Label lblSabor = new Label(grpDetallesDelProducto, SWT.NONE);
		lblSabor.setBounds(323, 38, 55, 15);
		lblSabor.setText("Sabor");
		
		ComboViewer comboViewer = new ComboViewer(grpDetallesDelProducto, SWT.NONE);
		comboBoxTipo = comboViewer.getCombo();
		comboBoxTipo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
                 if(comboBoxTipo.getText().equalsIgnoreCase("VERDURA")){

                	 tipo = comboBoxTipo.getText();
                	 
                	 txtNombre.setEnabled(true);
                	 txtCantidad.setEnabled(true);
                	 txtPrecio.setEnabled(true);
                	 comboBoxTipoFruta.setEnabled(false);
                 }else{
                	 
                	 tipo = comboBoxTipo.getText();
                	 
                	 comboBoxTipoFruta.setEnabled(true);
                	 txtNombre.setEnabled(true);
                	 txtCantidad.setEnabled(true);
                	 txtPrecio.setEnabled(true);
                 }
                 
			}
		});
		comboBoxTipo.setItems(new String[] {"FRUTA", "VERDURA"});
		comboBoxTipo.setBounds(71, 33, 184, 23);
//		comboBoxTipo.setItem(1, "VERDURA");
//		comboBoxTipo.setItem(2, "FRUTA");
		
		ComboViewer comboViewer_1 = new ComboViewer(grpDetallesDelProducto, SWT.NONE);
		comboBoxTipoFruta = comboViewer_1.getCombo();
		comboBoxTipoFruta.setItems(new String[] {"DULCE", "ACIDA"});
		comboBoxTipoFruta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(comboBoxTipoFruta.getText().equalsIgnoreCase("dulce")){
					System.out.println(comboBoxTipoFruta.getSelectionIndex());//0
					tipoFruta = 1;
				}else{
					tipoFruta =2;
					System.out.println(comboBoxTipoFruta.getSelectionIndex());//1
				}
			}
		});
		comboBoxTipoFruta.setBounds(400, 33, 184, 23);
		comboBoxTipoFruta.setEnabled(false);
		
		Group grpListaDeProductos = new Group(this, SWT.NONE);
		grpListaDeProductos.setText("Lista de productos");
		grpListaDeProductos.setBounds(10, 180, 700, 223);
		
		tableViewer = new TableViewer(grpListaDeProductos, SWT.BORDER | SWT.FULL_SELECTION);
		tblProductos = tableViewer.getTable();
		tblProductos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
		
				 prod = (Producto) tblProductos.getItem(tblProductos.getSelectionIndex()).getData();
				
				if(prod.getClass()==Verdura.class){
					verduraSeleccionada = (Verdura) prod;
					txtNombre.setText(verduraSeleccionada.getNombre());
					txtCantidad.setText(String.valueOf(verduraSeleccionada.getCantidad()));
					txtPrecio.setText(String.valueOf(verduraSeleccionada.getPrecio()));
					comboBoxTipo.setText("VERDURA");
					comboBoxTipoFruta.setText("");
					tipo="verdura";
				}else{
					frutaSeleccionada = (Fruta) prod;
					txtNombre.setText(frutaSeleccionada.getNombre());
					txtCantidad.setText(String.valueOf(frutaSeleccionada.getCantidad()));
					txtPrecio.setText(String.valueOf(frutaSeleccionada.getPrecio()));
					String tipFruta = frutaSeleccionada.getTipoFruta();
					comboBoxTipo.setText("FRUTA");
					tipo="fruta";
					if(tipFruta.equalsIgnoreCase("dulce")){
						//comboBoxTipoFruta.setItem(0, "DULCE");
						comboBoxTipoFruta.setText("DULCE");
						tipoFruta=1;
					}else{
						//comboBoxTipoFruta.setItem(1, "ACIDA");
						comboBoxTipoFruta.setText("ACIDA");
						tipoFruta=2;
					}
					
				}
//				clienteSeleccionado = (Cliente) tblClientes.getItem(tblClientes.getSelectionIndex()).getData();
//				
//				txtNombre.setText(clienteSeleccionado.getNombre());
//				txtCedula.setText(String.valueOf(clienteSeleccionado.getCedula()));
//				txtEdad.setText(String.valueOf(clienteSeleccionado.getEdad()));
//				txtDireccion.setText(clienteSeleccionado.getDireccion());
//				txtEmail.setText(clienteSeleccionado.getEmail());
			}
		});
		tblProductos.setLinesVisible(true);
		tblProductos.setHeaderVisible(true);
		tblProductos.setBounds(10, 25, 680, 188);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(217);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCantidad = tableViewerColumn_1.getColumn();
		tblclmnCantidad.setWidth(214);
		tblclmnCantidad.setText("Cantidad");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnPrecioPorUnidad = tableViewerColumn_2.getColumn();
		tblclmnPrecioPorUnidad.setWidth(245);
		tblclmnPrecioPorUnidad.setText("Precio por unidad");
		
		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 409, 700, 111);
		
		Button btnCrear = new Button(grpAcciones, SWT.NONE);
		btnCrear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(verificarCampos()){
					
					String nombre = txtNombre.getText();
					int cantidad = Integer.parseInt(txtCantidad.getText().trim());
					double precio = Double.parseDouble(txtPrecio.getText().trim());
					
					if(tipo.equalsIgnoreCase("vedura")){
						
						if(productoViewController.crearVerdura(cantidad,nombre,precio)){
							JOptionPane.showMessageDialog(null, "Producto creado con exito");
							initDataBindings();
							vaciarCampos();
						}else{
							JOptionPane.showMessageDialog(null, "El producto ya existe.");
							vaciarCampos();
						}
						
					}else{
					
						if(productoViewController.crearFruta(cantidad,nombre,precio,tipoFruta)){
							JOptionPane.showMessageDialog(null, "Producto creado con exito");
							initDataBindings();
							vaciarCampos();
						}else{
							JOptionPane.showMessageDialog(null, "El producto ya existe.");
							vaciarCampos();
						}
					}
				}
			}
		});
		btnCrear.setBounds(20, 42, 114, 25);
		btnCrear.setText("Crear");
		
		Button btnModificar = new Button(grpAcciones, SWT.NONE);
		btnModificar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(verificarCampos()){
					String nombreModif ="";
					
					if(comboBoxTipo.getText().equalsIgnoreCase("verdura")){
					   	nombreModif=verduraSeleccionada.getNombre();
					   	
					   	productoViewController.modificarVerdura(nombreModif,Integer.parseInt(txtCantidad.getText().trim()),txtNombre.getText(),
					   		Double.parseDouble(txtPrecio.getText().trim())	);
					   	initDataBindings();
					   	vaciarCampos();
					}else{
						nombreModif=frutaSeleccionada.getNombre();
						
						productoViewController.modificarFruta(nombreModif,Integer.parseInt(txtCantidad.getText().trim()),txtNombre.getText(),
						   		Double.parseDouble(txtPrecio.getText().trim()), tipoFruta);
						initDataBindings();
						vaciarCampos();
					}
				}
				
			}
		});
		btnModificar.setBounds(148, 42, 114, 25);
		btnModificar.setText("Modificar");
		
		Button btnEliminar = new Button(grpAcciones, SWT.NONE);
		btnEliminar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!txtNombre.getText().equalsIgnoreCase("")){
					
					if(productoViewController.eliminarProducto(txtNombre.getText())){
						JOptionPane.showMessageDialog(null, "producto eliminado con exito.");
						initDataBindings();
						vaciarCampos();
					}else{
						JOptionPane.showMessageDialog(null, "el producto esta asociado a una venta o no existe.");
						vaciarCampos();
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "debe seleccionar un producto.");
				}
			}
		});
		btnEliminar.setBounds(282, 42, 114, 25);
		btnEliminar.setText("Eliminar");
		
		Button btnGenerarReporte = new Button(grpAcciones, SWT.NONE);
		btnGenerarReporte.setBounds(419, 42, 114, 25);
		btnGenerarReporte.setText("Generar reporte");
		
		Button btnSalir = new Button(grpAcciones, SWT.NONE);
		btnSalir.setBounds(561, 42, 114, 25);
		btnSalir.setText("Salir");
		m_bindingContext = initDataBindings();
		

	}
	
	public boolean verificarCampos(){
		
		if(tipo.equalsIgnoreCase("verdura")){
			
			if(txtNombre.getText().equalsIgnoreCase("") || txtCantidad.getText().equalsIgnoreCase("") || txtPrecio.getText().equalsIgnoreCase("")){
				JOptionPane.showMessageDialog(null, "debe llenar todos los campos.");
				return false;
			}else{
				if(verificarCampos2(txtCantidad.getText()) && verificarCampo3(txtPrecio.getText())){
					return true;
				}else{
					JOptionPane.showMessageDialog(null, "los campos Cantidad y Precio \n solo reciben NUMEROS.");
					txtCantidad.setText("");
					txtPrecio.setText("");
					return false;
				}
			}
			
	       }else{
		
				if(txtNombre.getText().equalsIgnoreCase("") || txtCantidad.getText().equalsIgnoreCase("") 
						|| txtPrecio.getText().equalsIgnoreCase("") || tipoFruta==0){
					JOptionPane.showMessageDialog(null, "debe llenar todos los campos.");
					return false;
				}else{
					if(verificarCampos2(txtCantidad.getText()) && verificarCampo3(txtPrecio.getText())){
						return true;
					}else{
						JOptionPane.showMessageDialog(null, "los campos Cantidad y Precio \n solo reciben NUMEROS.");
						txtCantidad.setText("");
						txtPrecio.setText("");
						return false;
					}
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
	
	private boolean verificarCampo3(String cadena){
		  String cadenaNueva = cadena.replace('.',' ');
		  cadenaNueva = cadenaNueva.replace(" ", "");
		  return verificarCampos2(cadenaNueva);
	}
	
	public void vaciarCampos(){
		txtNombre.setText("");
		txtCantidad.setText("");
		txtPrecio.setText("");
		comboBoxTipo.setText("");
		comboBoxTipoFruta.setText("");
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Producto.class, new String[]{"nombre", "cantidad", "precio"});
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer.setContentProvider(listContentProvider);
		//
		IObservableList listaProductosRevuelteriaObserveList = PojoProperties.list("listaProductos").observe(revuelteria);
		tableViewer.setInput(listaProductosRevuelteriaObserveList);
		//
		return bindingContext;
	}
}
