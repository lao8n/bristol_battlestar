package swarm_wars_library.graphics;

import swarm_wars_library.Images;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.util.concurrent.*;
import java.util.ArrayList;

public class RenderPlayer2Bullet extends AbstractRenderMapObject{

  private float scale = (float) Map.getInstance().getBulletScale();
  private PImage bulletSprite = Images.getInstance().getBullet2Sprite();
  private double heading=0;

  // animated sprite infor
  private PImage[] sprites;
  private final int spriteX = 4;
  private final int spriteY = 1;
  private final int totalSprites = spriteX * spriteY;
  private int currentSprite;
  private int spriteW;
  private int spriteH;
  private int index = 0;

  public RenderPlayer2Bullet(PApplet sketch){
    super(sketch);

    // animated sprite setp
    sprites = new PImage[totalSprites];
    spriteW = bulletSprite.width / spriteX;
    spriteH = bulletSprite.height / spriteY;
    currentSprite = ThreadLocalRandom.current().nextInt(0, totalSprites);
    index = 0;
    for (int x = 0; x < spriteX; x++) {
      for (int y = 0; y < spriteY; y++) {
        sprites[index] = bulletSprite.get(x * spriteW, y * spriteH, spriteW, spriteH);
        index++;
      }
    }
  }

  public void update(Vector2D objectMapLocation,
                     Vector2D viewCentreMapLocation,double heading){
    this.objectMapLocation = objectMapLocation;

    this.setObjectRenderLocation(objectMapLocation, viewCentreMapLocation);
    if(this.objectRenderLocation.getX() >= 0 &&
            this.objectRenderLocation.getX() <= this.sketch.width &&
            this.objectRenderLocation.getY() >= 0 &&
            this.objectRenderLocation.getY() <= this.sketch.height){
      this.heading=heading;
      this.renderMapObject();
    }
  }

  @Override 
  public void renderMapObject(){

  // image setup
    this.sketch.noStroke();
    this.sketch.imageMode(0); 

    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(), (float) this.objectRenderLocation.getY());
    this.sketch.rotate((float)(this.heading+Math.PI / 2));
    this.sketch.imageMode(PConstants.CENTER);
    // Draw bullet
    this.sketch.tint(255, 255);
    this.sketch.image(sprites[currentSprite],
            0, 0,
             10,
             70);
    this.sketch.popMatrix();

    currentSprite++;
    currentSprite %= totalSprites;
  }

  @Override 
  public void renderMapObjectExplosion(int alpha){
    this.sketch.noStroke();
    this.sketch.fill(0, 100, 255, alpha); 
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderLocation.getX(), 
                        (float) this.objectRenderLocation.getY(), 
                        (float) 2, 
                        (float) 2); 
  }
}