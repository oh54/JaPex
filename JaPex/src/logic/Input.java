package logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Input {
  private String inputString;
  List<Integer> byteNrList = new LinkedList<Integer>();
  Map<Integer, String> byteNrToLine = new HashMap<Integer, String>();

  public Input(String inputString) {
    super();
    this.inputString = inputString;
    initializeInputTokens(inputString);
  }

  public void initializeInputTokens(String inputString) {
    String[] spaceTokens = inputString.split("\n");
    byteNrToLine.put(-2, spaceTokens[0].trim());
    byteNrList.add(-2);
    byteNrToLine.put(-1, spaceTokens[1].trim());
    byteNrList.add(-1);
    for (int i = 2; i < spaceTokens.length; i++) {
      int byteNr = Integer.valueOf(spaceTokens[i].substring(0, spaceTokens[i].indexOf(':')).trim());
      byteNrList.add(byteNr);
      byteNrToLine.put(byteNr, spaceTokens[i].trim());
    }


  }


}
