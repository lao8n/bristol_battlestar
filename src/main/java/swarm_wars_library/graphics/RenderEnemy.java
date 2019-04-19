package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import swarm_wars_library.engine.Vector2D;
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

  @Override 
  public void renderMapObjectExplosion(int alpha){
    this.sketch.noStroke();
    this.sketch.fill(229, 11, 109, alpha); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        (float) 6, 
                        (float) 6); 
  }

  public void updateVoid(Vector2D objectMapPosition, 
    Vector2D viewCentreMapPosition){
    this.setObjectRenderPosition(objectMapPosition, viewCentreMapPosition);
    this.renderMapObjectVoid();
  }

  public void renderMapObjectVoid(){
    this.sketch.noStroke();
    this.sketch.fill(0, 0, 0); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        (float) this.scale, 
                        (float) this.scale); 
  }
}