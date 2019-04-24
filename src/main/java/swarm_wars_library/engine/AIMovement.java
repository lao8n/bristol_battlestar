package swarm_wars_library.engine;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.map.Map;

public class AIMovement{

  private Vector2D location;
  private int moveForce;
  private double moveRight, moveDown;
  private Map map;
  private int movementCount = 0;

  public AIMovement(ENTITY tag){
    this.map = Map.getInstance();
    this.location = this.map.getPlayerStartingLocation(tag);
    this.moveForce = 5;
  }

  public void update(){
    // go in random new direction every 10s
    if(this.movementCount % 60 == 0){
      this.moveRight = -1 + 2 * Math.random();
      this.moveDown = -1 + 2 * Math.random();
      this.movementCount = 0;
    }
    this.location.setXY(this.location.getX() + 
                        (int) this.moveForce * this.moveRight,
                        this.location.getY() + 
                        (int) this.moveForce * 0.8 * this.moveDown);
    this.edgeCheck();
    this.movementCount++;
  }

  public Vector2D getLocation(){
    return this.location;
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
}