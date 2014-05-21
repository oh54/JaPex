package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BtnListener implements ActionListener{

  @Override
  public void actionPerformed(ActionEvent arg0) {
    String btnInfo=((ImageIcon)((JButton)arg0.getSource()).getIcon()).getDescription();
    switch(btnInfo){
      case "fb":;
      case "b":;
      case "f":;
      case "ff":;
    }


    
  }

}
