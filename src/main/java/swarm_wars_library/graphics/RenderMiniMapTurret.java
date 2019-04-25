package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderMiniMapTurret extends AbstractRenderMiniMapObject{

  private int scale = 5;

  public RenderMiniMapTurret(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMiniMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(168, 5, 78);
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(
      this.sketch.width - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getX(),
      this.sketch.height - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getY(),
      (float) this.scale, 
      (float) this.scale);  
  }
}
