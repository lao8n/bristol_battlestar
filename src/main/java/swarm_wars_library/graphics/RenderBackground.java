package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderBackground{

  private PApplet sketch;

  public RenderBackground(PApplet sketch){
    this.sketch = sketch;
  }

  public void update(){
    this.sketch.background(0, 0, 0);
  }
}