package swarm_wars_library.graphics;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.util.concurrent.*;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;

public class RenderPlus10 extends AbstractRenderMapObject{

  private float scale;
  // sprite information
  private PImage plus10 = this.sketch.loadImage("resources/images/plus10Points.png");
  private int scaleFactor = 0; // for growing/shrinking 
  private String currentPlayer; 
  private int playerScore = 0;
  private Vector2D loc = new Vector2D(0, 0); 

  public RenderPlus10(PApplet sketch){
    super(sketch);
    this.scale = (float) Map.getInstance().getPlayerScale();
    if (Map.getInstance().getPlayerId() == 1){
        currentPlayer = "PLAYER1";
    } else {
        currentPlayer = "PLAYER2";
    }
  }

  // @Override 
  public void renderMapObject(){

    // if player got a kill, put on counter, which renders x times 
    int newScore = CommsGlobal.get(currentPlayer).getPacket(0).getScore();
    if (playerScore < newScore){
        playerScore = newScore;

        // turn on draw bonus
        scaleFactor = 200; 
        SoundMixer.playPointsWon();

        loc = new Vector2D(
            Math.random() * this.sketch.width,
            Math.random() * this.sketch.height
        );
    }
    if (scaleFactor > 0){
        scaleFactor-=10; 
    } else if (scaleFactor < 0){
        scaleFactor = 0; 
    }

    // image setup 
    this.sketch.noStroke();
    this.sketch.imageMode(0); 
    this.sketch.pushMatrix();
    this.sketch.translate((float) loc.getX(),  
                          (float) loc.getY());
    //this.sketch.rotate(rotationToApply);
    this.sketch.imageMode(PConstants.CENTER);
    this.sketch.tint(255, 255);
    this.sketch.image(plus10, 
                                  0, 0,
                                  scaleFactor, 
                                  scaleFactor); 
    this.sketch.popMatrix();
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
  }
  
  // for render background to calculate screen position over map
  Vector2D getObjectRenderLocation(){
    return objectRenderLocation;
  }
}