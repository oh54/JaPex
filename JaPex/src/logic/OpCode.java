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
          localVariables.add(parameter + " (method parameter)");
          localVariables.add(parameter + " (method parameter)");
        } else {
          localVariables.add(parameter + " (method parameter)");
        }
      }
      Main.stateQueue.add(new State(beautifyText(line,
          "Method parameters are stored in local variables"), -1, localVariables,
          new Stack<String>()));
    } else if (Main.currentStateNr == 1) {
      Main.stateQueue.add(createState(beautifyText(line, ""), -1));
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
          iinc(line, getByteNr(lineTokens[0]),
              Integer.valueOf(lineTokens[2].substring(0, lineTokens[0].length() - 1)),
              lineTokens[3]);
          break;

        case 1:
          System.out.println("works!");
          load(line,getByteNr(lineTokens[0]),Integer.valueOf(lineTokens[2]));
          break;

        case 2:
          System.out.println("works2!");
          load(line,getByteNr(lineTokens[0]));
          break;

        case 3:
          System.out.println("works3!");
          store(line,getByteNr(lineTokens[0]),Integer.valueOf(lineTokens[2]));
          break;

        case 4:
          System.out.println("works4!");
          store(line,getByteNr(lineTokens[0]));
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
          xconst_y(line, getByteNr(lineTokens[0]));
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
          ifcmp(line,getByteNr(lineTokens[0]),Integer.valueOf(lineTokens[2]));
          // if_acmpeq,if_acmpne,if_icmpeq,if_icmpge,if_icmpgt,if_icmple,if_icmplt,if_icmpne
          break;
      }
    }
  }


  private static void ifcmp(String line, Integer byteNr, int goTo) {
    char type=line.split(" ")[1].charAt(0);
    String cmpMethod=line.substring(line.indexOf("cmp")+3,line.indexOf("cmp")+5);
    switch(cmpMethod){
    }
    
  }


  private static void load(String line, Integer byteNr) {
    load(line,byteNr,Integer.parseInt(String.valueOf((line.charAt(line.length()-1)))));
    
  }


  private static void load(String line, Integer byteNr, int index) {
    char type = line.split(" ")[1].charAt(0);
    State state= createState(beautifyText(line, "load a type "+type+" value from local variable #"+index),byteNr);
    state.addStack(state.getLocalVariables().get(index));
    Main.stateQueue.add(state);
  }


  private static void store(String line, Integer byteNr) {
    
    store(line,byteNr,Integer.parseInt(String.valueOf((line.charAt(line.length()-1)))));
    
  }
  private static void store(String line, Integer byteNr, int index){
    char type = line.split(" ")[1].charAt(0);
    State state= createState(beautifyText(line, "store a type "+type+" value (popped from stack) into a local variable #"+index),byteNr);
    state.setLocalVariableElement(index, state.popStack());
    Main.stateQueue.add(state);
  }


  private static void iinc(String line, int byteNr, int index, String constant) {
    State state =
        createState(
            beautifyText(line, "Increment local variable #" + index + " by signed byte const "
                + constant), byteNr);

    state.setLocalVariableElement(index,
        state.getLocalVariables().get(index) + " + " + String.valueOf(constant));
    Main.stateQueue.add(state);
  }

  private static void xconst_y(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    String value = String.valueOf(line.charAt(line.length()-1));
    State state =
        createState(
            beautifyText(line, "load the type " + type + " value " + value + " onto the stack"),
            byteNr);
    
    state.getOperandStack().push(value + " (type: "+type+")");
    if (type == 'l' || type == 'd') {
      state.getOperandStack().push(value + " (type: "+type+")");}
    Main.stateQueue.add(state);
  }

  private static State createState(String line, int byteNr) {
    return new State(line, byteNr, new ArrayList<String>(Main.stateQueue.get(
        Main.currentStateNr - 1).getLocalVariables()), (Stack<String>) Main.stateQueue
        .get(Main.currentStateNr - 1).getOperandStack().clone());
  }

  private static String beautifyText(String line, String explanation) {
    return "<html><b>" + line + "</b><br><font color='green'>Explanation: </font>" + explanation
        + "</html>";
  }

  private static Integer getByteNr(String lineToken) {
    return Integer.valueOf(lineToken.substring(0, lineToken.length() - 1));
  }
}
