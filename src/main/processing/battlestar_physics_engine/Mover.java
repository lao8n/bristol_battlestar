class Mover extends GameObject {
	RigidBody rb;

	Mover(Vector2D location_) {
		super(location_);
		rb = new RigidBody();

	}

	/* ---------- TESTS --------- */

	public static void main(String[] args) {
		Mover program = new Mover(new Vector2D(0, 0));
		program.run();
	}

	private void run() {
		boolean testing = false;
		assert(testing = true);
		if (! testing) throw new Error("Use java -ea Mover");
		testConstructor();
	}

	private void testConstructor() {
		Mover m = new Mover(new Vector2D(1, 5));
		assert(m.location.x == 1);
		assert(m.location.y == 5);
		assert(m.scale.x == 1);
		assert(m.scale.y == 1);
		assert(m.heading == 0);
		assert(m.rb.mass == 1);
		assert(m.rb.velocity.x == 0);
		assert(m.rb.velocity.y == 0);

	}
}