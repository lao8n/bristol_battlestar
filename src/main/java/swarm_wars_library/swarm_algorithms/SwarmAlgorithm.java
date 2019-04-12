package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.Transform;

public abstract class SwarmAlgorithm{

  protected Transform transform;

  public SwarmAlgorithm(Transform transform){
    this.transform = transform;
  }

  public abstract void applySwarmAlgorithm();
  protected abstract Vector2D seekMotherShip(Vector2D mothership_location);

  protected void avoidEdge(double scale){
    if(transform.getPosition().getX() < 0){
      this.transform.setPosition(0, transform.getPosition().getY());
      this.transform.setVelocity(new Vector2D(scale, 0.0));
    } else if (transform.getPosition().getX() > 900){
      this.transform.setPosition(900, transform.getPosition().getY());
      this.transform.setVelocity(new Vector2D(-scale, 0.0));
    }
    if(transform.getPosition().getY() < 0){
      this.transform.setPosition(transform.getPosition().getX(), 0);
      this.transform.setVelocity(new Vector2D(0, scale));
    } else if (transform.getPosition().getY() > 700){
      this.transform.setPosition(transform.getPosition().getX(), 700);
      this.transform.setVelocity(new Vector2D(0, -scale));
    }
  }
}