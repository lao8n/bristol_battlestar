class GameObject {
	Vector2D location;
	Vector2D scale;
	double heading;

	GameObject(Vector2D location_){
		location = location_;
		scale = new Vector2D(1, 1);
		heading = 0;
	}

	/* ---------- TESTS --------- */

	public static void main(String[] args) {
		GameObject program = new GameObject(new Vector2D(0, 0));
		program.run();
	}

	private void run() {
		boolean testing = false;
		assert(testing = true);
		if (! testing) throw new Error("Use java -ea Vector2D");
		testConstructor();
	}

	private void testConstructor() {
		GameObject go = new GameObject(new Vector2D(1, 5));
		assert(go.location.x == 1);
		assert(go.location.y == 5);
		assert(go.scale.x == 1);
		assert(go.scale.y == 1);
		assert(go.heading == 0);
	}
}