package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderPlayerHealth{

  private PApplet sketch;
  private float healthBarLength = 100;
  private float healthBarOffset = 15;

  public RenderPlayerHealth(PApplet sketch){
    this.sketch = sketch;
  }

  public void update(int health){
    //draw border
    this.sketch.rectMode(0);
    this.sketch.stroke(20, 100, 0);
    this.sketch.fill(0, 72, 150);
    this.sketch.rect(
      sketch.width - this.healthBarLength - this.healthBarOffset, 
      this.healthBarOffset, 
      this.healthBarLength, 
      30);
    //draw health
    this.sketch.stroke(25, 25, 76);
    this.sketch.fill(31, 126, 226);
    this.sketch.rect(
      sketch.width - this.healthBarLength - this.healthBarOffset, 
      this.healthBarOffset,
      health, 
      30
    );
  }
}