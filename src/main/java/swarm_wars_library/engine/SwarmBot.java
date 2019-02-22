package swarm_wars_library.engine;

public class SwarmBot extends Mover {

  private double maxForce;
  private double stopDistance;
  private double orbitDistance;
  private RigidBody rb;
  private CommsChannel comms;

    public SwarmBot(Vector2D location_, CommsChannel comms) {
      super(location_);
      rb = getRigidBody();

      rb.setMaxSpeed(5);
      maxForce = 5;
      stopDistance = 100;
      orbitDistance = 70;
      this.setGOTag("PLAYER");
      this.comms = comms;
    }

    public void seek(Vector2D target) {
      Vector2D desired = Vector2D.sub(target, getLocation());
      findOrbit(desired);
      checkStopDistance(desired);
      steerBot(desired);
    }

    public void steerBot(Vector2D desired) {
      Vector2D steer = Vector2D.sub(desired, rb.getVelocity());
      steer.limit(maxForce);
      rb.applyForce(steer);
    }

    public void checkStopDistance(Vector2D desired) {
      double dist = desired.mag();
      desired.normalise();

      if(dist > stopDistance) {
        desired.mult(rb.getMaxSpeed());
      }
      else {
        double mappedSpeed = dist * rb.getMaxSpeed() / (stopDistance);
        desired.mult(mappedSpeed);
      }
    }

    public void findOrbit (Vector2D desired) {
      Vector2D orbitTarget = new Vector2D(desired.getX(), desired.getY());

      orbitTarget.normalise();
      orbitTarget.mult(orbitDistance);
      desired.sub(orbitTarget);
    }

    @Override
    public void update() {
      seek(comms.getMotherLocation());
      setHeading(rb.getVelocity().heading());
      rb.update(getLocation(), getHeading());
    }


}
