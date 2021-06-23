package views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class LoginView {

	protected Shell shlRevuelteria;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginView window = new LoginView();
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
		shlRevuelteria.open();
		shlRevuelteria.layout();
		while (!shlRevuelteria.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRevuelteria = new Shell();
		shlRevuelteria.setToolTipText("");
		shlRevuelteria.setSize(327, 382);
		shlRevuelteria.setText("Revuelteria");
		
		Label lblNombreDeUsuario = new Label(shlRevuelteria, SWT.NONE);
		lblNombreDeUsuario.setBounds(10, 113, 110, 15);
		lblNombreDeUsuario.setText("Nombre de usuario");
		
		text = new Text(shlRevuelteria, SWT.BORDER);
		text.setBounds(138, 110, 137, 21);
		
		Label lblContrasea = new Label(shlRevuelteria, SWT.NONE);
		lblContrasea.setBounds(10, 171, 71, 15);
		lblContrasea.setText("Contrase\u00F1a");
		
		text_1 = new Text(shlRevuelteria, SWT.BORDER);
		text_1.setBounds(138, 168, 137, 21);
		
		Button btnEntrar = new Button(shlRevuelteria, SWT.NONE);
		btnEntrar.setBounds(59, 234, 75, 25);
		btnEntrar.setText("Entrar");
		
		Button btnSalir = new Button(shlRevuelteria, SWT.NONE);
		btnSalir.setBounds(181, 234, 75, 25);
		btnSalir.setText("Salir");

	}

}
