package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class State implements Cloneable {
  private String line;
  private int byteNr;
  private List<StoredValue> localVariables = new ArrayList<StoredValue>();
  private Stack<StoredValue> operandStack = new Stack<StoredValue>();

  public State(String line, int byteNr, List<StoredValue> localVariables, Stack<StoredValue> operandStack) {
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

  void setLocalVariableElement(int index, StoredValue value) {
    if(index<localVariables.size()){
    this.localVariables.set(index, value);}
    else{
      localVariables.add(value);
    }
  }

  void addStack(StoredValue elem) {
    this.operandStack.add(elem);
  }

  StoredValue popStack() {
    return this.operandStack.pop();
  }

  public Stack<StoredValue> getOperandStack() {
    return operandStack;
  }

  public List<StoredValue> getLocalVariables() {
    return localVariables;
  }

  public String toString() {
    return "Line: " + line + "\noperandStack:" + operandStack.toString() + "\nlocalVariables: "
        + Arrays.toString(localVariables.toArray()) + "\nbyteNr: " + byteNr;
  }

}
