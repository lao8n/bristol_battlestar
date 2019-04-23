package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PApplet;

public class RenderPlayerBullet extends RenderMapObject{

  private float scale = (float) Map.getInstance().getBulletScale();

  public RenderPlayerBullet(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(0, 237, 255); //neon blue
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale, 
                        this.scale);   
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    this.sketch.noStroke();
    this.sketch.fill(0, 237, 255, alpha); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        (float) 2, 
                        (float) 2); 
  }
}