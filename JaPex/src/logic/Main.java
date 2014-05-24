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
  public static int varNr;

  public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
      IllegalAccessException, UnsupportedLookAndFeelException {

    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
    Main.initalizeInput();
    ui.setVisible(true);

  }

  public static void initalizeInput() {
    ui.disableButtons();
    currentStateNr = 0;
    stateQueue = new LinkedList<State>();
    String in=ui.txaInput.getText();
    input = new Input(in);
    for (String line:input.getInputTokens()){
      OpCode.toMatch(line);
      currentStateNr++;
    }
    currentStateNr = 0;
    ui.enableButtons();
  }

  public static void toStart() {
    currentStateNr = 0;
    ui.clearAll();
  }

  public static void nextState() {
    if (currentStateNr < stateQueue.size()) {
      updateUI(true);
      currentStateNr++;
      
    }else{
      ui.lblCurrent.setText("Oled jõudnud meetodi lõppu");
    }
    
  }

  public static void previousState() {
    if (currentStateNr > 1) {
      currentStateNr--;
      updateUI(false);
    } else {
      toStart();
    }
  }

  public static void updateUI(boolean next) {
    int stateIndex = (next ? currentStateNr : currentStateNr - 1);
    ui.lblCurrent.setText(stateQueue.get(stateIndex).getLine());
    updateLocals(stateIndex);
    updateStack(stateIndex);
  }

  private static void updateStack(int stateIndex) {
    ui.clearStack();
    int varNr = 0;
    boolean first = true;
    for (int i = stateQueue.get(stateIndex).getOperandStack().size() - 1; i >= 0; i--) {
      StoredValue value = stateQueue.get(stateIndex).getOperandStack().get(i);
      if (!first && value == stateQueue.get(stateIndex).getOperandStack().get(i + 1)) {
        varNr--;
      }
      first = false;
      ui.addStack(varNr++ + ":  " + value.toString());
    }
  }

  private static void updateLocals(int stateIndex) {
    ui.clearLocals();
    boolean first = true;
    int varNr = 0;
    for (StoredValue storedValue : stateQueue.get(stateIndex).getLocalVariables()) {
      if (!first
          && storedValue == stateQueue.get(stateIndex).getLocalVariables()
              .get(stateQueue.get(stateIndex).getLocalVariables().lastIndexOf(storedValue) - 1)) {
        varNr--;
      }
      first=false;
      ui.addLocals(varNr++ + ":  " + storedValue.toString());
    }
  }
}
