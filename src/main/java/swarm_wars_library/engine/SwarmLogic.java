/* The component that controls all bot actions */
package swarm_wars_library.engine;

public class SwarmLogic {

  private double maxForce;
  private double stopDistance;
  private double orbitDistance;
  private RigidBody rb;
  private CommsGlobal comms;
  // private CommsChannel comms;
  // private CommsPacket commsPack;
  private boolean isAlive;
  private int id;
  private double desiredSeparation;
  public static int counter = 1;
  private Transform transform;
  private int playerId;

  public SwarmLogic(Transform transform, RigidBody rb, int playerId) {
    this.rb = rb;
    this.transform = transform;
    this.playerId = playerId;

    rb.setMaxSpeed(15);
    maxForce = 10;
    stopDistance = 100;
    orbitDistance = 70;
    // this.comms = comms;
    // commsPack = new CommsPacket();
    isAlive = true;
    id = counter;
    counter++;
    // sendPacket();
  }

  public void setTransform(Transform transform) {
    this.transform = transform;
  }

  public Transform getTransform() {
    return transform;
  }

  public static void resetCounter(){
    counter = 1;
  }

  public Vector2D separate() {
    desiredSeparation = 20;
    int neighbourCount = 0;
    Vector2D sum = new Vector2D(0, 0);
    for (int i = 1; i < comms.get("PLAYER" + playerId).getNumberOfReceivers(); i++) {
      if (i != id) {
        CommsPacket otherBot = comms.get("PLAYER"  + playerId).getPacket(i);
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

  public int getId() {
    return id;
  }

  public void setComms(CommsGlobal comms) {
    this.comms = comms;
  }

  // public void sendPacket() {
  //   commsPack.setIsAlive(isAlive);
  //   commsPack.setLocation(transform.getPosition()); 
  //   comms.setPacket(commsPack, id);
  // }

  private void applyBehaviours() {
    Vector2D seek = seek(comms.get("PLAYER" + playerId).getPacket(0).getLocation());
    Vector2D seperate = separate();
    seek.mult(1.5);
    seperate.mult(0.2);
    rb.applyForce(seek);
    rb.applyForce(seperate);
  }

  public void update() {
    // get other swarm pacakges
    // seperate based on other SwarmWarm
    // seek based on MS
    // update myself
    // send my packet

    applyBehaviours();
    transform.setHeading(rb.getVelocity().heading());
    rb.update(transform.getPosition(), transform.getHeading());
    // sendPacket();
  }


}