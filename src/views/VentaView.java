package views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Text;

import controller.VentaViewController;
import model.Revuelteria;
import model.Verdura;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;

import model.Cliente;
import model.Empleado;
import model.Fruta;
import model.Producto;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class VentaView extends Composite {
	private DataBindingContext m_bindingContext;
	private Table tblProductos;
	private Text txtCantidad;
	private Text txtTotal;
	private Combo comboBoxEmpleado;
	private Combo comboBoxCliente;
	private Button btnEmpezarVenta;
	private Button btnAgregarProducto;
	private Button btnSacarProducto;
	private Button btnComprar;
	private Button btnCancelarVenta;
	
	private VentaViewController ventaViewController = new VentaViewController();
	private Revuelteria revuelteria = ventaViewController.getRevuelteria();
	private TableViewer tableViewer_1;
	private ArrayList<String> listaNombresClientes;
	private ArrayList<String> listaNombresEmpleados;
	private ArrayList<Producto> listaProductosVenta;
	private Cliente clienteSeleccionado;
	private Empleado empleadoSeleccionado;
	private Producto productoSeleccionado;
	private Producto productoEnTablaCompra;
	private Table tblCompra;
	private double valorCompra;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public VentaView(Composite parent, int style) {
		super(parent, style);
		
		listaNombresClientes = new ArrayList<>();
		listaNombresEmpleados = new ArrayList<>();
		listaProductosVenta = new ArrayList<>();
		
		Group grpDetallesDeLa = new Group(this, SWT.NONE);
		grpDetallesDeLa.setText("Usuarios de la accion");
		grpDetallesDeLa.setBounds(10, 10, 700, 106);
		
		Label lblEmleado = new Label(grpDetallesDeLa, SWT.NONE);
		lblEmleado.setBounds(10, 45, 55, 15);
		lblEmleado.setText("Empleado");
		
		ComboViewer comboViewer = new ComboViewer(grpDetallesDeLa, SWT.NONE);
		comboBoxEmpleado = comboViewer.getCombo();
		comboBoxEmpleado.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				empleadoSeleccionado = ventaViewController.obtenerEmpleado(comboBoxEmpleado.getText());
			}
		});
		comboBoxEmpleado.setBounds(80, 45, 146, 23);
		
		
		Label lblCliente = new Label(grpDetallesDeLa, SWT.NONE);
		lblCliente.setBounds(273, 45, 47, 15);
		lblCliente.setText("Cliente");
		
		ComboViewer comboViewer_1 = new ComboViewer(grpDetallesDeLa, SWT.NONE);
		comboBoxCliente = comboViewer_1.getCombo();
		comboBoxCliente.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				clienteSeleccionado=ventaViewController.obtenerCliente(comboBoxCliente.getText());
			}
		});
		comboBoxCliente.setBounds(323, 42, 146, 23);
		
		
		
		btnEmpezarVenta = new Button(grpDetallesDeLa, SWT.NONE);
		btnEmpezarVenta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!(clienteSeleccionado==null || empleadoSeleccionado==null)){
					componentesIniciarVenta();
				}else{
					JOptionPane.showMessageDialog(null, "debe seleccionar un cliente y un empleado \n para empezar la venta-");
				}
			}
		});
		btnEmpezarVenta.setBounds(506, 45, 109, 25);
		btnEmpezarVenta.setText("Empezar venta");
		
		Group grpListaDeProductos = new Group(this, SWT.NONE);
		grpListaDeProductos.setText("Acciones de la venta");
		grpListaDeProductos.setBounds(10, 122, 700, 398);
		
		btnAgregarProducto = new Button(grpListaDeProductos, SWT.NONE);
		btnAgregarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(productoSeleccionado!=null){
					
					if(!txtCantidad.getText().equalsIgnoreCase("")){
						
						if(esNumero(txtCantidad.getText())){

							if(Integer.parseInt(txtCantidad.getText())<=productoSeleccionado.getCantidad()){
                     
								if(productoSeleccionado.getClass()==Verdura.class){
									
									Verdura verd = new Verdura();
									verd.setNombre(productoSeleccionado.getNombre());
									verd.setCantidad(Integer.parseInt(txtCantidad.getText()));
									verd.setPrecio(productoSeleccionado.getPrecio());
									//productoSeleccionado.setCantidad(productoSeleccionado.getCantidad()-Integer.parseInt(txtCantidad.getText()));
				    				agregarProductoCompra(verd);
								    
								}else{
									
									Fruta frut = new Fruta();
									frut.setNombre(productoSeleccionado.getNombre());
									frut.setCantidad(Integer.parseInt(txtCantidad.getText()));
									frut.setPrecio(productoSeleccionado.getPrecio());
									//productoSeleccionado.setCantidad(productoSeleccionado.getCantidad()-Integer.parseInt(txtCantidad.getText()));
									agregarProductoCompra(frut);
								}
								
								productoSeleccionado.setCantidad(productoSeleccionado.getCantidad()-Integer.parseInt(txtCantidad.getText()));
								valorCompra += Integer.parseInt(txtCantidad.getText())*productoSeleccionado.getPrecio();
								txtTotal.setText(String.valueOf(valorCompra));
								llenarTablaCompra();
								txtCantidad.setText("");
								initDataBindings();
								
							}else{
								JOptionPane.showMessageDialog(null, "No hay la cantidad que desea.");
							}
						}else{
							JOptionPane.showMessageDialog(null, "La cantidad es un valor NUMERICO.");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Debe ingresar la cantidad.");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.");
				}
			}
		});
		btnAgregarProducto.setBounds(318, 129, 122, 25);
		btnAgregarProducto.setText("Agregar producto");
		
		Label lblCantidad = new Label(grpListaDeProductos, SWT.NONE);
		lblCantidad.setBounds(318, 93, 55, 15);
		lblCantidad.setText("Cantidad");
		
		txtCantidad = new Text(grpListaDeProductos, SWT.BORDER);
		txtCantidad.setBounds(378, 90, 62, 21);
		
		btnSacarProducto = new Button(grpListaDeProductos, SWT.NONE);
		btnSacarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(productoEnTablaCompra!=null){
					valorCompra-=productoEnTablaCompra.getPrecio()*productoEnTablaCompra.getCantidad();
					txtTotal.setText(String.valueOf(valorCompra));
					Producto prod = ventaViewController.obtenerProducto(productoEnTablaCompra.getNombre());
					prod.setCantidad(prod.getCantidad()+productoEnTablaCompra.getCantidad());
					listaProductosVenta.remove(productoEnTablaCompra);
					llenarTablaCompra();
					initDataBindings();
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un producto de la compra.");
				}
			}
		});
		btnSacarProducto.setBounds(318, 177, 122, 25);
		btnSacarProducto.setText("Sacar producto");
		
		Group grpCompra = new Group(grpListaDeProductos, SWT.NONE);
		grpCompra.setText("Compra");
		grpCompra.setBounds(461, 25, 229, 235);
		
		TableViewer tableViewer = new TableViewer(grpCompra, SWT.BORDER | SWT.FULL_SELECTION);
		tblCompra = tableViewer.getTable();
		tblCompra.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				int num =  tblCompra.getSelectionIndex();
				productoEnTablaCompra = listaProductosVenta.get(num);
				
			}
		});
		tblCompra.setLinesVisible(true);
		tblCompra.setHeaderVisible(true);
		tblCompra.setBounds(10, 24, 209, 201);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(100);
		tblclmnNombre.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnCantidad = tableViewerColumn_1.getColumn();
		tblclmnCantidad.setWidth(100);
		tblclmnCantidad.setText("Cantidad");
		
		Group grpProductos = new Group(grpListaDeProductos, SWT.NONE);
		grpProductos.setText("Productos");
		grpProductos.setBounds(10, 25, 286, 235);
		
		tableViewer_1 = new TableViewer(grpProductos, SWT.BORDER | SWT.FULL_SELECTION);
		tblProductos = tableViewer_1.getTable();
		tblProductos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//empleadoSeleccionado = (Empleado) tblEmpleados.getItem(tblEmpleados.getSelectionIndex()).getData();
				productoSeleccionado = (Producto) tblProductos.getItem(tblProductos.getSelectionIndex()).getData();
			}
		});
		tblProductos.setBounds(10, 28, 265, 197);
		tblProductos.setLinesVisible(true);
		tblProductos.setHeaderVisible(true);
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnNombreProducto = tableViewerColumn_2.getColumn();
		tblclmnNombreProducto.setWidth(100);
		tblclmnNombreProducto.setText("Nombre");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnPrecioProducto = tableViewerColumn_3.getColumn();
		tblclmnPrecioProducto.setWidth(71);
		tblclmnPrecioProducto.setText("Precio");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnCantidadProducto = tableViewerColumn_4.getColumn();
		tblclmnCantidadProducto.setWidth(90);
		tblclmnCantidadProducto.setText("Cantidad");
		
		btnComprar = new Button(grpListaDeProductos, SWT.NONE);
		btnComprar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(clienteSeleccionado!=null && empleadoSeleccionado !=null){
					
					if(!listaProductosVenta.isEmpty()){
						
						if(ventaViewController.crearVenta(empleadoSeleccionado,clienteSeleccionado,listaProductosVenta,valorCompra)){
							JOptionPane.showMessageDialog(null, "Venta exitosa.");
							cancelarCompra();
			                componentesAntesIniciarVenta();
							comboBoxCliente.setEnabled(true);
							comboBoxEmpleado.setEnabled(true);
						}else{
							JOptionPane.showMessageDialog(null, "esta venta ya esta registrada \n"
									+ "o el cliente o empleado no existe,\n por lo tanto no se puede hacer");
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Debe agregar prodcutos a la compra");
					}
					
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente y un empleado primero.");
				}
			}
		});
		btnComprar.setBounds(32, 314, 122, 25);
		btnComprar.setText("Comprar");
		
		btnCancelarVenta = new Button(grpListaDeProductos, SWT.NONE);
		btnCancelarVenta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				cancelarCompra();
                componentesAntesIniciarVenta();
				comboBoxCliente.setEnabled(true);
				comboBoxEmpleado.setEnabled(true);
			}
		});
		btnCancelarVenta.setBounds(257, 314, 122, 25);
		btnCancelarVenta.setText("Cancelar venta");
		
		Label lblTotalDeLa = new Label(grpListaDeProductos, SWT.NONE);
		lblTotalDeLa.setBounds(461, 321, 104, 15);
		lblTotalDeLa.setText("Total de la compra");
		
		txtTotal = new Text(grpListaDeProductos, SWT.BORDER);
		txtTotal.setBounds(571, 318, 104, 21);
		m_bindingContext = initDataBindings();

		componentesAntesIniciarVenta();
	}
	
	public void componentesAntesIniciarVenta(){
		
		txtCantidad.setEnabled(false);
		txtTotal.setEnabled(false);
		btnAgregarProducto.setEnabled(false);
		btnSacarProducto.setEnabled(false);
		btnComprar.setEnabled(false);
		btnCancelarVenta.setEnabled(false);
		tblProductos.setEnabled(false);
		
	}
	
	public void componentesIniciarVenta(){
		txtCantidad.setEnabled(true);
		txtTotal.setEnabled(true);
		txtTotal.setEditable(false);
		btnAgregarProducto.setEnabled(true);
		btnSacarProducto.setEnabled(true);
		btnComprar.setEnabled(true);
		btnCancelarVenta.setEnabled(true);
		tblProductos.setEnabled(true);
		tblCompra.setEnabled(true);
		comboBoxCliente.setEnabled(false);
		comboBoxEmpleado.setEnabled(false);
	}
	
	public void llenarComboCliente(){
		
		if(listaNombresClientes.size()!=0){
			
			for (int i = 0; i < listaNombresClientes.size(); i++) {
			    comboBoxCliente.add(listaNombresClientes.get(i), i);	
			}
		}
	}
	
	public void llenarComboEmpleado(){
		
		if(listaNombresEmpleados.size()!=0){
			
			for (int i = 0; i < listaNombresEmpleados.size(); i++) {

			    comboBoxEmpleado.add(listaNombresEmpleados.get(i), i);	
			}
		}
	}
	
	public void llenarListaEmp(){
	listaNombresEmpleados = ventaViewController.obtenerNombresEmpleados();
	}
	public void llenarListClient(){
		listaNombresClientes = ventaViewController.obtenerNombresClientes();
	}
	
	public boolean esNumero(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	public void llenarTablaCompra(){
 
		tblCompra.removeAll();
		
		for (Producto prod : listaProductosVenta) {
			
			TableItem item1 = new TableItem(tblCompra, SWT.NONE);
			item1.setText(new String[]{prod.getNombre(),String.valueOf(prod.getCantidad())});

		}
		
	}
	
	public void agregarProductoCompra(Producto producto){

		boolean estaEnLaLista =false;
		
		for (Producto prod : listaProductosVenta) {
			if(prod.getNombre().equalsIgnoreCase(producto.getNombre())){
				prod.setCantidad(prod.getCantidad()+Integer.parseInt(txtCantidad.getText().trim()));
				estaEnLaLista =true;
			}
		}
		if(!estaEnLaLista){
			listaProductosVenta.add(producto);
		}
	}
	
	public void cancelarCompra(){
		
		Producto p = null;
		for (Producto prod : listaProductosVenta) {
			p = ventaViewController.obtenerProducto(prod.getNombre());
			p.setCantidad(p.getCantidad()+prod.getCantidad());
		}
		listaProductosVenta.removeAll(listaProductosVenta);
		tblCompra.removeAll();
		valorCompra=0;
		txtTotal.setText(String.valueOf(valorCompra));
		initDataBindings();
	}
	
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(), Producto.class, new String[]{"nombre", "precio", "cantidad"});
		tableViewer_1.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableViewer_1.setContentProvider(listContentProvider);
		//
		IObservableList listaProductosRevuelteriaObserveList = PojoProperties.list("listaProductos").observe(revuelteria);
		tableViewer_1.setInput(listaProductosRevuelteriaObserveList);
		//
		
		llenarListaEmp();
		llenarListClient();
		llenarComboCliente();
		llenarComboEmpleado();
		
		return bindingContext;
	}
}
