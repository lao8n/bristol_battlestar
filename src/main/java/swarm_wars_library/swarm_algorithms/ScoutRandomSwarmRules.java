package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;

public class ScoutRandomSwarmRules extends AbstractSwarmRules {
  
  // Cumulative vectors
  private Vector2D separateV2D = new Vector2D(0, 0);

  // Neighbourhood counts
  private int separateNeighbourhoodCount = 0;
  
  // Rule minimum distances
  private int separateDistance = 20;

  //=========================================================================//
  // Scout Random Swarm Rules Constructor                                    //
  //=========================================================================//
  public ScoutRandomSwarmRules(int ruleId, RigidBody ruleRb, 
                               Transform ruleTransform){
    super(ruleId, ruleRb, ruleTransform);
  }

  //=========================================================================//
  // Scout Random Swarm Rules                                                //
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
  }

  //=========================================================================//
  // Scout Random Neighbourhood Count Rules                                  //
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