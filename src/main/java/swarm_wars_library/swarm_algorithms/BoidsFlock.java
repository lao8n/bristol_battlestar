package swarm_wars_library.swarm_algorithms;

import swarm_wars_library.swarm_algorithms.SwarmAlgorithm;
import swarm_wars_library.engine.Transform;
import swarm_wars_library.engine.RigidBody;
import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.CommsPacket;
import swarm_wars_library.engine.CommsGlobal;

import java.util.*;

public class BoidsFlock implements SwarmAlgorithm {

  private CommsGlobal comms;
  private double desiredDistance;
  private double desiredSeparation;
  private double maxForce = 10;
  private int id;
  public RigidBody rb;
  public Transform transform;

  public BoidsFlock(CommsGlobal comms, int id, Transform transform, RigidBody rb){
    this.comms = comms;
    this.id = id;
    this.rb = rb;
    this.transform = transform;
  }

  public void applySwarmAlgorithm(){
    Vector2D separate_v2d = this.separate();
    List<Vector2D> list_vectors = this.alignmentAndCohesion();
    Vector2D align_v2d = list_vectors.get(0);
    Vector2D cohesion_v2d = list_vectors.get(1);
    Vector2D random_v2d = new Vector2D(Math.random() - 0.5, Math.random() - 0.5);
    separate_v2d.mult(0.2);
    align_v2d.mult(0.4);
    cohesion_v2d.mult(0.3);
    random_v2d.mult(0.1);
    this.rb.applyForce(separate_v2d);
    this.rb.applyForce(align_v2d);
    this.rb.applyForce(cohesion_v2d);
    this.rb.applyForce(random_v2d);
    this.avoidEdge();
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

  public List<Vector2D> alignmentAndCohesion() {
    this.desiredDistance = 40;
    int neighbourCount = 0;
    Vector2D target = new Vector2D(0, 0);
    Vector2D sum = new Vector2D(0, 0);
    List<Vector2D> list_vectors = new ArrayList<Vector2D>();
    list_vectors.add(0, sum);
    list_vectors.add(1, target);

    for (int i = 1; i < comms.get("PLAYER").getNumberOfReceivers(); i++) {
      if (i != id) {
        CommsPacket otherBot = comms.get("PLAYER").getPacket(i);
        if (otherBot.getIsAlive()) {
          double dist = Vector2D.sub(transform.getPosition(), otherBot.getLocation()).mag();
          if (dist > 0 && dist < desiredDistance) {
            sum.add(otherBot.getVelocity());
            target.add(otherBot.getLocation());
            neighbourCount++;
          }
        }
      }
    }
    if (neighbourCount > 0) {
        sum.div(neighbourCount);
        sum.normalise();
        sum.mult(rb.getMaxSpeed());
        sum.sub(rb.getVelocity());
        sum.limit(this.maxForce);
        target.div(neighbourCount);
        list_vectors.add(0, sum);
        list_vectors.add(1, target);
    }
    return list_vectors;
  }

  public void avoidEdge(){
    if(transform.getPosition().getX() < 0){
      this.transform.setPosition(0, transform.getPosition().getY());
    } else if (transform.getPosition().getX() > 600){
      this.transform.setPosition(600, transform.getPosition().getY());
    }
    if(transform.getPosition().getY() < 0){
      this.transform.setPosition(transform.getPosition().getX(), 0);
    } else if (transform.getPosition().getY() > 600){
      this.transform.setPosition(transform.getPosition().getX(), 600);
    }
  }

  public Vector2D steerBot(Vector2D desired) {
    Vector2D steer = Vector2D.sub(desired, rb.getVelocity());
    steer.limit(maxForce);
    return steer;
  }
}