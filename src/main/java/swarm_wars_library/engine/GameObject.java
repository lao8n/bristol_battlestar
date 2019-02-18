package swarm_wars_library.engine;

public class GameObject {
	/* boundingLength needs to change with scale */

	public Vector2D location;
	public Vector2D scale;
	public double heading;
	double boundingLength;


	public GameObject(Vector2D location_){
		location = location_;
		scale = new Vector2D(1, 1);
		heading = 0;
		setBoundingLength();
	}

	public void setScale(int x, int y){
		scale.x = x;
		scale.y = y;
		setBoundingLength();
	}

	private void setBoundingLength(){
		boundingLength = Math.sqrt(scale.x * scale.x / 4 + scale.y * scale.y / 4);
	};
}