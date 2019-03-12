package swarm_wars_library.engine;

public class GameObject {
	/* boundingLength needs to change with scale */

	private Vector2D location;
	private Vector2D scale;
	private double heading;
	private double boundingLength;
	private String tag = "Empty";
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
		scale.setXY(x, y);
		setBoundingLength();
	}

  public String getGOTag(){
		return tag;
	}

	public void setGOTag(String tag_){
		tag = tag_;
	}

	public double getHeading(){
		return heading;
	}

	public void setHeading(double h){
		this.heading = h;
	}

	public Vector2D getLocation(){
		return location;
	}

	public double getLocationX(){
		return location.getX();
	}

	public double getLocationY(){
		return location.getY();
	}

	public void setLocationXY(double x, double y){
		location.setXY(x, y);
	}

	public Vector2D getScale(){
		return scale;
	}

	public double getScaleX(){
		return scale.getX();
	}

	public double getScaleY(){
		return scale.getY();
	}

	public double getBoundingLength(){
		return boundingLength;
	}

	private void setBoundingLength(){
		double x = scale.getX();
		double y = scale.getY();
		boundingLength = Math.sqrt(x * x / 4 + y * y / 4);
	};

	public void update(){
	}

	public int getTimer(){
		return 0;
	}


}