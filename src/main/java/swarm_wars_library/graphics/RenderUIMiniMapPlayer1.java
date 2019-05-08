package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderUIMiniMapPlayer1 extends AbstractRenderMiniMapObject{

  private int scale = 7;

  public RenderUIMiniMapPlayer1(PApplet sketch){
    super(sketch);
    this.miniMapDim = (int) (sketch.height / 2.2);
    this.miniMapOffset = 20;
  }

  @Override 
  public void renderMiniMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(70, 102, 255);
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(
      this.sketch.width - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getX(),
      this.sketch.height - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getY(),
      (float) this.scale, 
      (float) this.scale); 
    this.sketch.ellipseMode(3);
  }
}
