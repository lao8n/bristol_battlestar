package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.physics.Vector2D;

import java.util.ArrayList;

public abstract class AbstractSwarmRules{
  protected double ruleDistance;
  protected int ruleId;
  protected CommsPacket ruleOtherBot;
  protected ArrayList<Vector2D> rulesV2D;
  protected RigidBody ruleRb;
  protected Transform ruleTransform;

  public AbstractSwarmRules(int ruleId, RigidBody ruleRb, 
    Transform ruleTransform){
    this.ruleId = ruleId;
    this.ruleRb = ruleRb;
    this.ruleTransform = ruleTransform;
    this.rulesV2D = new ArrayList<Vector2D>();
  }

  public ArrayList<Vector2D> iterateOverSwarm(ENTITY tag){
    ArrayList<CommsPacket> otherBots = CommsGlobal.get(tag.toString())
                                                  .getPackets();
    for(CommsPacket otherBot: otherBots){
      this.ruleOtherBot = otherBot;
      if (this.ruleOtherBot.getId() != this.ruleId){
        if (this.ruleOtherBot.getState().equals(STATE.ALIVE)){
          this.ruleDistance = Vector2D.sub(this.ruleTransform.getLocation(),
                                           this.ruleOtherBot.getLocation())
                                      .mag();
          this.swarmRules();
        }
      }
    }
    this.neighbourCountRules();
    
    return this.rulesV2D;
  }

  public abstract void neighbourCountRules();

  public abstract void swarmRules();
}