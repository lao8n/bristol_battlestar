
/* For enemy ai movement - the enemy Mothership */
package swarm_wars_library.engine;

class AI {

    static int shootInterval = 500; 
    double heading = 50;
    Vector2D target = new Vector2D(0, 0);
    boolean inRange = false;

    //calculates heading and target for shooter to hit targetLoc
    public void update(Vector2D targetLoc, Transform trans){
        //update shoot intervial
        shootInterval = shootInterval-- % 500;

        //calculate target to hit
        target = Vector2D.sub(targetLoc, trans.getPosition());
        if(target.mag() < 500){
            this.heading = target.heading();
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
        return target; 
    }


    public int getTimer(){
        return shootInterval; 
    }

    public void resetTimer(){
        shootInterval = 500;
    }
}