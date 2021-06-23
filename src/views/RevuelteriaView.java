package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;

public class RevuelteriaView {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RevuelteriaView window = new RevuelteriaView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(765, 592);
		shell.setText("SWT Application");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(10, 10, 729, 533);
		
		//cliente
		TabItem tabItemCliente = new TabItem(tabFolder, SWT.NONE);
		tabItemCliente.setText("Cliente");
		
		ClienteView compositeCliente = new ClienteView(tabFolder, SWT.NONE, this);
		tabItemCliente.setControl(compositeCliente);
		
		//empleado
		TabItem tbtmEmpleado = new TabItem(tabFolder, SWT.NONE);
		tbtmEmpleado.setText("Empleado");
		
		EmpleadoView compositeEmpleado = new EmpleadoView(tabFolder, SWT.NONE,this);
		tbtmEmpleado.setControl(compositeEmpleado);
		
		//producto
		TabItem tbmProducto = new TabItem(tabFolder, SWT.NONE);
		tbmProducto.setText("Producto");
		
		ProductoView compositeProducto = new ProductoView(tabFolder, SWT.NONE);
		tbmProducto.setControl(compositeProducto);
		
		TabItem tbmVenta = new TabItem(tabFolder, SWT.NONE);
		tbmVenta.setText("Venta");
		
		VentaView compositeVenta = new VentaView(tabFolder, SWT.NONE);
		tbmVenta.setControl(compositeVenta);
		
		

	}
	
	public void close(){
		this.close();
	}
}
