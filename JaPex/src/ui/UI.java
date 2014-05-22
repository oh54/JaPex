package ui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class UI extends JFrame {

  public UI() {

    setTitle("Simple JVM simulator");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(950, 775));
    
    JPanel pnlWrap= new JPanel();
    pnlWrap.setLayout(new MigLayout("","[center] [] []","[min!] []"));
    
    JPanel btnPanel= new JPanel();
    
    ImageIcon fb = new ImageIcon(((new ImageIcon("res/fastback.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH), "fb");
    ImageIcon b = new ImageIcon(((new ImageIcon("res/back.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH), "b");
    ImageIcon f = new ImageIcon(((new ImageIcon("res/forward.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH), "f");
    ImageIcon ff = new ImageIcon(((new ImageIcon("res/fastforward.png")).getImage()).getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH), "ff");
    
    JButton btnFB = new JButton(fb);
    JButton btnB = new JButton(b);
    JButton btnF = new JButton(f);
    JButton btnFF = new JButton(ff);
    
    BtnListener btnListener= new BtnListener();
    btnFB.addActionListener(btnListener);
    btnB.addActionListener(btnListener);
    btnF.addActionListener(btnListener);
    btnFF.addActionListener(btnListener);
    
    btnPanel.add(btnFB);
    btnPanel.add(btnB);
    btnPanel.add(btnF);
    btnPanel.add(btnFF);
    
    JPanel pnlLocal= new JPanel();
    JPanel pnlStack= new JPanel();
    
    JList lstLocal = new JList();
    JList lstStack = new JList();
    
    pnlLocal.add(lstLocal);
    pnlStack.add(lstStack);

    
    
    JScrollPane txaInput = new JScrollPane(new JTextArea("Type or paste Java bytecode here",40,35));
    txaInput.addKeyListener(new KeyListener(){

      @Override
      public void keyPressed(KeyEvent arg0) {
      }

      @Override
      public void keyReleased(KeyEvent arg0) {
        // TODO class.Initialize();
        
      }

      @Override
      public void keyTyped(KeyEvent arg0) {
      }
      
    });
    pnlWrap.add(btnPanel);
    pnlWrap.add(pnlLocal," span 1 2, width 250!, growy, pushy");
    pnlWrap.add(pnlStack, "wrap, span 1 2, width 250!, growy, pushy");
    pnlWrap.add(txaInput, "grow, push");
    
    add(pnlWrap);
    
    
    pack();
    setVisible(true);
  }

}
