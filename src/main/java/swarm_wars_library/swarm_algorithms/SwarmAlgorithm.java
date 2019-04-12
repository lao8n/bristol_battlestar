package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.map.Map;

public abstract class SwarmAlgorithm{

  protected Transform transform;
  private Map map;

  public SwarmAlgorithm(Transform transform){
    this.transform = transform;
    map = Map.getInstance();
  }

  public abstract void applySwarmAlgorithm();
  protected abstract Vector2D seekMotherShip(Vector2D mothership_location);

  protected void avoidEdge(double scale){
    if(transform.getPosition().getX() < 0){
      this.transform.setPosition(0, transform.getPosition().getY());
      this.transform.setVelocity(new Vector2D(scale, 0.0));
    } else if (transform.getPosition().getX() > map.getMapWidth()){
      this.transform.setPosition(map.getMapWidth(), transform.getPosition().getY());
      this.transform.setVelocity(new Vector2D(-scale, 0.0));
    }
    if(transform.getPosition().getY() < 0){
      this.transform.setPosition(transform.getPosition().getX(), 0);
      this.transform.setVelocity(new Vector2D(0, scale));
    } else if (transform.getPosition().getY() > map.getMapHeight()){
      this.transform.setPosition(transform.getPosition().getX(), map.getMapHeight());
      this.transform.setVelocity(new Vector2D(0, -scale));
    }
  }
}