package swarm_wars_library.engine;

public class Mover extends GameObject {
	public RigidBody rb;

	public Mover(Vector2D location_) {
		super(location_);
		rb = new RigidBody();

	}
}