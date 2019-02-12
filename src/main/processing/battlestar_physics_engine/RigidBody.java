class RigidBody {
	public Vector2D velocity;
	public Vector2D acceleration;

	public double aVelocity;
	public double aAcceleration;

	public double maxSpeed;
	public double maxAngSpeed;

	public double mass;

	RigidBody(){
		velocity = new Vector2D(0, 0);
		acceleration = new Vector2D(0, 0);

		aVelocity = 0;
		aAcceleration = 0;

		maxSpeed = 10;
		maxAngSpeed = 10;

		mass = 1;
	}

	void update(Vector2D location, double heading) {
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

	void applyForce(Vector2D force) {
		force.div(mass);
		acceleration.add(force);
	}

	void applyRelativeFore(Vector2D force, double heading) {
		Vector2D relForce = new Vector2D(0, 0);
		relForce.x = force.mag() * Math.cos(force.heading() + heading);
		relForce.y = force.mag() * Math.sin(force.heading() + heading);
		applyForce(relForce);
	}

	/* ---------- TESTS --------- */

	public static void main(String[] args) {
		RigidBody program = new RigidBody();
		program.run();
	}

	private void run() {
		boolean testing = false;
		assert(testing = true);
		if (! testing) throw new Error("Use java -ea RigidBody");
		testApplyForce();
		testApplyRelativeForce();

	}

	private void testApplyForce() {
		RigidBody rb = new RigidBody();
		rb.mass = 1;
		rb.applyForce(new Vector2D(1, 1));
		assert(rb.acceleration.x == 1);
		assert(rb.acceleration.y == 1);
	}

	private void testApplyRelativeForce(){
		RigidBody rb = new RigidBody();
		double angle = Math.PI / 2;
		rb.mass = 1;
		rb.applyRelativeFore(new Vector2D(1, 0), angle);
		assert(rb.acceleration.x < 0.0001);
		assert(rb.acceleration.y == 1);
	}


}