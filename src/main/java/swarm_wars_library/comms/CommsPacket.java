package swarm_wars_library.comms;

import swarm_wars_library.physics.Vector2D;

import javax.sound.sampled.BooleanControl;

import swarm_wars_library.entities.STATE;

public class CommsPacket {
  private Vector2D location;
  private STATE state;
  private Vector2D velocity;
  private int id;
  private int health;
  private int score;
  private double motherShipHeading;
  private boolean moveLeft;
  private boolean moveRight;
  private boolean moveUp;
  private boolean moveDown;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLocation(Vector2D location) {
    this.location = location;
  }

  public Vector2D getLocation() {
    return this.location;
  }

  public Vector2D getVelocity(){
    return this.velocity;
  }

  public void setVelocity(Vector2D velocity){
    this.velocity = velocity;
  }

  public int getHealth(){
    return this.health;
  }
  public void setHealth(int health){
    this.health = health;
  }

  public int getScore(){
    return this.score;
  }

  public void setScore(int score){
    this.score = score;
  }

  public void addScore(int score){
    this.score += score;
  }

  public STATE getState(){
    return this.state;
  }

  public void setState(STATE state){
    this.state = state;
  }
  
  public boolean getMoveLeft(){
    return this.moveLeft; 
  }

  public boolean getMoveRight(){
    return this.moveRight; 
  }

  public  void setMoveLeft(boolean moveLeft){
    this.moveLeft = moveLeft;
  }

  public  void setMoveRight(boolean moveRight){
    this.moveRight = moveRight;
  }

  public double getMotherShipHeading(){
    return this.motherShipHeading; 
  }

  public  void setMotherShipHeading(double heading){
    this.motherShipHeading = heading; 
  }

  public  void setMoveUp(boolean moveUp){
    this.moveUp = moveUp;
  }

  public boolean getMoveUp(){
    return this.moveUp; 
  }

  public  void setMoveDown(boolean moveDown){
    this.moveDown = moveDown;
  }

  public boolean getMoveDown(){
    return this.moveDown; 
  }

}
