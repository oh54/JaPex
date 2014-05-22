package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class State implements Cloneable{
	private String line;
	private int byteNr;
	private Stack<String> operandStack=new Stack<String>();
	private List<String> localVariables=new ArrayList<String>();
	
	public State(String line,int byteNr, List<String> localVariables,Stack<String> operandStack) {
		super();
		this.line=line;
		this.operandStack = operandStack;
		this.localVariables = localVariables;
		this.byteNr= byteNr;
	}
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	void setLocalVariableElement(int index, String value){
		this.localVariables.set(index, value);
	}
	private void addStack(String elem){
		this.operandStack.add(elem);
	}
	private void popStack(){
		this.operandStack.pop();
	}

	public Stack<String> getOperandStack() {
		return operandStack;
	}
	
	public List<String> getLocalVariables() {
		return localVariables;
	}
}
