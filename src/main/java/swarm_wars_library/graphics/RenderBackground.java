package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.graphics.RenderPlayer1;

public class RenderBackground{

  private PApplet sketch;
  private PImage background; 
  private PImage backgroundImage;

  public RenderBackground(PApplet sketch){
    this.sketch = sketch;
    background = sketch.loadImage("resources/images/gameMap.png");
    this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
  }

  public void update(){
    // move around the section? 
    //this.sketch.background(0, 0, 0);
    this.sketch.noStroke();
        // Draw sprite : rotate screen (pop/push matrix)
    //this.sketch.pushMatrix();
    //this.sketch.translate((float) this.objectRenderLocation.getX(),  (float) this.objectRenderLocation.getY());
    this.sketch.imageMode(PConstants.CORNERS);

    // crop 

// grap object 1 
    // player1 info to calculate screen location on map
    //Vector2D mapLoc = CommsGlobal.get("PLAYER1").getPacket(0).getLocation();
    //Vector2D renderLoc = RenderPlayer1.getObjectRenderLocation();

    //Vector2D visibleScreen = Vector2D.sub(mapLoc, renderLoc);

    this.backgroundImage = background.get(0,0,
                                  //visibleScreen.getX(), 
                                  //visibleScreen.getY(), 
                                  sketch.width, sketch.height);

    // draw 
    this.sketch.image(this.backgroundImage, 
                                  //(float) this.objectRenderLocation.getX(), 
                                  //float) this.objectRenderLocation.getY(), 
                                  0, 0,
                                  this.sketch.width, 
                                  this.sketch.height); 
                
    //this.sketch.popMatrix();
  }
}