package logic;

import java.util.LinkedList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.UI;

public class Main {
	public static Input input;
	public static LinkedList<State> stateQueue = new LinkedList<State>();
	public static int currentStateNr = 0;
	public static UI ui;

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

		ui = new UI();
		ui.setVisible(true);
	}

	public static void initalizeInput(String in) {
		input = new Input(in);
	}

	public static void toStart() {
		currentStateNr = 0;
	}

	public static void NextState() {
		String line = input.getLine(currentStateNr);
		OpCode.toMatch(line);
		updateUI(line);
		currentStateNr++;
	}

	private static void updateUI(String line) {
		ui.lblCurrent.setText(line);
		ui.ClearLocals();
		for (String str: Main.stateQueue.get(currentStateNr).getLocalVariables()){
			ui.addLocals(str);
		}
		ui.ClearStack();
		for (String str: Main.stateQueue.get(currentStateNr).getOperandStack()){
			ui.addStack(str);
		} 
	}

	public static void PreviousState() {
		// TODO:
	}
}
