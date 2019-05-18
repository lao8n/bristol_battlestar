
package swarm_wars_library.engine;

import java.util.List;
import java.util.Collections;

import swarm_wars_library.physics.Vector2D;

public class AIShooter {

  double heading = 50;
  Vector2D target = new Vector2D(0, 0);
  boolean inRange = false;

  //calculates heading and target for shooter to hit targetLoc
  public void update(List<Vector2D> targetLocs, Vector2D location){
    // randomly choose who to shoot first
    Collections.shuffle(targetLocs);
    for(Vector2D targetLoc : targetLocs){
      //calculate target to hit
      this.target = Vector2D.sub(targetLoc, location);
      if(this.target.mag() < 500){
        this.heading = this.target.heading();
        this.inRange = true;
        break;
      }
      else {
        this.inRange = false;
      } 
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
}