
package swarm_wars_library.engine;

import swarm_wars_library.physics.Vector2D;

public class AI {

    static int shootInterval = 500; 
    double heading = 50;
    Vector2D target = new Vector2D(0, 0);
    boolean inRange = false;

    //calculates heading and target for shooter to hit targetLoc
    public void update(Vector2D targetLoc, Vector2D location){
        //update shoot intervial
        shootInterval = shootInterval-- % 500;

        //calculate target to hit
        this.target = Vector2D.sub(targetLoc, location);
        if(this.target.mag() < 500){
            this.heading = this.target.heading();
            this.inRange = true;
        }
        else {
            this.inRange = false;
        }
    }

    public boolean getInRange(){
        return this.inRange;
    }

    public double getHeading(){
        return heading;
    }

    public Vector2D targetLoc(){
        return this.target; 
    }


    // public int getTimer(){
    //     return shootInterval; 
    // }

    // public void resetTimer(){
    //     shootInterval = 500;
    // }
}