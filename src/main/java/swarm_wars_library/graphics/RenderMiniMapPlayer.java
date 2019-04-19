package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderMiniMapPlayer extends RenderMiniMapObject{

  private int scale = 5;

  public RenderMiniMapPlayer(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMiniMapObject(){

    this.sketch.noStroke();
    this.sketch.fill(70, 102, 255);
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(
      this.sketch.width - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderPosition.getX(),
      this.sketch.height - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderPosition.getY(),
      (float) this.scale, 
      (float) this.scale);  
  }
}
