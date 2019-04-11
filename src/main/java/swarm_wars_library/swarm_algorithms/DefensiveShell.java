package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.swarm_algorithms.SwarmAlgorithm;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.CommsPacket;
import swarm_wars_library.engine.CommsGlobal;
import swarm_wars_library.engine.CommsChannel;

public class DefensiveShell implements SwarmAlgorithm {

  private CommsGlobal comms;
  private double desiredSeparation;
  private int id;
  private double maxForce = 10;
  private double orbitDistance = 70;
  public RigidBody rb;
  private double stopDistance = 100;
  public Transform transform;

  public DefensiveShell(CommsGlobal comms, int id, Transform transform, 
    RigidBody rb){
    this.comms = comms;
    this.id = id;
    this.rb = rb;
    this.transform = transform;
  }

  public void applySwarmAlgorithm(){
    CommsChannel x = this.comms.get("PLAYER");
    CommsPacket y = x.getPacket(0);
    Vector2D seek_v2d = this.seek(this.comms.get("PLAYER").getPacket(0).getLocation());
    Vector2D separate_v2d = this.separate();
    seek_v2d.mult(1.5);
    separate_v2d.mult(0.2);
    this.rb.applyForce(seek_v2d);
    this.rb.applyForce(separate_v2d);
    this.transform.setHeading(this.rb.getVelocity().heading());
    this.rb.update(this.transform.getPosition(), this.transform.getHeading());
  }

  public Vector2D separate() {
    this.desiredSeparation = 20;
    int neighbourCount = 0;
    Vector2D sum = new Vector2D(0, 0);
    for (int i = 1; i < comms.get("PLAYER").getNumberOfReceivers(); i++) {
      if (i != id) {
        CommsPacket otherBot = comms.get("PLAYER").getPacket(i);
        if (otherBot.getIsAlive()) {
          double dist = Vector2D.sub(transform.getPosition(), otherBot.getLocation()).mag();
          if (dist > 0 && dist < desiredSeparation) {
            Vector2D diff = Vector2D.sub(transform.getPosition(), otherBot.getLocation());
            diff.normalise();
            diff.div(dist);
            sum.add(diff);
            neighbourCount++;
          }
        }
      }
    }
    if (neighbourCount > 0) {
      sum.div(neighbourCount);
      sum.normalise();
      sum.mult(rb.getMaxSpeed());
      return steerBot(sum);
    }
    return sum;
  }

  public Vector2D seek(Vector2D target) {
    Vector2D desired = Vector2D.sub(target, transform.getPosition());
    findOrbit(desired);
    checkStopDistance(desired);
    return steerBot(desired);
  }

  public Vector2D steerBot(Vector2D desired) {
    Vector2D steer = Vector2D.sub(desired, rb.getVelocity());
    steer.limit(maxForce);
    return steer;
  }

  public void checkStopDistance(Vector2D desired) {
    double dist = desired.mag();
    desired.normalise();
    if (dist > stopDistance) {
      desired.mult(rb.getMaxSpeed());
    } else {
      double mappedSpeed = dist * rb.getMaxSpeed() / (stopDistance);
      desired.mult(mappedSpeed);
    }
  }

  public void findOrbit(Vector2D desired) {
    Vector2D orbitTarget = new Vector2D(desired.getX(), desired.getY());
    orbitTarget.normalise();
    orbitTarget.mult(orbitDistance);
    desired.sub(orbitTarget);
  }
}
