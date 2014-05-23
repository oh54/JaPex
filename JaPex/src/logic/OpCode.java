package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OpCode {
  public static void toMatch(String line) {

    if (Main.currentStateNr == 0) {
      String[] parameters = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(",");
      List<String> localVariables = new ArrayList<String>();
      for (String parameter : parameters) {
        parameter = parameter.trim();
        if (parameter.equals("long") || parameter.equals("double")) {
          localVariables.add(parameter);
          localVariables.add(parameter);
        } else {
          localVariables.add(parameter);
        }
      }
      Main.stateQueue.add(new State(line, -1, localVariables, new Stack<String>()));
      Main.updateUI(line);
    } else if (Main.currentStateNr == 1) {
      Main.stateQueue.add(Main.stateQueue.get(0));
      Main.stateQueue.get(1).setLine(line);
      Main.updateUI(line);
    } else {

      String[] regexArray =
          new String[] {"iinc", "[adfil]load$", "[adfil]load_[0-3]", "[adfil]store",
              "[adfil]store_[0-3]", "[dfil]2[bcdilfs]$", "[ilfd]add$", "[fild]const_([0-5]|m1)",
              "[dfil]div$", "[dfil]mul$", "[dfil]neg", "[dfil]rem", "[dfil]sub", "pop|pop2",
              "new$", "sipush$", "ldc|ldc_w", "if(eq|ge|gt|le|lt|ne)$", "goto|goto_w",
              "[lfd]cmp([gl])*", "get(field|static)", "invoke(dynamic|special|static)",
              "instanceof", "if_[ai]cmp(eq|ne|ge|gt|le|lt)"};

      String[] lineTokens = line.split("( )+");
      String inputCode = lineTokens[1];
      int matchingIndex = -1;

      for (int i = 0; i < regexArray.length; i++) {
        if (inputCode.matches(regexArray[i])) {
          matchingIndex = i;
          break;
        }
      }

      switch (matchingIndex) {
        case 0:
          iinc(line, Integer.valueOf(lineTokens[0].substring(0, lineTokens[0].length() - 1)),
              Integer.valueOf(lineTokens[2].substring(0, lineTokens[0].length() - 1)),
              lineTokens[3]);
          break;

        case 1:
          System.out.println("works!");
          // TODO
          // aload x, dload x etc
          break;

        case 2:
          System.out.println("works2!");
          // TODO
          // aload_x, dload_x etc
          break;

        case 3:
          System.out.println("works3!");
          // TODO
          // astore x, dstore x etc
          break;

        case 4:
          System.out.println("works4!");
          // TODO
          // astore_x, dstore_x etc
          break;

        case 5:
          System.out.println("works5!");
          // TODO
          // All the conversions.
          break;

        case 6:
          System.out.println("works6!");
          // TODO
          // iadd, dadd, fadd, ladd
          break;

        case 7:
          System.out.println("works7!");
          // TODO
          // iconst_0, iconst_m1, fconst_2 etc
          break;

        case 8:
          System.out.println("works8!");
          // TODO
          // fdiv, idiv, ldiv etc
          break;

        case 9:
          System.out.println("works9!");
          // TODO
          // dmul, fmul, imul, lmul
          break;

        case 10:
          System.out.println("works10!");
          // TODO
          // dneg, lneg, fneg, ineg
          break;

        case 11:
          System.out.println("works11!");
          // TODO
          // drem, lrem, frem, irem
          break;

        case 12:
          System.out.println("works12!");
          // TODO
          // dsub, lsub, fsub, isub
          break;

        case 13:
          System.out.println("works13!");
          // TODO
          // pop, pop2
          break;

        case 14:
          System.out.println("works14!");
          // TODO
          // new
          break;

        case 15:
          System.out.println("works15!");
          // TODO
          // sipush
          break;

        case 16:
          System.out.println("works16!");
          // TODO
          // ldc, ldc_w
          break;

        case 17:
          System.out.println("works17!");
          // TODO
          // ifeq, iflt, ifle etc
          break;

        case 18:
          System.out.println("works18!");
          // TODO
          // goto, goto_w
          break;

        case 19:
          System.out.println("works19!");
          // TODO
          // lcmp, fcmpl, fcmpg, dcmpl, dcmpg
          break;

        case 20:
          System.out.println("works20!");
          // TODO
          // getfield, getstatic
          break;

        case 21:
          System.out.println("works21!");
          // TODO
          // invokedynamic, invokespecial, invokestatic
          break;

        case 22:
          System.out.println("works22!");
          // TODO
          // instanceof
          break;

        case 23:
          System.out.println("works23!");
          // TODO
          // if_acmpeq,if_acmpne,if_icmpeq,if_icmpge,if_icmpgt,if_icmple,if_icmplt,if_icmpne
          break;
      }
    }
  }

  private static void iinc(String line, int byteNr, int index, String constant) {
    State state =
        new State(line, byteNr, Main.stateQueue.get(Main.currentStateNr - 1).getLocalVariables(),
            Main.stateQueue.get(Main.currentStateNr - 1).getOperandStack());

    state.setLocalVariableElement(index,
        state.getLocalVariables().get(index) + " + " + String.valueOf(constant));
    Main.stateQueue.add(state);
    Main.updateUI(line + ": increment local variable #index by signed byte const");
  }

  private static void consts(String line, int byteNr, String opLetter, String arg) {

  }
  
}
