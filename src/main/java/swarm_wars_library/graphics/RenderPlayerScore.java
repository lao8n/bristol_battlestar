package swarm_wars_library.graphics;

import processing.core.PApplet;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;


public class RenderPlayerScore{

  private PApplet sketch;

  public RenderPlayerScore(PApplet sketch){
    this.sketch = sketch;
  }

  public void update(int points){
    this.sketch.fill(0, 101, 255);
    this.sketch.textSize(30);
    this.sketch.textAlign(LEFT, TOP);
    this.sketch.text("POINTS: " + points, 5, 5);
  }
}