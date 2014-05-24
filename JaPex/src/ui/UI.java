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
        new JTextArea("  public static double meetod(double, int);\r\n" + "    Code:\r\n"
            + "       0: iinc          2, 1\r\n" + "       3: iconst_0\r\n"
            + "       4: istore_3\r\n" + "       5: iload_3\r\n" + "       6: iconst_4");
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
