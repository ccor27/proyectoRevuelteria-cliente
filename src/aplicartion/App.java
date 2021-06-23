package aplicartion;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;

import views.RevuelteriaView;

@SuppressWarnings("deprecation")
public class App {

	public static void main(String args[]){

		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display),new Runnable() {
			
			@Override
			public void run() {
				
				Display display = Display.getDefault();
				Realm.runWithDefault(SWTObservables.getRealm(display),new Runnable() {
					
					@Override
					public void run() {
					
						RevuelteriaView revuelteria = new RevuelteriaView();
						revuelteria.open();
						
						Display.getCurrent().dispose();
                    
					}
				});
				
			}
		});
	}
}
