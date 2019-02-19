package swarm_wars_library.engine;

public class GameObject {
	/* boundingLength needs to change with scale */

	public Vector2D location;
	public Vector2D scale;
	public double heading;
	double boundingLength;
	public String tag = "Empty";
	private boolean hasCollision = false;


	public GameObject(Vector2D location_){
		location = location_;
		scale = new Vector2D(1, 1);
		heading = 0;
		setBoundingLength();
	}

	public void setHasCollision(boolean value){
		hasCollision = value;
	}

	public boolean getHasCollision(){
		return hasCollision;
	}

	public void setScale(int x, int y){
		scale.x = x;
		scale.y = y;
		setBoundingLength();
	}

  public String getTag(){
		return tag;
	}

	public void setTag(String tag_){
		tag = tag_;
	}

	public double getHeading(){
		return heading;
	}

	public Vector2D getLocation(){
		return location;
	}

	public double getLocationX(){
		return location.x;
	}

	public double getLocationY(){
		return location.y;
	}

	public Vector2D getScale(){
		return scale;
	}

	public double getScaleX(){
		return scale.x;
	}

	public double getScaleY(){
		return scale.y;
	}

	private void setBoundingLength(){
		boundingLength = Math.sqrt(scale.x * scale.x / 4 + scale.y * scale.y / 4);
	};

}