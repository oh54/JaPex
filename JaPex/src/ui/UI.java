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
        new JTextArea("  public static double some(double, double, java.lang.String);\r\n"
            + "    Code:\r\n" + "       0: ldc2_w        #3                  // double 2.5d\r\n"
            + "       3: dstore        5\r\n"
            + "       5: ldc2_w        #5                  // double 4.0d\r\n"
            + "       8: dstore        7\r\n" + "      10: dload         5\r\n"
            + "      12: dload         7\r\n" + "      14: dmul\r\n"
            + "      15: dstore        9\r\n" + "      17: dload         5\r\n"
            + "      19: dload         7\r\n" + "      21: dadd\r\n"
            + "      22: dstore        11\r\n" + "      24: iconst_1\r\n"
            + "      25: istore        13\r\n" + "      27: iconst_2\r\n"
            + "      28: istore        14\r\n" + "      30: iload         14\r\n"
            + "      32: iload         13\r\n" + "      34: isub\r\n"
            + "      35: istore        15\r\n" + "      37: iload         13\r\n"
            + "      39: i2d\r\n" + "      40: dload         11\r\n" + "      42: dcmpl\r\n"
            + "      43: ifle          53\r\n"
            + "      46: ldc           #7                  // String v├żiksem\r\n"
            + "      48: astore        16\r\n" + "      50: goto          57\r\n"
            + "      53: ldc           #8                  // String suurem\r\n"
            + "      55: astore        16\r\n" + "      57: iconst_0\r\n"
            + "      58: istore        16\r\n" + "      60: iload         16\r\n"
            + "      62: iconst_4\r\n" + "      63: if_icmpge     76\r\n" + "      66: dload_0\r\n"
            + "      67: dconst_1\r\n" + "      68: dadd\r\n" + "      69: dstore_0\r\n"
            + "      70: iinc          16, 1\r\n" + "      73: goto          60\r\n"
            + "      76: dload_2\r\n" + "      77: dreturn");
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
