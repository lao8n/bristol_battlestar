package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.engine.CommsGlobal;
import swarm_wars_library.engine.CommsPacket;
import swarm_wars_library.engine.Vector2D;

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