package swarm_wars_library.graphics;

import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.map.Map;

import processing.core.PApplet;

public abstract class AbstractRenderMiniMapObject{

  protected PApplet sketch;
  protected Vector2D objectRenderLocation = new Vector2D(0, 0);
  protected Map map;
  protected float miniMapDim = 200;
  protected float miniMapOffset = 20;

  public AbstractRenderMiniMapObject(PApplet sketch){
    this.sketch = sketch;
    this.map = Map.getInstance();
  }

  protected abstract void renderMiniMapObject();

  public void update(Vector2D objectMapLocation){

    this.setObjectRenderLocation(objectMapLocation);
    this.renderMiniMapObject();
  }

  private void setObjectRenderLocation(Vector2D objectMapLocation){
    
    this.objectRenderLocation.setX(
      objectMapLocation.getX() / this.map.getMapWidth() *
      this.miniMapDim * 0.95 + this.miniMapDim * 0.025);
    this.objectRenderLocation.setY(
      objectMapLocation.getY() / this.map.getMapHeight() *
      this.miniMapDim * 0.95 + this.miniMapDim * 0.025);
  }
}
