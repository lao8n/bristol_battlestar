package swarm_wars_library.graphics;


import processing.core.PApplet;

import swarm_wars_library.map.Map;

public class RenderStar extends AbstractRenderMapObject{

  private float scale = (float) Map.getInstance().getStarScale();

  public RenderStar(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){

    this.sketch.noStroke();
    this.sketch.fill(255, 255, 204); 
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        (float) this.scale, 
                        (float) this.scale); 
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    // DO NOTHING
  }
}