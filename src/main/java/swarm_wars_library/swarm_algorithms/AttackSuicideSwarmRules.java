package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsPacket;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.Tag;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Vector2D;

import java.util.ArrayList;

public class AttackSuicideSwarmRules extends AbstractSwarmRules {

  // Cumulative vectors
  private Vector2D separateV2D = new Vector2D(0, 0);

  // Neighbourhood counts
  private int separateNeighbourhoodCount = 0;

  // Rule minimum distances
  private int separateDistance = 20;

  private double orbitDistance = 70;
  private double stopDistance = 100;



  //=========================================================================//
  // Defend Shell Swarm Rules Constructor                                    //
  //=========================================================================//
  public AttackSuicideSwarmRules(int ruleId, RigidBody ruleRb,
                               Transform ruleTransform){
    super(ruleId, ruleRb, ruleTransform);
  }

  //=========================================================================//
  // Attack Shell Swarm Rules                                                //
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
    // Hunt target and explode
    if (this.ruleId % 2 == 0) {
      Vector2D locationTarget =
              CommsGlobal.get(ENTITY.TURRET.toString())
                      .getPacket(0)
                      .getLocation();
      Vector2D target = Vector2D.sub(locationTarget,
              ruleTransform.getLocation());
      separateV2D = this.findOrbit(target);
      separateV2D = this.checkStopDistance(target);
    }
  }

  private Vector2D checkStopDistance(Vector2D desired) {
    double dist = desired.mag();
    desired.normalise();
    if (dist > this.stopDistance) {
      desired.mult(ruleRb.getMaxSpeed());
    }
    else {
      double mappedSpeed = dist * ruleRb.getMaxSpeed() / (this.stopDistance);
      desired.mult(mappedSpeed);
    }
    return desired;
  }

  private Vector2D findOrbit(Vector2D desired) {
    Vector2D orbitTarget = new Vector2D(desired.getX(), desired.getY());
    orbitTarget.normalise();
    orbitTarget.mult(this.orbitDistance);
    desired.sub(orbitTarget);

    return desired;
  }

  //=========================================================================//
  // Attack Swarm Shell Neighbourhood Count Rules                                  //
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