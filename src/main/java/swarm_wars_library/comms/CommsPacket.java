package swarm_wars_library.comms;

import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class CommsPacket {
  private Vector2D location;
  private STATE state;
  private Vector2D velocity;
  private int id;
  private int health;
  private int score;
  private int missileNum;
  private double motherShipHeading;
  private int moveLeft;
  private int moveRight;
  private int moveUp;
  private int moveDown;
  private double heading;
  private SWARMALGORITHM swarmAlgorithm;

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
    if(this.health>100){this.health=100;}
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

  public  void setMoveUp(int moveUp){
    this.moveUp = moveUp;
  }

  public int getMoveUp(){
    return this.moveUp;
  }

  public  void setMoveDown(int moveDown){
    this.moveDown = moveDown;
  }

  public int getMoveDown(){
    return this.moveDown;
  }
  
  public int getMoveLeft(){
    return this.moveLeft; 
  }

  public int getMoveRight(){
    return this.moveRight; 
  }

  public  void setMoveLeft(int moveLeft){
    this.moveLeft = moveLeft;
  }

  public  void setMoveRight(int moveRight){
    this.moveRight = moveRight;
  }

  public double getMotherShipHeading(){
    return this.motherShipHeading; 
  }

  public  void setMotherShipHeading(double heading){
    this.motherShipHeading = heading; 
  }

  public  void setMissileNum(int num){
    this.missileNum = num;
  }

  public  int getMissileNum(){
    return this.missileNum;
  }

  public double getHeading(){
    return  this.heading;
  }

  public void setHeading(double Heading)  {
    this.heading = Heading;
  }

  public SWARMALGORITHM getSwarmAlgorithm() {
    return swarmAlgorithm;
  }

  public void setSwarmAlgorithm(SWARMALGORITHM swarmAlgorithm) {
    this.swarmAlgorithm = swarmAlgorithm;
  }
}
