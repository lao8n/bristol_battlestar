package swarm_wars_library.graphics;

import processing.core.PApplet;

import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class RenderUIMiniMapBot extends AbstractRenderMiniMapObject{

  private int scale = 2;
  protected int botMapLineOfSight = 200;
  protected int botRenderLineOfSight;

  public RenderUIMiniMapBot(PApplet sketch, int miniMapDim, int miniMapOffset){
    super(sketch);
    this.miniMapDim = miniMapDim;
    this.miniMapOffset = miniMapOffset;
    this.botRenderLineOfSight = (int) this.botMapLineOfSight 
      * (int) this.miniMapDim / Map.getInstance().getMapWidth();
  }

  @Override 
  public void renderMiniMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(21, 0, 255, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse(
      this.sketch.width - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getX(),
      this.sketch.height - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderLocation.getY(),
      (float) this.scale + this.botRenderLineOfSight / 10, 
      (float) this.scale + this.botRenderLineOfSight / 10);  
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

  public boolean checkInLineOfSight(Vector2D botMapLocation, 
    Vector2D otherMapLocation, double botMapLineOfSight){

    if(Vector2D.sub(botMapLocation, otherMapLocation).mag()
      < botMapLineOfSight){
      return true;
    }
    else {
      return false;
    }
  }
}
