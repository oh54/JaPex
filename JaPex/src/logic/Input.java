package logic;

public class Input {
	private String inputString;
	private String[] inputTokens;
	
	public Input(String inputString) {
		super();
		this.inputString = inputString;
		this.inputTokens = getInputTokens(inputString);
	}
	
	public String[] getInputTokens(String inputString){
		String[] spaceTokens = inputString.split("\n");
		for (int i = 0; i < spaceTokens.length; i++){
			spaceTokens[i] = spaceTokens[i].trim();
		}
		
		return spaceTokens;
		
		
	}
	public String getLine(int currentState){
		String line=inputTokens[currentState];
		return line;
	}
	public String previousLine(int currentState){
		if (currentState>0){
			String line=inputTokens[currentState-1];
			return line;
		}
		return inputTokens[currentState];
	}

}
