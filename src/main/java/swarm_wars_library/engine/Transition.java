package swarm_wars_library.engine;

public class Transition {


  CondElement element;
  char conditionalOp;
  int value;

  public Transition() {
    element = CondElement.ENEMYSHIPDIST;
    conditionalOp = '<';
    value = 1;
  }

  public void setElement(CondElement element) {
    this.element = element;
  }
  public void setCondtionalOp(char conditionalOp) {
    // check that it is correct i.e >, <
    this.conditionalOp = conditionalOp;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public CondElement getElement() {
    return element;
  }

  public char getConditionalOp() {
    return conditionalOp;
  }

  public int getValue() {
    return value;
  }
}
