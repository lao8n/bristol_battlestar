package swarm_wars_library.graphics;

import processing.core.PApplet;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;


public class RenderPlayer2Score{

  private PApplet sketch;

  public RenderPlayer2Score(PApplet sketch){
    this.sketch = sketch;
  }

  public void update(int score){
    this.sketch.fill(255, 22, 65);
    this.sketch.textSize(30);
    this.sketch.textAlign(LEFT, TOP);
    this.sketch.text("SCORE: " + score, 5, 55);
  }
}