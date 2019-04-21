package swarm_wars_library.physics;

public class Transform {

    private Vector2D location;
    private Vector2D scale;
    private double heading; 
    private Vector2D velocity;

    public Transform(int scale){
        this.location = new Vector2D(0,0);
        this.scale = new Vector2D(scale, scale);
        this.velocity = new Vector2D(0,0);
        this.heading = 0;
    }

    public void setLocation(double x, double y){
        this.location.setX(x);
        this.location.setY(y);
    }

    public void setLocation(Vector2D pos){
        this.location = pos;
    }

    public Vector2D getLocation(){
        return this.location;
    }

    public void setVelocity(double x, double y){
        velocity.setX(x);
        velocity.setY(y);
    }

    public void setVelocity(Vector2D vel){
        velocity = vel;
    }

    public Vector2D getVelocity(){
        return velocity;
    }
    
    public double getScale(){
        return scale.getX();
    }

    public void setHeading(double h){
        heading = h;
    }

    public double getHeading(){
        return heading;
    }
}