package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.engine.Vector2D;

/**
 * SwarmRule Class holds information about CommsGlobal and properties of 
 * the Entity including id, RigidBody and Transform. It includes two 
 * abstrat methods: neighbourCountRule() and swarmRule() as well as a 
 * concrete implementation over iterateOverSwarm() which calls on 
 * the abstract methods.
 * <p>
 * This class follows a variation of the State design pattern, where each State
 * (in this case Swarm Rule) implements a different version of the Swarm Rule 
 * interface (although here we use an abstract class to allow state
 * and concrete implementation).
 * <p>
 * The advantage of this approach is that you have DRY-er code where the
 * iterateOverSwarm (which is shared across SwarmRules) only has to be 
 * implemented (and tested!) once. It also means that the implementations of
 * SwarmRules are much easier to understand as you only have to look at the
 * swarmRule() and neighbourCountRule().
 * <p>
 * Issues
 * 1. iterateOverSwarm() may not generalise for other types of Swarm Algorithms
 * 2. Each of the SwarmRules iterates over the entire swarm separately. This
 *    iteration, may potentially, be very expensive. Perhaps it would be worth
 *    only doing the iteration once, and calculating all the SwarmRules together
 *    in one go?
 */
public abstract class SwarmRule{
  protected CommsGlobal rule_comms;
  protected double rule_dist;
  protected int rule_id;
  protected int rule_neighbourCount;
  protected CommsPacket rule_otherBot;
  protected Vector2D rule_v2d = new Vector2D(0, 0);
  protected RigidBody rule_rb;
  protected Transform rule_transform;

  public SwarmRule(CommsGlobal rule_comms, int rule_id, 
    RigidBody rule_rb, Transform rule_transform){
    this.rule_comms = rule_comms;
    this.rule_id = rule_id;
    this.rule_rb = rule_rb;
    this.rule_transform = rule_transform;
  }

  public abstract void neighbourCountRule();

  public abstract void swarmRule();

  public Vector2D iterateOverSwarm(double desiredDistance){
    this.rule_neighbourCount = 0;

    for(int i = 1; i < this.rule_comms.get("PLAYER").getNumberOfReceivers(); i++){
      if (i != rule_id){
        this.rule_otherBot = this.rule_comms.get("PLAYER").getPacket(i);
        if (this.rule_otherBot.getIsAlive()){
          this.rule_dist = Vector2D.sub(this.rule_transform.getPosition(),
                                        this.rule_otherBot.getLocation())
                                   .mag();
          if (this.rule_dist > 0 && this.rule_dist < desiredDistance){
            swarmRule();
          }
        }
      }
    }

    if (this.rule_neighbourCount > 0){
      neighbourCountRule();
    }
    return this.rule_v2d;
  }
}