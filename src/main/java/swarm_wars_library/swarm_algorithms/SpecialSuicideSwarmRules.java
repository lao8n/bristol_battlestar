package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;

import java.util.ArrayList;

public class SpecialSuicideSwarmRules extends AbstractSwarmRules {

  // Cumulative vectors
  private Vector2D separateV2D = new Vector2D(0, 0);

  // Neighbourhood counts
  private int separateNeighbourhoodCount = 0;

  // Rule minimum distances
  private int separateDistance = 20;

  private double orbitDistance = 70;
  private double stopDistance = 100;


  //=========================================================================//
  // Special Suicide Constructor                                             //
  //=========================================================================//
  public SpecialSuicideSwarmRules(int ruleId, RigidBody ruleRb,
                               Transform ruleTransform){
    super(ruleId, ruleRb, ruleTransform);
  }

  //=========================================================================//
  // Special Suicide Swarm Rules                                             //
  //=========================================================================//
  @Override
  public void swarmRules(){
    // Separate rule
    if(this.ruleDistance > 0 && this.ruleDistance < this.separateDistance){
      Vector2D diff = Vector2D.sub(this.ruleTransform.getLocation(),
              this.ruleOtherBot.getLocation());
      diff.normalise();
      diff.div(this.ruleDistance);
      this.separateV2D.add(diff);
      this.separateNeighbourhoodCount++;
    }
    // // Hunt target and explode
    // Vector2D locationTarget =
    //         CommsGlobal.get(ENTITY.TURRET.toString())
    //                     .getPacket(0)
    //                     .getLocation();
    // Vector2D target = Vector2D.sub(locationTarget,
    //         this.ruleTransform.getLocation());
    // target.normalise();
    // target.mult(this.ruleRb.getMaxSpeed());
    // target.limit(this.ruleRb.getMaxForce());

  }

  //=========================================================================//
  // Special Suicide Neighbourhood Count Rules                               //
  //=========================================================================//
  @Override
  public void neighbourCountRules(){
    // Separate Rule
    if(this.separateNeighbourhoodCount > 0){
      this.separateV2D.div(this.separateNeighbourhoodCount);
      this.separateV2D.normalise();
      this.separateV2D.mult(this.ruleRb.getMaxSpeed());
      this.separateV2D.sub(this.ruleRb.getVelocity());
      this.separateV2D.limit(this.ruleRb.getMaxForce());
    }
    this.rulesV2D.add(0, this.separateV2D);
    this.separateNeighbourhoodCount = 0;
  }

}