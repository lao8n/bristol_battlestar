package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.map.Map;

/**
 * SwarmAlgorithm Class holds information about the game map and the Transform
 * of the Bot calling it. It includes two abstract methods: 
 * applySwarmAlgorithm() and seekMotherShip() which need to be implemented
 * in inheriting classes. It also includes a concrete implementation of 
 * avoidEdge() - although this can be overridden.
 * <p>
 * This class follows a variation of the State design pattern, where each State
 * (in this case Swarm Algorithm) implements a different version of the Swarm
 * Algorithm 'interface' (although here we use an abstract class to allow state
 * and concrete implementation).
 * <p>
 * The advantage of this approach is that applySwarmAlgorithm() can be called 
 * in SwarmLogic (in the update() method), without having to worry about which
 * implementation to carry out (no need for if/else statements). Instead 
 * it implements the version for the class that is implementing SwarmAlgorithm
 * (see BoidsFlock, DefensiveShell and ScoutShell)
 * <p>
 * Issues
 * 1. How does this work with FSM? In game programming patterns it specifically
 *    uses finite state machines as good examples of this design pattern. 
 *    Although I'm not sure if/how that would work specifically.
 * 2. There is a question on how to have this design pattern handle 1. 
 *    combinations of state 2. history. Perhaps hierarchical state machines 
 *    for the former and pushdown automata (for the latter) are worth considering.
 *
 */
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