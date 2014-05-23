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
    ui.setVisible(true);

  }

  public static void initalizeInput(String in) {
    input = new Input(in);
  }

  public static void toStart() {
    currentStateNr = 0;
    stateQueue = new LinkedList<State>();
    ui.clearAll();
  }

  public static void nextState() {
    if (!(currentStateNr < stateQueue.size())) {
      String line = input.getLine(currentStateNr);
      OpCode.toMatch(line);
    }
    updateUI(true);
    currentStateNr++;
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
    ui.clearLocals();
    int stateIndex = (next ? currentStateNr : currentStateNr - 1);
    ui.lblCurrent.setText(stateQueue.get(stateIndex).getLine());
    for (String str : stateQueue.get(stateIndex).getLocalVariables()) {
      ui.addLocals(str);
    }
    ui.clearStack();
    for(int i=stateQueue.get(stateIndex).getOperandStack().size()-1;i>=0;i--){
      ui.addStack(stateQueue.get(stateIndex).getOperandStack().get(i));
    }
//    for (String str : stateQueue.get(stateIndex).getOperandStack()) {
//      ui.addStack(str);
//    }
  }


}
