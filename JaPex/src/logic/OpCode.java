package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OpCode {
  static String[] regexArray = new String[] {"iinc", "[adfil]load$", "[adfil]load_[0-3]",
      "[adfil]store", "[adfil]store_[0-3]", "[dfil]2[bcdilfs]$", "[ilfd]add$",
      "[fild]const_([0-5]|m1)", "[dfil]div$", "[dfil]mul$", "[dfil]neg", "[dfil]rem", "[dfil]sub",
      "pop|pop2", "new$", "dup(2)?$", "ldc|ldc(2)?_w", "if(eq|ge|gt|le|lt|ne)$", "goto|goto_w",
      "[lfd]cmp([gl])*", "get(field|static)", "invoke(dynamic|special|static)", "instanceof",
      "if_[ai]cmp(eq|ne|ge|gt|le|lt)", ".return"};

  public static void toMatch(String line) {

    if (Main.currentByteNrIndex == 0) {
      String[] parameters = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(",");
      List<StoredValue> localVariables = new ArrayList<StoredValue>();
      int i = 1;
      for (String parameter : parameters) {

        parameter = parameter.trim();
        if (parameter.contains(".")) {
          parameter = parameter.substring(parameter.lastIndexOf('.') + 1);
        }
        StoredValue storedValue = new StoredValue("<method parameter#" + i + ">", parameter);
        localVariables.add(storedValue);
        if (parameter.equals("long") || parameter.equals("double")) {
          localVariables.add(storedValue);
        }
        i++;
      }
      Main.stateQueue.add(new State(beautifyText(line,
          "Method parameters are stored in local variables"), -1, localVariables,
          new Stack<StoredValue>()));

    } else if (Main.currentByteNrIndex == 1) {
      Main.stateQueue.add(createState(beautifyText(line, ""), -1));
    } else {



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
              Integer.valueOf(lineTokens[2].substring(0, lineTokens[2].length() - 1)),
              lineTokens[3]);
          break;

        case 1:
          System.out.println("works!");
          load(line, getByteNr(lineTokens[0]), Integer.valueOf(lineTokens[2]));
          break;

        case 2:
          System.out.println("works2!");
          load(line, getByteNr(lineTokens[0]));
          break;

        case 3:
          System.out.println("works3!");
          store(line, getByteNr(lineTokens[0]), Integer.valueOf(lineTokens[2]));
          break;

        case 4:
          System.out.println("works4!");
          store(line, getByteNr(lineTokens[0]));
          break;

        case 5:
          System.out.println("works5!");
          // TODO
          // All the conversions.
          break;

        case 6:
          System.out.println("works6!");
          add(line, getByteNr(lineTokens[0]));
          break;

        case 7:
          xconst_y(line, getByteNr(lineTokens[0]));
          break;

        case 8:
          System.out.println("works8!");
          div(line, getByteNr(lineTokens[0]));
          break;

        case 9:
          System.out.println("works9!");
          mul(line, getByteNr(lineTokens[0]));
          break;

        case 10:
          System.out.println("works10!");
          neg(line, getByteNr(lineTokens[0]));
          break;

        case 11:
          System.out.println("works11!");
          rem(line, getByteNr(lineTokens[0]));
          break;

        case 12:
          System.out.println("works12!");
          sub(line, getByteNr(lineTokens[0]));
          break;

        case 13:
          System.out.println("works13!");
          pop(line, inputCode, getByteNr(lineTokens[0]));
          break;

        case 14:
          System.out.println("works14!");
          newObject(line, lineTokens[2], lineTokens[5], getByteNr(lineTokens[0]));
          break;

        case 15:
          System.out.println("works15!");
          dup(line,getByteNr(lineTokens[0]));
          break;

        case 16:
          System.out.println("works16!");
          ldc(line,getByteNr(lineTokens[0]),lineTokens[2], lineTokens[4],lineTokens[5]);
          break;

        case 17:
          System.out.println("works17!");
          ifThenJump(line, lineTokens[1], lineTokens[1], getByteNr(lineTokens[0]));
          break;

        case 18:
          System.out.println("works18!");
          goTo(line, getByteNr(lineTokens[0]), Integer.valueOf(lineTokens[2]));
          break;

        case 19:
          System.out.println("works19!");
          cmp(line, lineTokens[1], getByteNr(lineTokens[0]));
          break;

        case 20:
          System.out.println("works20!");
          // TODO
          // getstatic, getfield
          //Won't implement
          break;

        case 21:
          System.out.println("works21!");
          // TODO
          // invokedynamic, invokespecial, invokestatic
          //Won't implement
          break;

        case 22:
          System.out.println("works22!");
          // TODO
          // instanceof
          // Won't implement
          break;

        case 23:
          System.out.println("works23!");
          ifcmp(line, getByteNr(lineTokens[0]), Integer.valueOf(lineTokens[2]));
          // if_acmpeq,if_acmpne,if_icmpeq,if_icmpge,if_icmpgt,if_icmple,if_icmplt,if_icmpne
          break;
        case 24:
          returnOp(line, getByteNr(lineTokens[0]));
          break;
      }
    }
  }

  private static void dup(String line, Integer byteNr) {
    
    State state=createState(beautifyText(line,"duplicate the value(s) on top of the stack."), byteNr);
    if (line.split(" ")[1].matches("dup2")){
      StoredValue initialValue1=state.popStack();
      StoredValue initialValue2=state.popStack();
      StoredValue newValue1=new StoredValue(initialValue1.getValue(), initialValue1.getType());
      StoredValue newValue2=new StoredValue(initialValue2.getValue(), initialValue2.getType());
      state.addStack(initialValue2);
      state.addStack(initialValue1);
      state.addStack(newValue2);
      state.addStack(newValue1);
    }else{
      StoredValue initialValue1=state.popStack();
      StoredValue newValue1=new StoredValue(initialValue1.getValue(), initialValue1.getType());
      state.addStack(initialValue1);
      state.addStack(newValue1);
    }
    Main.stateQueue.add(state);
    
  }
  private static void pop(String line, String input, int byteNr) {
    State state = createState(beautifyText(line, "discard the top value(s) on the stack."), byteNr);
    if (input.equals("pop")) {
      state.popStack();
    } else {
      state.popStack();
      state.popStack();
    }
    Main.stateQueue.add(state);
  }

  private static void ldc(String line, Integer byteNr, String index, String rawType,String rawValue) {
    State state = createState(beautifyText(line,"push a constant "+index+" from a constant pool onto the stack"),byteNr);
    String type;
    if (rawType.charAt(0)>='a' && rawType.charAt(0)<='z'){
      type= rawType.substring(0,1);
    }else{
      type=rawType;
    }
    if (type.matches("d|l")){
      StoredValue storedValue=new StoredValue(rawValue.substring(0,rawValue.length()-1),type);
      state.addStack(storedValue);
      state.addStack(storedValue);
    }else{
      StoredValue storedValue=new StoredValue(rawValue,type);
      state.addStack(storedValue);
    }
    Main.stateQueue.add(state);
    
    
  }

  private static void goTo(String line, Integer byteNr, Integer goTo) {
    State state = createState(beautifyText(line, "goes to another instruction at " + goTo), byteNr);
    Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
    Main.stateQueue.add(state);

  }

  private static void returnOp(String line, int byteNr) {
    State state =
        createState(beautifyText(line, "return a value from the method,unless void"), byteNr);
    state.popStack();
    try {
      state.popStack();
    } finally {
      Main.stateQueue.add(state);
      Main.reachedReturn = true;
    }
  }

  private static void ifcmp(String line, Integer byteNr, int goTo) {
    char type = line.split(" ")[1].charAt(0);
    String explType = (type == 'i' ? "ints" : "object references");
    State state = createState(line, byteNr);
    String cmpMethod = line.substring(line.indexOf("cmp") + 3, line.indexOf("cmp") + 5);
    switch (cmpMethod) {
      case "eq":
        state.setLine(beautifyText(line, "If" + explType + "are equal, branch to instruction at "
            + goTo));
        if (type == 'i') {
          if (Integer.valueOf(state.popStack().getValue()) == Integer.valueOf(state.popStack()
              .getValue())) {
            Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
          }
        } else {
          if (state.popStack() == state.popStack()) {
            Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
          }
        }
        Main.stateQueue.add(state);
        break;
      case "ne":
        state.setLine(beautifyText(line, "If" + explType
            + "are not equal, branch to instruction at " + goTo));
        if (type == 'i') {
          if (Integer.valueOf(state.popStack().getValue()) != Integer.valueOf(state.popStack()
              .getValue())) {
            Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
          }
        } else {
          if (state.popStack() != state.popStack()) {
            Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
          }
        }
        Main.stateQueue.add(state);
        break;
      case "lt":
        state.setLine(beautifyText(line, "if value1 is less than value2, branch to instruction at"
            + goTo));
        if (Integer.valueOf(state.popStack().getValue()) > Integer.valueOf(state.popStack()
            .getValue())) {
          Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
        }
        Main.stateQueue.add(state);
        break;
      case "ge":
        state.setLine(beautifyText(line,
            "if value1 is greater or equal than value2, branch to instruction at" + goTo));
        if (Integer.valueOf(state.popStack().getValue()) <= Integer.valueOf(state.popStack()
            .getValue())) {
          Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
        }
        Main.stateQueue.add(state);
        break;
      case "gt":
        state.setLine(beautifyText(line,
            "if value1 is greater than value2, branch to instruction at" + goTo));
        if (Integer.valueOf(state.popStack().getValue()) < Integer.valueOf(state.popStack()
            .getValue())) {
          Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
        }
        Main.stateQueue.add(state);
        break;
      case "le":
        state.setLine(beautifyText(line,
            "if value1 is less or equal than value2, branch to instruction at" + goTo));
        if (Integer.valueOf(state.popStack().getValue()) >= Integer.valueOf(state.popStack()
            .getValue())) {
          Main.currentByteNrIndex = Main.input.byteNrList.indexOf(goTo);
        }
        Main.stateQueue.add(state);
        break;
    }
  }


  private static void load(String line, Integer byteNr) {
    load(line, byteNr, Integer.parseInt(String.valueOf((line.charAt(line.length() - 1)))));
  }


  private static void load(String line, Integer byteNr, int index) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(
            beautifyText(line, "load a type " + type + " value from local variable #" + index),
            byteNr);
    state.addStack(state.getLocalVariables().get(index));
    if (isLD(index, state)) {
      state.addStack(state.getLocalVariables().get(index));
    }
    Main.stateQueue.add(state);
  }


  private static boolean isLD(int index, State state) {
    return state.getLocalVariables().get(index).getType().matches("l|d|double|long");
  }


  private static void store(String line, Integer byteNr) {

    store(line, byteNr, Integer.parseInt(String.valueOf((line.charAt(line.length() - 1)))));

  }

  private static void store(String line, Integer byteNr, int index) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(
            beautifyText(line, "store a type " + type
                + " value (popped from stack) into a local variable #" + index), byteNr);
    state.setLocalVariableElement(index, state.popStack());
    if (isLD(index, state)) {
      state.setLocalVariableElement(index + 1, state.popStack());
    }
    Main.stateQueue.add(state);
  }


  private static void iinc(String line, int byteNr, int index, String constant) {
    State state =
        createState(
            beautifyText(line, "Increment local variable #" + index + " by signed byte const "
                + constant), byteNr);
    StoredValue value =
        new StoredValue(state.getLocalVariables().get(index).getValue(), state.getLocalVariables()
            .get(index).getType());
    value.addToValue(constant);

    state.setLocalVariableElement(index, value);
    Main.stateQueue.add(state);
  }

  private static void xconst_y(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    String value = (line.charAt(line.indexOf('_')+1)=='m')?"-1":line.split("")[line.split("").length - 1];
    State state =
        createState(
            beautifyText(line, "load the type " + type + " value " + value + " onto the stack"),
            byteNr);
    StoredValue storedValue = new StoredValue(value, String.valueOf(type));

    state.getOperandStack().push(storedValue);
    if (type == 'l' || type == 'd') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void add(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Add two values of the following type: " + type), byteNr);
    String sum = "";
    String operand2 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();
      state.popStack();
    }
    String operand1 = state.popStack().getValue();
    if (operand1.matches("-?\\d+(\\.\\d+)?") && operand2.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sum = Integer.toString(Integer.valueOf(operand1) + Integer.valueOf(operand2));
      } else if (type == 'd') {
        sum = Double.toString(Double.valueOf(operand1) + Double.valueOf(operand2));
      } else if (type == 'f') {
        sum = Float.toString(Float.valueOf(operand1) + Float.valueOf(operand2));
      } else if (type == 'l') {

        sum = Long.toString(Long.valueOf(operand1) + Long.valueOf(operand2));

      }
      storedValue = new StoredValue(String.valueOf(sum), String.valueOf(type));
    } else {
      if (operand1.length() >= operand2.length()) {
        storedValue = new StoredValue(operand1, String.valueOf(type));
        storedValue.addToValue(operand2);
      } else {
        storedValue = new StoredValue(operand2, String.valueOf(type));
        storedValue.addToValue(operand1);
      }

    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void sub(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Subtract values of the following type: " + type), byteNr);
    String sub = "";
    String operand2 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();
      state.popStack();
    }
    String operand1 = state.popStack().getValue();
    if (operand1.matches("-?\\d+(\\.\\d+)?") && operand2.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sub = Integer.toString(Integer.valueOf(operand1) - Integer.valueOf(operand2));
      } else if (type == 'd') {
        sub = Double.toString(Double.valueOf(operand1) - Double.valueOf(operand2));
      } else if (type == 'f') {
        sub = Float.toString(Float.valueOf(operand1) - Float.valueOf(operand2));
      } else if (type == 'l') {

        sub = Long.toString(Long.valueOf(operand1) - Long.valueOf(operand2));

      }
      storedValue = new StoredValue(String.valueOf(sub), String.valueOf(type));
    } else {
        storedValue = new StoredValue(operand2, String.valueOf(type));
        storedValue.subFromValue(operand1);

    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void div(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Divide values of the following type: " + type), byteNr);
    String sub = "";
    String operand2 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();
      state.popStack();
    }
    String operand1 = state.popStack().getValue();
    if (operand1.matches("-?\\d+(\\.\\d+)?") && operand2.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sub = Integer.toString(Integer.valueOf(operand1) / Integer.valueOf(operand2));
      } else if (type == 'd') {
        sub = Double.toString(Double.valueOf(operand1) / Double.valueOf(operand2));
      } else if (type == 'f') {
        sub = Float.toString(Float.valueOf(operand1) / Float.valueOf(operand2));
      } else if (type == 'l') {

        sub = Long.toString(Long.valueOf(operand1) / Long.valueOf(operand2));

      }
      storedValue = new StoredValue(String.valueOf(sub), String.valueOf(type));
    } else {
        storedValue = new StoredValue(operand2, String.valueOf(type));
        storedValue.divideValue(operand1);

    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void mul(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Multiply two values of the following type: " + type), byteNr);
    String sum = "";
    String operand2 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();
      state.popStack();
    }
    String operand1 = state.popStack().getValue();
    if (operand1.matches("-?\\d+(\\.\\d+)?") && operand2.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sum = Integer.toString(Integer.valueOf(operand1) * Integer.valueOf(operand2));
      } else if (type == 'd') {
        sum = Double.toString(Double.valueOf(operand1) * Double.valueOf(operand2));
      } else if (type == 'f') {
        sum = Float.toString(Float.valueOf(operand1) * Float.valueOf(operand2));
      } else if (type == 'l') {

        sum = Long.toString(Long.valueOf(operand1) * Long.valueOf(operand2));

      }
      storedValue = new StoredValue(String.valueOf(sum), String.valueOf(type));
    } else {
      if (operand1.length() >= operand2.length()) {
        storedValue = new StoredValue(operand1, String.valueOf(type));
        storedValue.multiplyValue(operand2);
      } else {
        storedValue = new StoredValue(operand2, String.valueOf(type));
        storedValue.multiplyValue(operand1);
      }

    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void neg(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Negate a value of the following type: " + type), byteNr);
    String sum = "";
    String operand1 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();

    }
    if (operand1.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sum = Integer.toString(Integer.valueOf(operand1) *-1);
      } else if (type == 'd') {
        sum = Double.toString(Double.valueOf(operand1) * -1);
      } else if (type == 'f') {
        sum = Float.toString(Float.valueOf(operand1) * -1);
      } else if (type == 'l') {

        sum = Long.toString(Long.valueOf(operand1) * -1);

      }
      storedValue = new StoredValue(String.valueOf(sum), String.valueOf(type));
    } else {
        storedValue = new StoredValue(operand1, String.valueOf(type));
        storedValue.multiplyValue("-1");


    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void rem(String line, int byteNr) {
    char type = line.split(" ")[1].charAt(0);
    State state =
        createState(beautifyText(line, "Divide values of the following type: " + type), byteNr);
    String sub = "";
    String operand2 = state.popStack().getValue();
    StoredValue storedValue;
    if (type == 'l' || type == 'd') {
      state.popStack();
      state.popStack();
    }
    String operand1 = state.popStack().getValue();
    if (operand1.matches("-?\\d+(\\.\\d+)?") && operand2.matches("-?\\d+(\\.\\d+)?")) {
      if (type == 'i') {
        sub = Integer.toString(Integer.valueOf(operand1) % Integer.valueOf(operand2));
      } else if (type == 'd') {
        sub = Double.toString(Double.valueOf(operand1) % Double.valueOf(operand2));
      } else if (type == 'f') {
        sub = Float.toString(Float.valueOf(operand1) % Float.valueOf(operand2));
      } else if (type == 'l') {

        sub = Long.toString(Long.valueOf(operand1) % Long.valueOf(operand2));

      }
      storedValue = new StoredValue(String.valueOf(sub), String.valueOf(type));
    } else {
        storedValue = new StoredValue(operand2, String.valueOf(type));
        storedValue.remValue(operand1);

    }
    state.getOperandStack().push(storedValue);
    if (type == 'd' || type == 'l') {
      state.getOperandStack().push(storedValue);
    }
    Main.stateQueue.add(state);
  }

  private static void newObject(String line, String index, String type, int byteNr) {
    type = type.split("/")[type.split("/").length - 1];
    State state =
        createState(
            beautifyText(line, "create new object of type " + type
                + " by class reference in constant pool " + index), byteNr);
    state.addStack(new StoredValue("objectref", type));
    Main.stateQueue.add(state);
  }

  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // OR YOLO? :D
  private static void cmp(String line, String type, int byteNr) {
    State state =
        createState(
            beautifyText(line, "compare two values of the following type: " + type.charAt(0)),
            byteNr);
    StoredValue storedState = new StoredValue("", "");
    double d1 = 0.0;
    double d2 = 0.0;
    if (type.equals("lcmp") || type.equals("dcmpl") || type.equals("dcmpg")) {
      d1 = Double.valueOf(state.popStack().getValue());
      state.popStack();
      state.popStack();
      d2 = Double.valueOf(state.popStack().getValue());
    } else if (type.equals("fcmpl") || type.equals("fcmpg")) {
      d1 = Double.valueOf(state.popStack().getValue());
      d2 = Double.valueOf(state.popStack().getValue());
    }
    if (d1 == d2) {
      storedState = new StoredValue("0", "i");
    } else if (d1 == d2) {
      storedState = new StoredValue("-1", "i");
    } else if (d2 < d1) {
      storedState = new StoredValue("1", "i");
    } else if (Double.isNaN(d1) || Double.isNaN(d2)) {
      if (type.equals("dcmpl")) {
        storedState = new StoredValue("-1", "i");
      } else {
        storedState = new StoredValue("1", "i");
      }
    }
    state.getOperandStack().push(storedState);
    Main.stateQueue.add(state);
  }

  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // Needs testing!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  // OR YOLO? :D
  private static void ifThenJump(String line, String type, String toWhere, int byteNr) {
    State state =
        createState(beautifyText(line, "if value is <0/0/>0 then jump to " + toWhere), byteNr);
    int intValue = Integer.valueOf(state.popStack().getValue());
    if (type.equals("ifeq")) {
      if (intValue == 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    } else if (type.equals("ifle")) {
      if (intValue <= 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    } else if (type.equals("ifne")) {
      if (intValue != 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    } else if (type.equals("ifge")) {
      if (intValue >= 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    } else if (type.equals("ifgt")) {
      if (intValue > 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    } else if (type.equals("iflt")) {
      if (intValue < 0) {
        Main.currentByteNrIndex = Main.input.byteNrList.indexOf(Integer.parseInt(toWhere));
      }
    }
    Main.stateQueue.add(state);
  }

  @SuppressWarnings("unchecked")
  private static State createState(String line, int byteNr) {
    return new State(line, byteNr, new ArrayList<StoredValue>(Main.stateQueue.get(
        Main.currentStateNr - 1).getLocalVariables()), (Stack<StoredValue>) Main.stateQueue
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
