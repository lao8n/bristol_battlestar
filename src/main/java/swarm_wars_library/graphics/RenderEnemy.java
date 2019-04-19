package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PApplet;

public class RenderEnemy extends RenderMapObject{

  private float scale = (float) Map.getInstance().getEnemyScale();

  public RenderEnemy(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(168, 5, 78); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale, 
                        this.scale); 
    // Dark inside
    this.sketch.fill(81, 4, 37, 90); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale - 2, 
                        this.scale - 2);     
    this.sketch.fill(81, 4, 37, 85); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale - 3, 
                        this.scale - 3);      
    // Add glow
    this.sketch.fill(239, 2, 57, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale + 2, 
                        this.scale + 2);     
    this.sketch.fill(239, 2, 57, 50); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale + 3, 
                        this.scale + 3);       
  }
}