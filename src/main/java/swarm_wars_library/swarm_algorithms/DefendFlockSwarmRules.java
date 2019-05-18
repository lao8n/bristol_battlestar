package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;

public class DefendFlockSwarmRules extends AbstractSwarmRules {

  // Cumulative vectors
  private Vector2D separateV2D = new Vector2D(0, 0);
  private Vector2D alignV2D = new Vector2D(0, 0);
  private Vector2D coheseV2D = new Vector2D(0, 0);

  // Neighbourhood counts
  private int separateNeighbourhoodCount = 0;
  private int alignNeighbourhoodCount = 0;
  private int coheseNeighbourhoodCount = 0;
  
  // Rule minimum distances
  private int separateDistance = 20;
  private int alignDistance = 40;
  private int coheseDistance = 40;

  //=========================================================================//
  // Defend Flock Swarm Rules Constructor                                    //
  //=========================================================================//
  public DefendFlockSwarmRules(int ruleId, RigidBody ruleRb, 
                               Transform ruleTransform){
    super(ruleId, ruleRb, ruleTransform);
  }

  //=========================================================================//
  // Defend Flock Swarm Rules                                                //
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

    // Align Rule
    if(this.ruleDistance > 0 && this.ruleDistance < this.alignDistance){
      this.alignV2D.add(this.ruleOtherBot.getVelocity());
      this.alignNeighbourhoodCount++;
    }

    // Cohese Rule
    if(this.ruleDistance > 0 && this.ruleDistance < this.coheseDistance){
      this.coheseV2D.add(this.ruleOtherBot.getVelocity());
      this.coheseNeighbourhoodCount++;
    }
  }

  //=========================================================================//
  // Defend Flock Neighbourhood Count Rules                                  //
  //=========================================================================//
  @Override 
  public void neighbourCountRules(){
    // Separate Rule
    if(this.separateNeighbourhoodCount > 0){
      this.separateV2D.div(this.separateNeighbourhoodCount);
      this.separateV2D.normalise();
      this.separateV2D.mult(this.ruleRb.getMaxSpeed());
    }
    this.rulesV2D.add(0, this.separateV2D);
    this.separateNeighbourhoodCount = 0;

    // Align Rule 
    if(this.alignNeighbourhoodCount > 0){
      this.alignV2D.div(this.alignNeighbourhoodCount);
      this.alignV2D.normalise();
      this.alignV2D.mult(this.ruleRb.getMaxSpeed());
      this.alignV2D.sub(this.ruleRb.getVelocity());
      this.alignV2D.limit(this.ruleRb.getMaxForce());
    }
    this.rulesV2D.add(1, this.alignV2D);
    this.alignNeighbourhoodCount = 0;

    // Cohese Rule
    if(this.coheseNeighbourhoodCount > 0){
      this.coheseV2D.div(this.coheseNeighbourhoodCount);
      this.coheseV2D.sub(this.ruleTransform.getLocation());
      this.coheseV2D.normalise();
      this.coheseV2D.mult(this.ruleRb.getMaxSpeed());
      this.coheseV2D.sub(this.ruleRb.getVelocity());
      this.coheseV2D.limit(this.ruleRb.getMaxForce());
    }
    this.rulesV2D.add(2, this.coheseV2D);
    this.coheseNeighbourhoodCount = 0;
  }
}