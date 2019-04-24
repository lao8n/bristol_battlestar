package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PApplet;

public class RenderPlayer extends RenderMapObject{

  private float scale;

  public RenderPlayer(PApplet sketch){
    super(sketch);
    this.scale = (float) Map.getInstance().getPlayerScale();
  }

  // @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(70, 102, 255); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale, 
                        this.scale); 
    // Dark inside
    this.sketch.fill(17, 8, 117, 50); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale - 2, 
                        this.scale - 2);     
    this.sketch.fill(9, 3, 71); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale - 3, 
                        this.scale - 3);      
    // Add glow
    this.sketch.fill(21, 0, 255, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale + 2, 
                        this.scale + 2);     
    this.sketch.fill(21, 0, 255, 40); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale + 3, 
                        this.scale + 3);       
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    this.sketch.noStroke();
    this.sketch.fill(70, 102, 255, alpha); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale, 
                        this.scale); 
  }
}