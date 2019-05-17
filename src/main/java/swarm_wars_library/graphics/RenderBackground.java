package swarm_wars_library.graphics;

import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PConstants.CORNER;


public class RenderBackground extends AbstractRenderMapObject{

  PImage backgroundImage;

  public RenderBackground(PApplet sketch){
    super(sketch);
    this.backgroundImage = sketch.loadImage("resources/images/gameMap.png");
  }

  @Override 
  public void renderMapObject(){
    this.sketch.imageMode(CORNER);
    PImage croppedImage = this.backgroundImage.get(
      (int) (this.objectMapLocation.getX() - this.objectRenderLocation.getX()),
      (int) (this.objectMapLocation.getY() - this.objectRenderLocation.getY()),
      this.sketch.width, 
      this.sketch.height);
    this.sketch.image(croppedImage,
                      0,
                      0,
                      this.sketch.width,
                      this.sketch.height);
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    // DO NOTHING
  }
}