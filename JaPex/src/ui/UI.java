package ui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class UI extends JFrame {

  public JTextArea txaInput;
  public JList<String> localList;
  public JList<String> stackList;
  public JLabel lblCurrent;
  private JButton btnFB;
  private JButton btnB;
  private JButton btnF;
  private JButton btnInit;

  public UI() {

    setTitle("Simple JVM simulator");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1100, 700));

    JPanel pnlWrap = new JPanel();
    pnlWrap.setLayout(new MigLayout("", "[center] [center] [center]", "[min!] [] []"));

    JPanel btnPanel = new JPanel();

    ImageIcon fb =
        new ImageIcon(((new ImageIcon("res/fastback.png")).getImage()).getScaledInstance(20, 20,
            java.awt.Image.SCALE_SMOOTH), "fb");
    ImageIcon b =
        new ImageIcon(((new ImageIcon("res/back.png")).getImage()).getScaledInstance(20, 20,
            java.awt.Image.SCALE_SMOOTH), "b");
    ImageIcon f =
        new ImageIcon(((new ImageIcon("res/forward.png")).getImage()).getScaledInstance(20, 20,
            java.awt.Image.SCALE_SMOOTH), "f");

    btnFB = new JButton(fb);
    btnB = new JButton(b);
    btnF = new JButton(f);
    btnInit = new JButton("Initialize");

    BtnListener btnListener = new BtnListener();
    btnInit.addActionListener(btnListener);
    btnFB.addActionListener(btnListener);
    btnB.addActionListener(btnListener);
    btnF.addActionListener(btnListener);


    btnPanel.add(btnInit);
    btnPanel.add(btnFB);
    btnPanel.add(btnB);
    btnPanel.add(btnF);

    lblCurrent = new JLabel();

    localList = new JList<String>();
    localList.setModel(new DefaultListModel());
    stackList = new JList<String>();
    stackList.setModel(new DefaultListModel());

    JScrollPane pnlLocal = new JScrollPane(localList);
    JScrollPane pnlStack = new JScrollPane(stackList);
    JLabel lblInput =
        new JLabel(
            "<html><b>Paste or type Javap output (only the static method itself). Initialize after editing.</b></html>");
    JLabel lblLocal = new JLabel("<html><b>Local variables</b></html>");
    JLabel lblStack = new JLabel("<html><b>Operand Stack</b></html>");

    txaInput =
        new JTextArea(" public static double some(double, double, java.lang.String);\r\n" + 
            "   Code:\r\n" + 
            "      0: ldc2_w        #3                  // double 2.5d\r\n" + 
            "      3: dstore        5\r\n" + 
            "      5: ldc2_w        #5                  // double 4.0d\r\n" + 
            "      8: dstore        7\r\n" + 
            "     10: ldc2_w        #7                  // double 2.0d\r\n" + 
            "     13: dstore        9\r\n" + 
            "     15: dload         5\r\n" + 
            "     17: dload         7\r\n" + 
            "     19: dmul\r\n" + 
            "     20: dstore        11\r\n" + 
            "     22: dload         11\r\n" + 
            "     24: dload         9\r\n" + 
            "     26: ddiv\r\n" + 
            "     27: dstore        13\r\n" + 
            "     29: dload         11\r\n" + 
            "     31: dload         7\r\n" + 
            "     33: drem\r\n" + 
            "     34: dstore        15\r\n" + 
            "     36: dload         5\r\n" + 
            "     38: dload         7\r\n" + 
            "     40: dadd\r\n" + 
            "     41: dstore        17\r\n" + 
            "     43: dload         7\r\n" + 
            "     45: dload         5\r\n" + 
            "     47: dsub\r\n" + 
            "     48: dstore        19\r\n" + 
            "     50: dload         15\r\n" + 
            "     52: dload         17\r\n" + 
            "     54: dcmpl\r\n" + 
            "     55: ifle          65\r\n" + 
            "     58: ldc           #9                  // String suurem\r\n" + 
            "     60: astore        21\r\n" + 
            "     62: goto          69\r\n" + 
            "     65: ldc           #10                 // String väiksem\r\n" + 
            "     67: astore        21\r\n" + 
            "     69: iconst_0\r\n" + 
            "     70: istore        21\r\n" + 
            "     72: iload         21\r\n" + 
            "     74: iconst_4\r\n" + 
            "     75: if_icmpge     88\r\n" + 
            "     78: dload_0\r\n" + 
            "     79: dconst_1\r\n" + 
            "     80: dadd\r\n" + 
            "     81: dstore_0\r\n" + 
            "     82: iinc          21, 1\r\n" + 
            "     85: goto          72\r\n" + 
            "     88: dload_2\r\n" + 
            "     89: dreturn");
    JScrollPane scrlpInput = new JScrollPane(txaInput);

    pnlWrap.add(btnPanel);
    pnlWrap.add(lblCurrent, "wrap,span, align left");
    pnlWrap.add(scrlpInput, "grow, push");
    pnlWrap.add(pnlLocal, "width 300!, growy, pushy");
    pnlWrap.add(pnlStack, "width 300!, growy, pushy,wrap");
    pnlWrap.add(lblInput);
    pnlWrap.add(lblLocal);
    pnlWrap.add(lblStack);

    add(pnlWrap);

    pack();

  }

  public void clearLocals() {
    DefaultListModel<String> listModel = (DefaultListModel<String>) localList.getModel();
    listModel.removeAllElements();
  }

  public void addLocals(String element) {
    DefaultListModel<String> listModel = (DefaultListModel<String>) localList.getModel();
    listModel.addElement(element);
  }

  public void clearStack() {
    DefaultListModel<String> listModel = (DefaultListModel<String>) stackList.getModel();
    listModel.removeAllElements();
  }

  public void addStack(String element) {
    DefaultListModel<String> listModel = (DefaultListModel<String>) stackList.getModel();
    listModel.addElement(element);
  }

  public void clearAll() {
    clearLocals();
    clearStack();
    lblCurrent.setText("");
  }

  public void disableButtons() {
    btnB.setEnabled(false);
    btnFB.setEnabled(false);
    btnF.setEnabled(false);
    btnInit.setEnabled(false);
  }

  public void enableButtons() {
    btnB.setEnabled(true);
    btnFB.setEnabled(true);
    btnF.setEnabled(true);
    btnInit.setEnabled(true);
  }

}
