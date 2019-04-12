package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.swarm_algorithms.SwarmAlgorithm;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.comms.CommsGlobal;

public class ScoutShell extends SwarmAlgorithm {
  private CommsGlobal comms;
  private int id;
  private double orbitDistance = 70;
  public RigidBody rb;
  public Transform transform;
  private SeparateRule separate_rule;
  private double stopDistance = 100;
  private double scoutFlag = Math.random();

  public ScoutShell(CommsGlobal comms, int id, Transform transform, RigidBody rb){
    super(transform);
    this.comms = comms;
    this.id = id;
    this.rb = rb;
    this.transform = transform;
    this.separate_rule = new SeparateRule(this.comms, this.id, 
      this.rb, this.transform);
  }

  @Override
  public void applySwarmAlgorithm(){
    Vector2D separate_v2d = this.separate_rule.iterateOverSwarm(20);
    Vector2D random_v2d = new Vector2D(Math.random() - 0.5, Math.random() - 0.5);
    Vector2D seek_mothership_v2d = seekMotherShip(this.comms.get("PLAYER")
                                                            .getPacket(0)
                                                            .getLocation());

    separate_v2d.mult(0.02);
    seek_mothership_v2d.mult(0.2);
    random_v2d.mult(0.1);
    this.stopScouting();
    this.avoidEdge(0.6);
    this.rb.applyForce(separate_v2d);
    this.rb.applyForce(seek_mothership_v2d);
    this.rb.applyForce(random_v2d);
    this.transform.setHeading(this.rb.getVelocity().heading());
    this.rb.update(this.transform.getPosition(), this.transform.getHeading());
  }

  @Override
  public Vector2D seekMotherShip(Vector2D mothership_location) {

    Vector2D target = Vector2D.sub(mothership_location, 
                                   transform.getPosition());
    if(this.scoutFlag > 0.2){
      target = findOrbit(target);
      target = checkStopDistance(target);
    }
    else if (this.scoutFlag < 0){
      if(target.mag() < 70){
        this.scoutFlag = 1;
      }
      target = findOrbit(target);
      target = checkStopDistance(target);
      target.mult(0.01);
    }
    else {
      target.mult(-0.000001);
    }

    target.limit(rb.getMaxForce());
    return target;
  }

  private Vector2D checkStopDistance(Vector2D desired) {
    double dist = desired.mag();
    desired.normalise();
    if (dist > stopDistance) {
      desired.mult(rb.getMaxSpeed());
    } else {
      double mappedSpeed = dist * rb.getMaxSpeed() / (stopDistance);
      desired.mult(mappedSpeed);
    }
    return desired;
  }

  private Vector2D findOrbit(Vector2D desired) {
    Vector2D orbitTarget = new Vector2D(desired.getX(), desired.getY());
    orbitTarget.normalise();
    orbitTarget.mult(orbitDistance);
    desired.sub(orbitTarget);

    return desired;
  }

  private void stopScouting(){
    if(transform.getPosition().getX() < 0){
      this.scoutFlag = -1;
    } else if (transform.getPosition().getX() > 900){
      this.scoutFlag = -1;
    }
    if(transform.getPosition().getY() < 0){
      this.scoutFlag = -1;
    } else if (transform.getPosition().getY() > 700){
      this.scoutFlag = -1;
    }
  }

  private class SeparateRule extends SwarmRule {  
    public SeparateRule(CommsGlobal rule_comms, int rule_id, 
      RigidBody rule_rb, Transform rule_transform){
      super(rule_comms, rule_id, rule_rb, rule_transform);
    }
    @Override
    public void swarmRule(){
      Vector2D diff = Vector2D.sub(this.rule_transform.getPosition(), 
      this.rule_otherBot.getLocation());
      diff.normalise();
      diff.div(this.rule_dist);
      this.rule_v2d.add(diff);
      this.rule_neighbourCount++;
    }
    @Override
    public void neighbourCountRule(){
      this.rule_v2d.div(this.rule_neighbourCount);
      this.rule_v2d.normalise();
      this.rule_v2d.mult(rb.getMaxSpeed());
      this.rule_v2d.sub(rb.getVelocity());
      this.rule_v2d.limit(rb.getMaxForce());
    }
  }
}