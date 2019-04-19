package swarm_wars_library.graphics;

import swarm_wars_library.engine.Vector2D;
import processing.core.PApplet;
import swarm_wars_library.map.Map;

public class RenderEnemyExplosion extends RenderMapObject{

  private float scale = (float) 3;
  private float scale_enemy = (float) Map.getInstance().getEnemyScale();
  private int frames = 10;
  private int alpha = 20;
  private ParticleExplosion PE;

  public RenderEnemyExplosion(PApplet sketch){
    super(sketch);
  }

  public void setParticleExplosionMapPosition(Vector2D objectMapPosition){
    this.PE = new ParticleExplosion(objectMapPosition);
    this.alpha = 20;
  }

  @Override 
  public void renderMapObject(){
    this.sketch.fill(0, 0, 0);
    this.sketch.noStroke();
    this.sketch.ellipseMode(2);
    this.sketch.ellipse((float) this.objectRenderPosition.getX(), 
                        (float) this.objectRenderPosition.getY(), 
                        this.scale_enemy,
                        this.scale_enemy);

    for (int f = 0; f < this.frames; f++){
      for(Particle p : PE.getParticleExplosion()){
        this.sketch.noStroke();
        this.sketch.fill(255, 0, 199, this.alpha);
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