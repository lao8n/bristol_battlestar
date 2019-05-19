package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.entities.STATE;

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
public abstract class AbstractSwarmAlgorithm{

  protected Transform transform;
  protected Map map;
  protected ENTITY tag;
  protected STATE state;

  public AbstractSwarmAlgorithm(ENTITY tag, Transform transform){
    this.transform = transform;
    this.tag = tag;
    map = Map.getInstance();
  }

  public abstract void applySwarmAlgorithm();
  protected abstract Vector2D seekMotherShip();

  public STATE getstate() { return this.state; }
  public void setState(STATE state) { this.state = state; }

  protected void avoidEdge(double scale){
    if(transform.getLocation().getX() < 0){
      this.transform.setLocation(0, transform.getLocation().getY());
      this.transform.setVelocity(new Vector2D(scale, 0.0));
    } else if (transform.getLocation().getX() > map.getMapWidth()){
      this.transform.setLocation(map.getMapWidth(), transform.getLocation().getY());
      this.transform.setVelocity(new Vector2D(-scale, 0.0));
    }
    if(transform.getLocation().getY() < 0){
      this.transform.setLocation(transform.getLocation().getX(), 0);
      this.transform.setVelocity(new Vector2D(0, scale));
    } else if (transform.getLocation().getY() > map.getMapHeight()){
      this.transform.setLocation(transform.getLocation().getX(), map.getMapHeight());
      this.transform.setVelocity(new Vector2D(0, -scale));
    }
  }
}