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
  private int moveLeftBuffer, moveRightBuffer, moveUpBuffer, moveDownBuffer;
  private double heading;
  private int mouseLeft, mouseRight;
  private int mouseLeftBuffer, mouseRightBuffer;
  private Map map;
  private int mouseX;
  private int mouseY;

  public Input(ENTITY tag, PApplet sketch) {
    this.sketch = sketch;
    this.map = Map.getInstance();
    this.location = this.map.getPlayerStartingLocation(tag);
    this.moveForce = Map.getInstance().getPlayerMoveForce();
    this.heading = 0;

    this.moveLeft = 0;
    this.moveRight = 0;
    this.moveUp = 0;
    this.moveDown = 0;
    this.mouseLeft = 0;
    this.mouseRight = 0;

    this.moveLeftBuffer = 0;
    this.moveRightBuffer = 0;
    this.moveUpBuffer = 0;
    this.moveDownBuffer = 0;
    this.mouseLeftBuffer = 0;
    this.mouseRightBuffer = 0;
  }

  public void update() {

    location.setXY(location.getX() + 
                   (int) moveForce * (moveRight - moveLeft),
                   location.getY() + 
                   (int) moveForce * (moveDown - moveUp));

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
    double comparativeMouseX = mouseX - this.sketch.width/2 - xAdj;
    double comparativeMouseY = mouseY - this.sketch.height/2 - yAdj;
    heading = Math.atan2(comparativeMouseY,
                         comparativeMouseX);
    edgeCheck();
  }

  public Vector2D getLocation() {
    return this.location;
  }

  public double getHeading() {
    return this.heading;
  }

  public int getMoveLeft(){
    return this.moveLeft;
  }

  public int getMoveRight(){
    return this.moveRight;
  }

  public int getMoveUp(){
    return this.moveUp;
  }

  public int getMoveDown(){
    return this.moveDown;
  }

  public int setMove(int k, int b) {
    if (k == 'W' || k == 'w' || k == UP) {
      return moveUp = b;
    } else if (k == 'S' || k == 's' || k == DOWN) {
      return moveDown = b;
    } else if (k == 'A' || k == 'a' || k == LEFT) {
      return moveLeft = b;
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

  public void setMouseLeft(int input) {
    if(input==1){
      this.mouseLeft=1;
    }
    else{
      this.mouseLeft=0;
    }
  }

  public void setMouseRight(int input) {
    if(input==1){
      mouseRight=1;
    }
    else mouseRight=0;
  }

  public int getMouseX() {
    return mouseX;
  }

  public void setMouseX(int mouseX) {
    this.mouseX = mouseX;
  }

  public int getMouseY() {
    return mouseY;
  }

  public void setMouseY(int mouseY) {
    this.mouseY = mouseY;
  }

  // BUFFER SYSTEM TO SYNC MULTIPLAYER

  public int setMoveBuffer(int k, int b) {
    if (k == 'W' || k == 'w' || k == UP) {
      return moveUpBuffer = b;
    } else if (k == 'S' || k == 's' || k == DOWN) {
      return moveDownBuffer = b;
    } else if (k == 'A' || k == 'a' || k == LEFT) {
      return moveLeftBuffer = b;
    } else if (k == 'D' || k == 'd' || k == RIGHT) {
      return moveRightBuffer = b;
    } else {
      return b;
    }
  }

  public void setMoveLeftBuffer(int moveLeftBuffer) {
    this.moveLeftBuffer = moveLeftBuffer;
  }

  public void setMoveRightBuffer(int moveRightBuffer) {
    this.moveRightBuffer = moveRightBuffer;
  }

  public int getMouseLeft(){
    return mouseLeft;
  }

  public int getMouseRight(){
    return mouseRight;
  }

  public void setMoveUpBuffer(int moveUpBuffer) {
    this.moveUpBuffer = moveUpBuffer;
  }

  public void setMoveDownBuffer(int moveDownBuffer) {
    this.moveDownBuffer = moveDownBuffer;
  }

  public void setMouseLeftBuffer(int mouseLeftBuffer) {
    this.mouseLeftBuffer = mouseLeftBuffer;
  }

  public void setMouseRightBuffer(int mouseRightBuffer){
    this.mouseRightBuffer = mouseRightBuffer;
  }

  public void updateBuffer() {
    this.moveUp = this.moveUpBuffer;
    this.moveDown = this.moveDownBuffer;
    this.moveLeft = this.moveLeftBuffer; 
    this.moveRight = this.moveRightBuffer;
    this.mouseLeft = this.mouseLeftBuffer;
    this.mouseRight = this.mouseRightBuffer;
  }
}
