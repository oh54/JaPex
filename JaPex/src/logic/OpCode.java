package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OpCode {
	public static void toMatch(String line) {
		if (Main.currentStateNr == 0) {
			String[] parameters = line.substring(line.indexOf('(') + 1,
					line.indexOf(')')).split(",");
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
			Main.stateQueue.add(new State(line,-1,
					localVariables, new Stack<String>()));

		} else if (Main.currentStateNr == 1) {
			Main.stateQueue.add(Main.stateQueue.get(0));
			Main.stateQueue.get(1).setLine(line);
		} else {
			String[] regexArray = new String[] { "iinc" };

			String[] lineTokens = line.split("( )+");

			String inputCode = lineTokens[1];
			int matchingIndex = -1;

			for (int i = 0; i < regexArray.length; i++) {
				if (regexArray[i].matches(inputCode)) {

					matchingIndex = i;
					break;
				}
			}

			switch (matchingIndex) {
			case 0:
				iinc(line,Integer.valueOf(lineTokens[0].substring(0,
						lineTokens[0].length() - 1)),
						Integer.valueOf(lineTokens[2].substring(0,
								lineTokens[0].length() - 1)),
						lineTokens[3]);
			}

		}

	}

	private static void iinc(String line, int byteNr, int index, String constant) {
		State state = new State(line,byteNr, Main.stateQueue
				.get(Main.currentStateNr-1).getLocalVariables(), Main.stateQueue
				.get(Main.currentStateNr-1).getOperandStack());
		
		state.setLocalVariableElement(index,state.getLocalVariables().get(index)+" + "+String.valueOf(constant));
		Main.stateQueue.add(state);
		
		

	}
}
