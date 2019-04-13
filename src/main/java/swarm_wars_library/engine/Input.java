package swarm_wars_library.engine;

import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.map.Map;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

import processing.core.PApplet;

/* Takes user input and applies it as forces to move the Entity */
public class Input {

  private PApplet sketch;
  private Vector2D location;
  private int moveForce;
  private int moveLeft, moveRight, moveUp, moveDown;
  private double heading;
  private int mouse;
  private Map map;

  public Input(PApplet sketch) {
    this.sketch = sketch;
    this.location = new Vector2D(sketch.width / 2, sketch.height / 2);
    this.moveForce = 6;
    this.heading = 0;
    map = Map.getInstance();
  }

  public void update(Vector2D view_centre) {
    location.setXY(location.getX() + 
                   (int) moveForce * (moveRight - moveLeft),
                   location.getY() + 
                   (int) moveForce * 0.8 * (moveDown - moveUp));

    heading = Math.atan2((sketch.mouseY + view_centre.getY() - sketch.height/2) 
                          - location.getY(), 
                         (sketch.mouseX + view_centre.getX() - sketch.width/2)
                          - location.getX());
    edgeCheck();
  }

  public Vector2D getLocation() {
    return location;
  }

  public double getHeading() {
    return heading;
  }

  public int setMove(int k, int b) {
    if (k == 'W' || k == 'w' || k == UP) {
      return moveUp = b;
    } else if (k == 'A' || k == 'a' || k == LEFT) {
      return moveLeft = b;
    } else if (k == 'S' || k == 's' || k == DOWN) {
      return moveDown = b;
    } else if (k == 'D' || k == 'd' || k == RIGHT) {
       return moveRight = b;      
    } else {
      return b;
    }
  }

  public void edgeCheck() {
    if (this.location.getX() < 0) {
      this.location.setX(0);
    } else if (this.location.getX() > this.map.getMapWidth()) {
      this.location.setX(this.map.getMapWidth());
    }
    if (this.location.getY() < 0) {
      this.location.setY(0);
    } else if (this.location.getY() > this.map.getMapHeight()) {
      this.location.setY(this.map.getMapHeight());
    }
  }

  public void setMouse(int input) {
    if(input==1){
      mouse=1;
    }
    else mouse=0;
  }

  public int getMouse(){
    return mouse;
  }
}
