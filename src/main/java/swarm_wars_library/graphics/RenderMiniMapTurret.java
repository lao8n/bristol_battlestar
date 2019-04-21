package swarm_wars_library.graphics;

import processing.core.PApplet;
import swarm_wars_library.physics.Vector2D;

public class RenderMiniMapTurret extends RenderMiniMapObject{

  private int scale = 5;

  public RenderMiniMapTurret(PApplet sketch){
    super(sketch);
  }

  public boolean checkInLineOfSight(Vector2D botMapLocation, 
    Vector2D turretMapLocation, double botMapLineOfSight){

    if(Vector2D.sub(botMapLocation, turretMapLocation).mag()
       < botMapLineOfSight){
      return true;
    }
    else {
      return false;
    }
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
