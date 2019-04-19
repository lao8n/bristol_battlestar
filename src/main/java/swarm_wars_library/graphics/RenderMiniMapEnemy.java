package swarm_wars_library.graphics;

import processing.core.PApplet;
import swarm_wars_library.engine.Vector2D;

public class RenderMiniMapEnemy extends RenderMiniMapObject{

  private int scale = 5;

  public RenderMiniMapEnemy(PApplet sketch){
    super(sketch);
  }

  public boolean checkInLineOfSight(Vector2D botMapPosition, 
    Vector2D enemyMapPosition, double botMapLineOfSight){

    if(Vector2D.sub(botMapPosition, enemyMapPosition).mag()
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
        + (float) this.objectRenderPosition.getX(),
      this.sketch.height - this.miniMapDim - this.miniMapOffset
        + (float) this.objectRenderPosition.getY(),
      (float) this.scale, 
      (float) this.scale);  
  }
}
