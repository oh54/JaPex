package program;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.UI;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			System.out.println(e.getStackTrace());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getStackTrace());
		} catch (InstantiationException e) {
			System.out.println(e.getStackTrace());
		} catch (IllegalAccessException e) {
			System.out.println(e.getStackTrace());
		}

		UI ui=new UI(); // Create and show the GUI.
		ui.setVisible(true);
	}
}
