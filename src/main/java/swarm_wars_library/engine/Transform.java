package swarm_wars_library.engine;

class Transform {

    private Vector2D position;
    private Vector2D scale;
    private double heading = 0; 

    Transform(){
        position = new Vector2D(0,0);
        scale = new Vector2D(1, 1);
    }

    public void setPosition(Vector2D pos){
        pos = position;
    }

    public Vector2D getPosition(){
        return position;
    }

    public void setScale(Vector2D sc){
        sc = scale;
    }

    public Vector2D getScale(){
        return scale;
    }

    public void setHeading(double h){
        h = heading;
    }

    public double getHeading(){
        return heading;
    }
}