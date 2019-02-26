package swarm_wars_library.engine;

import swarm_wars_library.engine.Vector2D;

import processing.core.PApplet;

/* Takes user input and applies it as forces to move the Entity */
public class Input {

  private PApplet sketch;
  private Vector2D location;
  private int moveForce;
  private int moveLeft, moveRight, moveUp, moveDown;
  private double heading;
  
  public Input(PApplet sketch){
    this.sketch = sketch;
    this.location = new Vector2D(sketch.width/2, sketch.height/2);
    this.moveForce = 6;
    this.heading = 0;
  }
  
  public void update(){
	this.location.setXY(this.location.getX() + (int) this.moveForce *
                                        (this.moveRight - this.moveLeft),
                        this.location.getY() + (int) this.moveForce * 0.8 * 
                                        (this.moveForce * (this.moveDown - this.moveUp)));
    this.heading = Math.atan2( this.sketch.mouseY - this.location.getY(),
                               this.sketch.mouseX - this.location.getX());
    edgeCheck();
  }

  public Vector2D getLocation(){
      return location;
  }

  public double heading(){
      return heading; 
  }

  public int setMove(int k, int b){
    if(k == 'W' || k == 'w' || k == sketch.UP){
      return moveUp = b;
    }
    else if (k == 'A' || k == 'a' || k == sketch.LEFT){
      return moveLeft = b;
    }
    else if (k == 'S' || k == 's' || k == sketch.DOWN){
      return moveDown = b;
    }
    else if (k == 'D' || k == 'd' || k == sketch.RIGHT){
      return moveRight = b;
    }
    else {
      return b;
    }
  }
  
  public void edgeCheck(){
    if (this.location.getX() < 0){
      this.location.setX(0);
    } else if (this.location.getX() > this.sketch.width){
      this.location.setX(this.sketch.width);
    }
    if (this.location.getY() < 0) {
      this.location.setY(0);
    } else if (this.location.getY() > this.sketch.height){
      this.location.setY(this.sketch.height); 
    }
  }
}
