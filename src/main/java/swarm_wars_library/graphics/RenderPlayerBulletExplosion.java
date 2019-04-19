package swarm_wars_library.graphics;

import swarm_wars_library.engine.Vector2D;
import processing.core.PApplet;

public class RenderPlayerBulletExplosion extends RenderMapObject{

  private float scale = (float) 3;
  private int frames = 5;
  private int alpha = 20;
  private ParticleExplosion PE;

  public RenderPlayerBulletExplosion(PApplet sketch){
    super(sketch);
  }

  public void setParticleExplosionMapPosition(Vector2D objectMapPosition){
    this.PE = new ParticleExplosion(objectMapPosition);
    this.alpha = 20;
  }

  @Override 
  public void renderMapObject(){
    for (int f = 0; f < this.frames; f++){
      for(Particle p : PE.getParticleExplosion()){
        this.sketch.noStroke();
        this.sketch.fill(0, 237, 255, this.alpha);
        this.sketch.ellipseMode(2);
        this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                            (float) this.objectRenderPosition.getY(), 
                            this.scale, 
                            this.scale);
        p.update();
      }
      this.alpha += 10;
    } 
  }
}