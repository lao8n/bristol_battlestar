package swarm_wars_library.graphics;

import processing.core.PApplet;

public class RenderUIMiniMapPlayer1 extends AbstractRenderMiniMapObject{

  private int scale = 7;

  public RenderUIMiniMapPlayer1(PApplet sketch, int miniMapDim, 
    int miniMapOffset){
    super(sketch);
    this.miniMapDim = miniMapDim;
    this.miniMapOffset = miniMapOffset;
  }

  @Override 
  public void renderMiniMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(0, 101, 255);
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
