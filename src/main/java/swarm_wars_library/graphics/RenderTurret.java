package swarm_wars_library.graphics;

import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import java.util.concurrent.*;
import swarm_wars_library.sound.SoundMixer;

public class RenderTurret extends AbstractRenderMapObject{
  private float scale = (float) Map.getInstance().getTurretScale();
  private PImage turretSprite = this.sketch.loadImage("resources/images/turretSprite.png");

  // animated sprite infor
  private PImage[] sprites;
  private final int spriteX = 8;
  private final int spriteY = 1;
  private final int totalSprites = spriteX * spriteY;
  private int currentSprite;
  private int spriteW;
  private int spriteH;
  private int index = 0;

  // slow down animations - larger number for slower animation
  private int maxAnimTimer = 7; 
  private int animTimer = maxAnimTimer; 

  public RenderTurret(PApplet sketch){
    super(sketch);

    // animated sprite setp
    sprites = new PImage[totalSprites];
    spriteW = turretSprite.width/spriteX;;
    spriteH = turretSprite.height/spriteY;
    currentSprite = ThreadLocalRandom.current().nextInt( 0 , totalSprites );
    index = 0;
    for (int x = 0; x < spriteX; x++){
      for (int y = 0; y < spriteY; y++) {
        sprites[index] = turretSprite.get(x * spriteW, y * spriteH, spriteW, spriteH);
        index++;
      }
    }
  }

  @Override 
  public void renderMapObject(){
    this.sketch.noStroke();
    this.sketch.imageMode(0);

    // draw turret
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(),  (float) this.objectRenderLocation.getY());
    this.sketch.imageMode(PConstants.CENTER);
    //this.sketch.image(sprites[currentSprite], 
    this.sketch.image(sprites[currentSprite], 
                                  //(float) this.objectRenderLocation.getX(), 
                                  //float) this.objectRenderLocation.getY(), 
                                  0, 0,
                                  this.scale * 3, 
                                  this.scale * 3); 
                
    this.sketch.popMatrix();

    animTimer--;
    if (animTimer < 0){
      currentSprite++;
      currentSprite %= totalSprites;
      animTimer = maxAnimTimer = 10;
    }

    /* OLD CIRCLE 
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
    */   
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