package swarm_wars_library.graphics;

import swarm_wars_library.engine.Vector2D;
// import swarm_wars_library.engine.Tag;

// import processing.core.PApplet;

// a class for the renderer to calculate special effects
class Particle {

    Vector2D location;
    Vector2D force = new Vector2D(0,0);
    double forceMag = 5;

    Particle(double x, double y){
        location = new Vector2D(x, y);
    }

    public void update(){
        // Apply force
        location.add(force);
    }

    public void setForce(Vector2D f){
        force = f;
        force.mult(forceMag);
    }

    public Vector2D getXY(){
        return location;
    }
    
    public void setXY(Vector2D location){
        this.location = location;
    }
}

