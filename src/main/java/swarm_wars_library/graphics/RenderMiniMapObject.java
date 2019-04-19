package swarm_wars_library.graphics;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.map.Map;

import processing.core.PApplet;

public abstract class RenderMiniMapObject{

  protected PApplet sketch;
  protected Vector2D objectRenderPosition = new Vector2D(0, 0);
  protected Map map;
  protected float miniMapDim = 200;
  protected float miniMapOffset = 20;

  public RenderMiniMapObject(PApplet sketch){
    this.sketch = sketch;
    this.map = Map.getInstance();
  }

  protected abstract void renderMiniMapObject();

  public void update(Vector2D objectMapPosition){

    this.setObjectRenderPosition(objectMapPosition);
    this.renderMiniMapObject();
  }

  private void setObjectRenderPosition(Vector2D objectMapPosition){
    
    this.objectRenderPosition.setX(
      objectMapPosition.getX() / this.map.getMapWidth() *
      this.miniMapDim);
    this.objectRenderPosition.setY(
      objectMapPosition.getY() / this.map.getMapHeight() *
      this.miniMapDim);
  }
}
