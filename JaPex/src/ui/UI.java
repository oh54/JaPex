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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.Main;
import net.miginfocom.swing.MigLayout;

public class UI extends JFrame {

  public JTextArea txaInput;
  public JList<String> localList;
  public JList<String> stackList;
  public JLabel lblCurrent;

  public UI() {

    setTitle("Simple JVM simulator");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setMinimumSize(new Dimension(1000, 700));

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
    ImageIcon ff =
        new ImageIcon(((new ImageIcon("res/fastforward.png")).getImage()).getScaledInstance(20, 20,
            java.awt.Image.SCALE_SMOOTH), "ff");

    JButton btnFB = new JButton(fb);
    JButton btnB = new JButton(b);
    JButton btnF = new JButton(f);
    JButton btnFF = new JButton(ff);

    BtnListener btnListener = new BtnListener();
    btnFB.addActionListener(btnListener);
    btnB.addActionListener(btnListener);
    btnF.addActionListener(btnListener);
    btnFF.addActionListener(btnListener);

    btnPanel.add(btnFB);
    btnPanel.add(btnB);
    btnPanel.add(btnF);
    btnPanel.add(btnFF);

    lblCurrent = new JLabel();

    localList = new JList<String>();
    localList.setModel(new DefaultListModel());
    stackList = new JList<String>();
    stackList.setModel(new DefaultListModel());

    JScrollPane pnlLocal = new JScrollPane(localList);
    JScrollPane pnlStack = new JScrollPane(stackList);
    JLabel lblInput=new JLabel("<html><b>Paste or type Javap output (only the static method itself)</b></html>");
    JLabel lblLocal=new JLabel("<html><b>Local variables</b></html>");
    JLabel lblStack=new JLabel("<html><b>Operand Stack</b></html>");

    txaInput =
        new JTextArea(
            "  public static double meetod(double, int);\r\n"
                + "    Code:\r\n"
                + "       0: iinc          2, 1\r\n"
                + "       3: iconst_0\r\n"
                + "       4: istore_3\r\n"
                + "       5: iload_3\r\n"
                + "       6: iconst_4\r\n"
                + "       7: if_icmpge     23\r\n"
                + "      10: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;\r\n"
                + "      13: iload_3\r\n"
                + "      14: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V\r\n"
                + "      17: iinc          3, 1\r\n" + "      20: goto          5\r\n"
                + "      23: dload_0\r\n" + "      24: iload_2\r\n" + "      25: i2d\r\n"
                + "      26: dadd\r\n" + "      27: dreturn");
    Main.initalizeInput(txaInput.getText());
    JScrollPane scrlpInput = new JScrollPane(txaInput);
    txaInput.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {}

      public void insertUpdate(DocumentEvent e) {
        Main.initalizeInput(txaInput.getText());
      }

      public void removeUpdate(DocumentEvent e) {}
    });

    pnlWrap.add(btnPanel);
    pnlWrap.add(lblCurrent, "wrap,span, align left");
    pnlWrap.add(scrlpInput, "grow, push");
    pnlWrap.add(pnlLocal, "width 250!, growy, pushy");
    pnlWrap.add(pnlStack, "width 250!, growy, pushy,wrap");
    pnlWrap.add(lblInput);
    pnlWrap.add(lblLocal);
    pnlWrap.add(lblStack);

    add(pnlWrap);

    pack();
    setVisible(true);
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
  public void clearAll(){
    clearLocals();
    clearStack();
    lblCurrent.setText("");
  }

}
