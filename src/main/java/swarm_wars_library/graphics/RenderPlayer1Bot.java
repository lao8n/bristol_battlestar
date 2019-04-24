package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;

import processing.core.PApplet;

public class RenderPlayer1Bot extends RenderMapObject{

  private float scale = (float) Map.getInstance().getBotScale();

  public RenderPlayer1Bot(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){

    this.sketch.noStroke();
    this.sketch.fill(50, 50, 255); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale, 
                        this.scale); 
    // Dark Inside 
    this.sketch.fill(17, 17, 135); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale - 2, 
                        this.scale - 2); 
    // Add Glow
    this.sketch.fill(21, 0, 255, 60); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        this.scale + 5, 
                        this.scale + 5); 
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    // this.sketch.noStroke();
    // this.sketch.fill(0, 0, 0, 100); 
    // this.sketch.ellipseMode(2);
    // this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
    //                     (float) this.objectRenderLocation.getY(), 
    //                     this.scale, 
    //                     this.scale); 
  }
}