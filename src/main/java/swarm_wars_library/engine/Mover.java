package swarm_wars_library.engine;

public class Mover extends GameObject {
	private RigidBody rb;

	public Mover(Vector2D location_) {
		super(location_);
		rb = new RigidBody();
	}

	public void setMoverTag(String tag_name){
		super.setGOTag(tag_name);
	}

	public RigidBody getRigidBody(){
		return rb;
	}

	public double getHeading(){
		return super.getHeading();
	}
	public void setHeading(double h){
		super.setHeading(h);
	}

	public Vector2D getLocation(){
		return super.getLocation();
	}

	public double getLocationX(){
		return super.getLocationX();
	}

	public double getLocationY(){
		return super.getLocationY();
	}

	public void setLocation(double x, double y){
		super.setLocationXY(x, y);
	}

	public Vector2D getScale(){
		return super.getScale();
	}

	public double getScaleX(){
		return super.getScaleX();
	}

	public double getScaleY(){
		return super.getScaleY();
	}

	public void updateMover(Vector2D location, double heading){
		rb.update(location, heading);
	}
}