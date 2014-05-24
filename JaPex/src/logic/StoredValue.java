package logic;

public class StoredValue {
  private String value;
  private String type;
  
  public StoredValue(String value,String type){
    this.value=value;
    this.type=type;
  }
  
  public String getValue() {
    return value;
  }

  public String getType() {
    return type;
  }

  public String toString(){
    return value + " (type: "+type+")";
  }
  
  //using reveresed polish notation
  public void addToValue(int constant){
    this.value+=(constant>=0?" "+constant+" +":" "+constant+" -");
  }
  public void multiplyValue(int constant){
    this.value+=" "+constant+" *";
  }
  public void divideValue(int constant){
    this.value+=" "+constant+" /";
  }

}
