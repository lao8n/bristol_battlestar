package swarm_wars_library.engine;

public class RigidBody {
	public Vector2D velocity;
	public Vector2D acceleration;

	public double aVelocity;
	public double aAcceleration;

	public double maxSpeed;
	public double maxAngSpeed;

	public double mass;

	public RigidBody(){
		velocity = new Vector2D(0, 0);
		acceleration = new Vector2D(0, 0);

		aVelocity = 0;
		aAcceleration = 0;

		maxSpeed = 10;
		maxAngSpeed = 10;

		mass = 1;
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

	/*  DISPLAY FUNCTION HERE */


	/*  EDGE WRAPPING / WALL FUNCTIONS HERE */


	/*  EDGE WRAPPING FUNCTIONS HERE */

	/*	RIGID BODY FORCES */

	public void applyForce(Vector2D force) {
		force.div(mass);
		acceleration.add(force);
	}

	public void applyRelativeForce(Vector2D force, double heading) {
		Vector2D relForce = new Vector2D(0, 0);
		relForce.x = force.mag() * Math.cos(force.heading() + heading);
		relForce.y = force.mag() * Math.sin(force.heading() + heading);
		applyForce(relForce);
	}

}