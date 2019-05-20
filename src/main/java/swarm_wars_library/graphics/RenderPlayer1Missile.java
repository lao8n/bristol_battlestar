package swarm_wars_library.graphics;

import processing.core.PConstants;
import processing.core.PImage;
import swarm_wars_library.Images;
import swarm_wars_library.map.Map;
import processing.core.PApplet;
import swarm_wars_library.physics.Vector2D;
import java.util.concurrent.ThreadLocalRandom;

public class RenderPlayer1Missile extends AbstractRenderMapObject {

  private float scale;
  // sprite information
  private PImage missileSprite = Images.getInstance().getMissileSprite();
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

  public RenderPlayer1Missile(PApplet sketch) {
    super(sketch);
    this.scale = (float) Map.getInstance().getPlayerScale();

    // animated sprite setp
    sprites = new PImage[totalSprites];
    spriteW = missileSprite.width / spriteX;
    spriteH = missileSprite.height / spriteY;
    currentSprite = ThreadLocalRandom.current().nextInt(0, totalSprites);
    index = 0;
    for (int x = 0; x < spriteX; x++) {
      for (int y = 0; y < spriteY; y++) {
        sprites[index] = missileSprite.get(x * spriteW, y * spriteH, spriteW, spriteH);
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
   public void renderMapObject() {
    // image setup
    this.sketch.noStroke();
    this.sketch.imageMode(0); 

    this.sketch.pushMatrix();
    this.sketch.translate((float) this.objectRenderLocation.getX(), (float) this.objectRenderLocation.getY());
    this.sketch.rotate((float)(this.heading+Math.PI / 2));
    this.sketch.imageMode(PConstants.CENTER);

    // Draw missile
    this.sketch.tint(255, 255);
    this.sketch.image(sprites[currentSprite],
            0, 0,
             60,
             180);
    this.sketch.popMatrix();}

    @Override
    public void renderMapObjectExplosion (int alpha){
      this.sketch.noStroke();
      this.sketch.fill(70, 102, 255, alpha);
      this.sketch.ellipseMode(2);
      this.sketch.ellipse((float) this.objectRenderLocation.getX(),
              (float) this.objectRenderLocation.getY(),
              this.scale,
              this.scale);
    }
  }
