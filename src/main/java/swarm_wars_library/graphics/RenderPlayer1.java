package swarm_wars_library.graphics;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.util.concurrent.*;
import java.util.ArrayList;

public class RenderPlayer1 extends AbstractRenderMapObject{

  private float scale;
  // sprite information
  //private PImage shipSprite = this.sketch.loadImage("resources/images/shipSprite.png");
  private PImage shipSprite = this.sketch.loadImage("resources/images/shipSingle.png");
  //private PImage shipThrust = this.sketch.loadImage("resources/images/shipThrustSingle.png");
  private PImage shipThrustSprite = this.sketch.loadImage("resources/images/shipThrustSprite.png");
  private int shipThrustStrength = 250; 

  // for tracking ship trail
  private ArrayList<Vector2D> shipLocList = new ArrayList<Vector2D>();
  int shipLocListIndex = 0;
  int maxShipLocCount = 20; 
  int maxRenderCount = 10; 
  int renderCount = maxRenderCount; 

  // animated sprite infor
  private PImage[] sprites;
  private final int spriteX = 4;
  private final int spriteY = 1;
  private final int totalSprites = spriteX * spriteY;
  private int currentSprite;
  private int spriteW;
  private int spriteH;
  private int index = 0;

  public RenderPlayer1(PApplet sketch){
    super(sketch);
    this.scale = (float) Map.getInstance().getPlayerScale();

    // animated sprite setp
    sprites = new PImage[totalSprites];
    spriteW = shipThrustSprite.width/spriteX;;
    spriteH = shipThrustSprite.height/spriteY;
    currentSprite = ThreadLocalRandom.current().nextInt( 0 , totalSprites );
    index = 0;
    for (int x = 0; x < spriteX; x++){
      for (int y = 0; y < spriteY; y++) {
        sprites[index] = shipThrustSprite.get(x * spriteW, y * spriteH, spriteW, spriteH);
        index++;
      }
    }
  }

  // @Override 
  public void renderMapObject(){
    // image setup 
    this.sketch.noStroke();
    this.sketch.imageMode(0); //need to figure out what codes for CENTER
    // can tint for player1 / player2 
    //tint(R, G, B, A); 
    //noTint();

    // used to determine if thrust is present
    boolean moveLeft = CommsGlobal.get("PLAYER1").getPacket(0).getMoveLeft();
    boolean moveRight = CommsGlobal.get("PLAYER1").getPacket(0).getMoveRight();
    boolean moveUp = CommsGlobal.get("PLAYER1").getPacket(0).getMoveUp();
    boolean moveDown = CommsGlobal.get("PLAYER1").getPacket(0).getMoveDown();
    double angle = CommsGlobal.get("PLAYER1").getPacket(0).getMotherShipHeading() + Math.PI/2;

    if (moveLeft || moveRight || moveUp || moveDown){
      shipThrustStrength += 10;
      if (shipThrustStrength > 255){
        shipThrustStrength = 255;
      }
    } else {
      shipThrustStrength-=30;
      if (shipThrustStrength < 0){
        shipThrustStrength = 0;
      }
    }

    //currentSprite = 2; 
    /* WEIRD ROTATION on LEFT / RIGHT - changing perspective of ship not reflected in drawing?
    // set correct sprite box for ship movement
    if (moveLeft && !moveRight) {
      currentSprite--;
    } else if (moveRight && !moveLeft) {
      currentSprite++;
    } else {
      currentSprite = 2; // basic ship
    }
    if (currentSprite < 0) {
      currentSprite = 0;
    } else if (currentSprite > spriteX){
      currentSprite = spriteX;
    }*/

    // get direction of heading to rotate sprite
    //Vector2D mouse = new Vector2D(this.sketch.mouseX, this.sketch.mouseY);
    //float rotationToApply = (float) mouse.heading();
    float rotationToApply  = (float) angle;

    // Draw sprite : rotate screen (pop/push matrix)
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(),  (float) this.objectRenderLocation.getY());
    this.sketch.rotate(rotationToApply);
    this.sketch.imageMode(PConstants.CENTER);

    // Add thrust
    if (shipThrustStrength > 0){
      this.sketch.tint(255, shipThrustStrength);
      this.sketch.image(sprites[currentSprite], 
      //this.sketch.image(shipThrust, 
                                  //(float) this.objectRenderLocation.getX(), 
                                  //(float) this.objectRenderLocation.getY(), 
                                  0, 0,
                                  this.scale * 9, 
                                  this.scale * 9); 
          //move the sprite -> use for animations!
      currentSprite++;
      currentSprite %= totalSprites;
    }

    // Draw ship
    this.sketch.tint(255, 255);
    this.sketch.image(shipSprite, 
                                  //(float) this.objectRenderLocation.getX(), 
                                  //(float) this.objectRenderLocation.getY(), 
                                  0, 0,
                                  this.scale * 5, 
                                  this.scale * 5); 
    this.sketch.popMatrix();

    /*
    // SMOKE TRAIL
    if (renderCount < 0){
      this.sketch.tint(255, 255);
      int transparency = 100; 
      for (int i = 0; i < shipLocList.size(); i++){
        // TODO reduce transparency, farther away...
        Vector2D pos = getObjectRenderLocation(shipLocList.get(i), 
            this.objectMapLocation);
        this.sketch.image(shipSprite, 
                                    (float) pos.getX(),
                                    (float) pos.getY(),
                                    this.scale * 5, 
                                    this.scale * 5); 
        transparency-=10; 
      }
      renderCount = maxRenderCount; 
    } else {
      renderCount--;
    }
    // Add ship location to shipLocList for rendering trail
    // convert this.objectRenderLocation
    shipLocList.add(shipLocListIndex++, CommsGlobal.get("PLAYER1").getPacket(0).getLocation());
    shipLocListIndex = shipLocListIndex % maxShipLocCount; 
    */

    /* OLD VERSION
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
    */     
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