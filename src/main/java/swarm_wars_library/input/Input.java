package swarm_wars_library.input;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.physics.Vector2D;
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

  public Input(ENTITY tag, PApplet sketch) {
    this.sketch = sketch;
    this.map = Map.getInstance();
    this.location = this.map.getPlayerStartingLocation(tag);
    this.moveForce = 6;
    this.heading = 0;
  }

  public void update() {

    location.setXY(location.getX() + 
                   (int) moveForce * (moveRight - moveLeft),
                   location.getY() + 
                   (int) moveForce * 0.8 * (moveDown - moveUp));

    // We want to do the comparison between the player and the mouseXY
    // purely in terms of actual map coordinates. 
    // Player coordinate is simple location
    // However mouseXY is complicated because rendering depends upon whether 
    // close to edge of map.

    double xAdj = 0;
    double yAdj = 0;
    if(map.getMapWidth() - location.getX() < sketch.width/2){
      xAdj = sketch.width/2 - map.getMapWidth() + location.getX();
    }
    else if(location.getX() < sketch.width/2){
      xAdj = - sketch.width/2 + location.getX();
    }
    if(map.getMapHeight() - location.getY() < sketch.height/2){
      yAdj = sketch.height/2 - map.getMapHeight() + location.getY();
    }
    else if(location.getY() < sketch.height/2){
      yAdj = - sketch.height/2 + location.getY();
    }
    double comparativeMouseX= sketch.mouseX - this.sketch.width/2 - xAdj;
    double comparativeMouseY = sketch.mouseY - this.sketch.height/2 - yAdj;
    heading = Math.atan2(comparativeMouseY,
                         comparativeMouseX);
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

  public int getMoveLeft() {
    return moveLeft;
  }

  public int getMoveRight() {
    return moveRight;
  }

  public int getMoveUp() {
    return moveUp;
  }

  public int getMoveDown() {
    return moveDown;
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
