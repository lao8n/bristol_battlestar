package swarm_wars_library.engine;

public class RigidBody {
	private Vector2D velocity;
	private Vector2D acceleration;

	private double aVelocity;
	private double aAcceleration;

	private double maxSpeed;
	private double maxAngSpeed;

	private double mass;

	public RigidBody(){
		velocity = new Vector2D(0, 0);
		acceleration = new Vector2D(0, 0);

		aVelocity = 0;
		aAcceleration = 0;

		maxSpeed = 10;
		maxAngSpeed = 10;

		mass = 1;
	}

	public double getMass(){
		return mass;
	}

	public void setMass(double mass_value){
		this.mass = mass_value;
	}

	public double getVelocityX(){
		return velocity.getX();
	}

	public double getVelocityY(){
		return velocity.getY();
	}

	public double getAccelerationX(){
		return acceleration.getX();
	}

	public double getAccelerationY(){
		return acceleration.getY();
	}

	public double getMaxSpeed(){
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Vector2D getVelocity() {
		Vector2D velocityCopy = new Vector2D(velocity.getX(), velocity.getY());
		return velocityCopy;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public void update(Vector2D location, double heading) {
		velocity.add(acceleration);
		velocity.limit(maxSpeed);
		location.add(velocity);
		acceleration.mult(0);


		aVelocity += (aAcceleration);
		if(aVelocity >= maxAngSpeed){
			aVelocity = maxAngSpeed;
		} else if (aVelocity <= -maxAngSpeed){
			aVelocity = -maxAngSpeed;
		}

		heading += (aVelocity);
		aAcceleration = 0;

	}

	/*  EDGE WRAPPING / WALL FUNCTIONS HERE */


	/*  EDGE WRAPPING FUNCTIONS HERE */

	/*	RIGID BODY FORCES */

	public void applyForce(Vector2D force) {
		Vector2D f = Vector2D.div(force, mass);
		acceleration.add(f);
	}

	public void applyRelativeForce(Vector2D force, double heading) {
		Vector2D relForce = new Vector2D(0, 0);
		relForce.setXY(force.mag() * Math.cos(force.heading() + heading),
									 force.mag() * Math.sin(force.heading() + heading));
		applyForce(relForce);
	}

}
