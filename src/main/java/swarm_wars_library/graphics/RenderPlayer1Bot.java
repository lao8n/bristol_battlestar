package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;
import swarm_wars_library.comms.CommsGlobal;


public class RenderPlayer1Bot extends AbstractRenderMapObject{

  private float scale = (float) Map.getInstance().getBotScale();
  private PImage droneSingle = Images.getInstance().getDroneSingle();
  
  public RenderPlayer1Bot(PApplet sketch){
    super(sketch);
  }

  @Override 
  public void renderMapObject(){

    this.sketch.noStroke();
    this.sketch.imageMode(0);

    // Draw sprite : rotate screen (pop/push matrix)
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(),  
                          (float) this.objectRenderLocation.getY());

    // rotate by heading
    float rotationToApply = (float) (CommsGlobal.get("PLAYER1").getPacket(0).getMotherShipHeading() + Math.PI/2);
    this.sketch.rotate(rotationToApply);

    this.sketch.imageMode(PConstants.CENTER);
    //this.sketch.image(sprites[currentSprite],
    this.sketch.image(droneSingle, 0, 0,
                                  this.scale * 4, 
                                  this.scale * 4); 
                
    this.sketch.popMatrix();
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){

    //SoundMixer.playShipExplosion();

    // this.sketch.noStroke();
    // this.sketch.fill(0, 0, 0, 100); 
    // this.sketch.ellipseMode(2);
    // this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
    //                     (float) this.objectRenderLocation.getY(), 
    //                     this.scale, 
    //                     this.scale); 
  }
}