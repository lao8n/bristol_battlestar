package swarm_wars_library.engine;

class Transform {

    private Vector2D position;
    private Vector2D scale;
    private double heading; 
    private Vector2D velocity;
    private double boundingLength;

    Transform(){
        position = new Vector2D(0,0);
        scale = new Vector2D(10, 10);
        velocity = new Vector2D(0,0);
        heading = 0;
    }

    public void setPosition(double x, double y){
        position.setX(x);
        position.setY(y);
    }

    public void setPosition(Vector2D pos){
        position = pos;
    }

    public Vector2D getPosition(){
        return position;
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

    public void setScale(double x, double y){
        scale.setX(x);
        scale.setY(y);
    }

    public void setScale(Vector2D sc){
        scale = sc;
    }

    public Vector2D getScale(){
        return scale;
    }

    public void setHeading(double h){
        heading = h;
    }

    public double getHeading(){
        return heading;
    }

    public double getBoundingLength(){
        return boundingLength;
    }

    public void setBoundingLength(){
        double x = position.getX();
        double y = position.getY();
        this.boundingLength = Math.sqrt(x * x / 4 + y * y / 4);
    }
}