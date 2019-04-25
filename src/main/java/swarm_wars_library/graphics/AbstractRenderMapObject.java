package swarm_wars_library.graphics;

import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.map.Map;
import processing.core.PApplet;

public abstract class AbstractRenderMapObject{

  protected PApplet sketch;
  protected Vector2D objectRenderLocation = new Vector2D(0, 0);
  private ParticleExplosion PE;

  public AbstractRenderMapObject(PApplet sketch){
    this.sketch = sketch;
  }

  protected abstract void renderMapObject();

  protected abstract void renderMapObjectExplosion(int alpha);

  public void update(Vector2D objectMapLocation, 
    Vector2D viewCentreMapLocation){

    this.setObjectRenderLocation(objectMapLocation, viewCentreMapLocation);
    if(this.objectRenderLocation.getX() >= 0 && 
       this.objectRenderLocation.getX() <= this.sketch.width &&
       this.objectRenderLocation.getY() >= 0 && 
       this.objectRenderLocation.getY() <= this.sketch.height){
        this.renderMapObject();
    }
  }

  public void updateExplosion(Vector2D objectMapLocation, 
    Vector2D viewCentreMapLocation, int frames){
      this.setParticleExplosionMapLocation(objectMapLocation);
    int alpha = 20;
    for(int f = 0; f < frames; f++){
      for(Particle p: PE.getParticleExplosion()){
        this.setObjectRenderLocation(p.getXY(), viewCentreMapLocation);
        this.renderMapObjectExplosion(alpha);
        p.update();
        alpha +=10;
      }
    }
  }

  public void setParticleExplosionMapLocation(Vector2D objectMapLocation){
    this.PE = new ParticleExplosion(objectMapLocation);
  }

  protected void setObjectRenderLocation(Vector2D objectMapLocation, 
    Vector2D viewCentreMapLocation){

    double viewCentreMapX;
    double viewCentreMapY;

    if(Map.getInstance().getMapWidth() - viewCentreMapLocation.getX() < 
       this.sketch.width /2){
      viewCentreMapX = Map.getInstance().getMapWidth() - this.sketch.width / 2;
    }
    else if (viewCentreMapLocation.getX() < this.sketch.width / 2){
      viewCentreMapX = this.sketch.width / 2;
    }
    else {
      viewCentreMapX = viewCentreMapLocation.getX();
    }

    if(Map.getInstance().getMapHeight() - viewCentreMapLocation.getY() < 
       this.sketch.height /2){
      viewCentreMapY = Map.getInstance().getMapHeight() - this.sketch.height / 2;
    }
    else if (viewCentreMapLocation.getY() < this.sketch.height / 2){
      viewCentreMapY = this.sketch.height / 2;
    }
    else {
      viewCentreMapY = viewCentreMapLocation.getY();
    }
    
    this.objectRenderLocation.setX(objectMapLocation.getX() - 
      viewCentreMapX + this.sketch.width / 2);
    this.objectRenderLocation.setY(objectMapLocation.getY() - 
      viewCentreMapY + this.sketch.height / 2);
  }

}