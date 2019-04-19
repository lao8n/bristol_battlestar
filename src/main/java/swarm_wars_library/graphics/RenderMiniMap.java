package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderMiniMap{

  private PApplet sketch;
  private float miniMapDim = 200;
  private float miniMapOffset = 20;

  public RenderMiniMap(PApplet sketch){
    this.sketch = sketch;
  }

  public void update(){
    this.sketch.rectMode(0);
    this.sketch.stroke(225, 225, 225);
    this.sketch.fill(0, 0, 0);
    this.sketch.rect(
      this.sketch.width - this.miniMapDim - 
        this.miniMapOffset - 5 - 1,
      this.sketch.height - this.miniMapDim - 
        this.miniMapOffset - 5 - 1,
      this.miniMapDim + 10 + 1, 
      this.miniMapDim + 10 + 1);
  }
}