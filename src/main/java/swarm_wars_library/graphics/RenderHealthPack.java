package swarm_wars_library.graphics;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import java.util.concurrent.ThreadLocalRandom;
public class RenderHealthPack extends AbstractRenderMapObject{
    // sprite information
    private PImage healthpack = this.sketch.loadImage("resources/images/p3.png");
    private int scale=10;
    public RenderHealthPack(PApplet sketch) {
        super(sketch);
    }
    public void update(Vector2D objectMapLocation,
                       Vector2D viewCentreMapLocation){
        this.objectMapLocation = objectMapLocation;
        this.setObjectRenderLocation(objectMapLocation, viewCentreMapLocation);
        if(this.objectRenderLocation.getX() >= 0 &&
                this.objectRenderLocation.getX() <= this.sketch.width &&
                this.objectRenderLocation.getY() >= 0 &&
                this.objectRenderLocation.getY() <= this.sketch.height){
            this.renderMapObject();
        }
    }


    @Override
    public void renderMapObject() {
        this.sketch.noStroke();
        this.sketch.imageMode(0);

        this.sketch.pushMatrix();
        this.sketch.translate((float) this.objectRenderLocation.getX(), (float) this.objectRenderLocation.getY());
        this.sketch.imageMode(PConstants.CENTER);

        // Draw missile
        this.sketch.tint(255, 255);
        this.sketch.image(healthpack,
                0, 0,
                60,
                60);
        this.sketch.popMatrix();
    }
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
