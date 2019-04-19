package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PApplet;

public class RenderEnemyBullet extends RenderMapObject{

  private float scale = (float) Map.getInstance().getBulletScale();

  public RenderEnemyBullet(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(255, 0, 199); //neon pink
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale, 
                        this.scale); 
  }
}