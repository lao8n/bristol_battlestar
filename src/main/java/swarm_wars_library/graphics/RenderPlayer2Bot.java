package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;

public class RenderPlayer2Bot extends AbstractRenderMapObject{

  private float scale = (float) Map.getInstance().getBotScale();
  private PImage droneSingle = this.sketch.loadImage("resources/images/droneSinglePlayer2.png");

  public RenderPlayer2Bot(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.imageMode(0);

    // Draw sprite : rotate screen (pop/push matrix)
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(),  (float) this.objectRenderLocation.getY());
    this.sketch.imageMode(PConstants.CENTER);
    //this.sketch.image(sprites[currentSprite], 
    this.sketch.image(droneSingle, 
                                  //(float) this.objectRenderLocation.getX(), 
                                  //(float) this.objectRenderLocation.getY(), 
                                  0, 0,
                                  this.scale * 4, 
                                  this.scale * 4); 
                
    this.sketch.popMatrix();
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