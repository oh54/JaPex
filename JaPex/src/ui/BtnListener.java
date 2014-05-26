package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import logic.Main;

public class BtnListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equals("Initialize")) {
      Main.initalizeInput();
      return;
    } else if (arg0.getActionCommand().equals("File")) {
      String fileName= JOptionPane.showInputDialog("Input .java or .class filename with path");
      if (fileName.endsWith(".java")){
        try {
          Main.readJava(fileName);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }else{
        try {
          Main.readClass(fileName);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else {
      String btnInfo = ((ImageIcon) ((JButton) arg0.getSource()).getIcon()).getDescription();
      switch (btnInfo) {
        case "fb":
          Main.toStart();
        case "b":
          Main.previousState();
          break;
        case "f":
          Main.nextState();
          break;
      }
    }
  }
}
