package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import processing.core.PApplet;

public class RenderTurret extends RenderMapObject{

  private float scale = (float) Map.getInstance().getTurretScale();

  public RenderTurret(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.fill(168, 5, 78); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale, 
                        this.scale); 
    // Dark inside
    this.sketch.fill(81, 4, 37, 90); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale - 2, 
                        this.scale - 2);     
    this.sketch.fill(81, 4, 37, 85); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale - 3, 
                        this.scale - 3);      
    // Add glow
    this.sketch.fill(239, 2, 57, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale + 2, 
                        this.scale + 2);     
    this.sketch.fill(239, 2, 57, 50); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale + 3, 
                        this.scale + 3);       
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    this.sketch.noStroke();
    this.sketch.fill(229, 11, 109, alpha); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        (float) 6, 
                        (float) 6); 
  }

  public void updateVoid(Vector2D objectMapLocation, 
    Vector2D viewCentreMapLocation){
    this.setObjectRenderLocation(objectMapLocation, viewCentreMapLocation);
    this.renderMapObjectVoid();
  }

  public void renderMapObjectVoid(){
    this.sketch.noStroke();
    this.sketch.fill(0, 0, 0); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        (float) this.scale, 
                        (float) this.scale); 
  }
}