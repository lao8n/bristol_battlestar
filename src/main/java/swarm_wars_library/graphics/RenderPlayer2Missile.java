package swarm_wars_library.graphics;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RenderPlayer2Missile extends AbstractRenderMapObject{

    private float scale;
    // sprite information
    //private PImage shipSprite = this.sketch.loadImage("resources/images/shipSprite.png");
    private PImage shipSprite = this.sketch.loadImage("resources/images/missile.png");
    private double heading=0;

    // for tracking ship trail
    private ArrayList<Vector2D> shipLocList = new ArrayList<Vector2D>();
    int maxRenderCount = 10;

    // animated sprite infor
    private PImage[] sprites;
    private final int spriteX = 4;
    private final int spriteY = 1;
    private final int totalSprites = spriteX * spriteY;
    private int currentSprite;
    private int spriteW;
    private int spriteH;
    private int index = 0;

    public RenderPlayer2Missile(PApplet sketch) {
        super(sketch);
        this.scale = (float) Map.getInstance().getPlayerScale();

        // animated sprite setp
        sprites = new PImage[totalSprites];
        spriteW = shipSprite.width / spriteX;
        spriteH = shipSprite.height / spriteY;
        currentSprite = ThreadLocalRandom.current().nextInt(0, totalSprites);
        index = 0;
        for (int x = 0; x < spriteX; x++) {
            for (int y = 0; y < spriteY; y++) {
                sprites[index] = shipSprite.get(x * spriteW, y * spriteH, spriteW, spriteH);
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
        this.sketch.imageMode(0); //need to figure out what codes for CENTER

        this.sketch.pushMatrix();
        this.sketch.translate((float) this.objectRenderLocation.getX(), (float) this.objectRenderLocation.getY());
        this.sketch.rotate((float)(this.heading+Math.PI / 2));
        this.sketch.imageMode(PConstants.CENTER);


        // Draw ship
        this.sketch.tint(255, 255);
        this.sketch.image(shipSprite,
                0, 0,
                30,
                90);
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
