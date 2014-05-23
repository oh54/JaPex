package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class State implements Cloneable {
  private String line;
  private int byteNr;
  private List<String> localVariables = new ArrayList<String>();
  private Stack<String> operandStack = new Stack<String>();

  public State(String line, int byteNr, List<String> localVariables, Stack<String> operandStack) {
    super();
    this.line = line;
    this.localVariables = localVariables;
    this.operandStack = operandStack;
    this.byteNr = byteNr;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  void setLocalVariableElement(int index, String value) {
    if(index<localVariables.size()){
    this.localVariables.set(index, value);}
    else{
      localVariables.add(value);
    }
  }

  void addStack(String elem) {
    this.operandStack.add(elem);
  }

  String popStack() {
    return this.operandStack.pop();
  }

  public Stack<String> getOperandStack() {
    return operandStack;
  }

  public List<String> getLocalVariables() {
    return localVariables;
  }

  public String toString() {
    return "Line: " + line + "\noperandStack:" + operandStack.toString() + "\nlocalVariables: "
        + Arrays.toString(localVariables.toArray()) + "\nbyteNr: " + byteNr;
  }

}
