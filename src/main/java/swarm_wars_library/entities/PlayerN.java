package swarm_wars_library.entities;

import java.util.ArrayList;

import processing.core.PApplet;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.engine.Health;
import swarm_wars_library.engine.Shooter;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.input.Input;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

import static processing.core.PConstants.DOWN;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.UP;

public class PlayerN extends AbstractEntity implements IHealth, IInputShooter,
  IScore, ISound{

  private Health health;
  private Input input;
  private int score;
  private Shooter shooter;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public PlayerN(PApplet sketch, ENTITY tag){
    super(tag, Map.getInstance().getPlayerScale());
    this.setLocation(Map.getInstance().getPlayerStartingLocation(this.tag));
    this.health = new Health(this.tag);
    this.input = new Input(this.tag, sketch);
    this.score = 0;
    this.shooter = new Shooter(this.tag, 10);
    this.updateCommsPacket();
    this.sendCommsPacket();  
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  @Override
  public void update(){
    if(this.isState(STATE.ALIVE)){
      this.updateHealth();
      this.updateInput();
      this.updateShooter();
      this.updateScore();
      this.updateState();
    }
    // Comms & explode last
    this.updateCommsPacket();
    this.sendCommsPacket();
    this.updateExplode2Dead();
  }

  //=========================================================================//
  // Comms method                                                            //
  //=========================================================================//
  @Override
  public void updateCommsPacket(){
    this.commsPacket.setHealth(this.getHealth());
    this.commsPacket.setLocation(this.getLocation());
    this.commsPacket.setScore(this.getScore());
    this.commsPacket.setState(this.getState());
    this.commsPacket.setVelocity(this.getVelocity());
    this.commsPacket.setMotherShipHeading(this.getHeading());
    this.commsPacket.setMoveLeft(this.getInputLeft());
    this.commsPacket.setMoveRight(this.getInputRight());
    this.commsPacket.setMoveUp(this.getInputUp());
    this.commsPacket.setMoveDown(this.getInputDown());
  }

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  @Override
  public void collidedWith(ENTITY tag){
    this.takeDamage(5);
  }

  //=========================================================================//
  // Health methods                                                          //
  //=========================================================================//
  @Override
  public void updateHealth(){
    this.health.update();
  }

  @Override
  public int getHealth(){
    return this.health.getCurrentHealth();
  }

  @Override
  public void updateState(){
    if(health.isDead()){
      this.state = STATE.EXPLODE;
    }
  }

  @Override
  public void takeDamage(int damage){
    this.health.takeDamage(damage);
  }

  //=========================================================================//
  // Input methods                                                           //
  //=========================================================================//
  @Override
  public void updateInput(){
    this.input.update();
    this.setLocation(this.getInputLocation());
    this.setHeading(this.getInputHeading());
  }

  @Override
  public Vector2D getInputLocation(){
    return this.input.getLocation();
  }

  @Override
  public double getInputHeading(){
    return this.input.getHeading();
  }

  @Override
  public int getInputLeft(){
    return this.input.getMoveLeft();
  }

  @Override
  public int getInputRight(){
    return this.input.getMoveRight();
  }

  @Override 
  public int getInputUp(){
    return this.input.getMoveUp();
  }

  @Override 
  public int getInputDown(){
    return this.input.getMoveDown();
  }

  @Override
  public int getInputMouseX(){
    return this.input.getMouseX();
  }

  @Override
  public int getInputMouseY(){
    return this.input.getMouseY();
  }

  @Override
  public int getInputMouse(){
    return this.input.getMouse();
  }

  @Override
  public void setInputUp(int b){
    this.input.setMove(UP, b);
  }

  @Override
  public void setInputDown(int b){
    this.input.setMove(DOWN, b);
  }

  @Override
  public void setInputLeft(int b){
    this.input.setMove(LEFT, b);
  }

  @Override
  public void setInputRight(int b){
    this.input.setMove(RIGHT, b);
  }

  @Override
  public void setInputMouseX(int mouseX) {
    this.input.setMouseX(mouseX);
  }

  @Override
  public void setInputMouseY(int mouseY) {
    this.input.setMouseY(mouseY);
  }

  @Override
  public void setInputMouse(int input) {
    this.input.setMouse(input);
  }


  //=========================================================================//
  // Input listeners                                                         //
  //=========================================================================//

  @Override
  public void listenKeyPressed(int keyCode) {
    this.input.setMoveBuffer(keyCode, 1);
  }

  @Override
  public void listenKeyReleased(int keyCode) {
    this.input.setMoveBuffer(keyCode, 0);
  }

  @Override
  public void listenMousePressed() {
    this.input.setMouseBuffer(1);
  }

  @Override
  public void listenMouseReleased() {
    this.input.setMouseBuffer(0);
  }

  @Override
  public void updateInputBuffer() {
    this.input.updateBuffer();
  }


  //=========================================================================//
  // Input-Shooter methods                                                   //
  //=========================================================================//
  @Override
  public boolean isInputShoot(){
    if(this.input.getMouse() == 1){
      return true;
    }
    return false;
  }

  //=========================================================================//
  // Score methods                                                           //
  //=========================================================================//
  public void updateScore(){
    this.setScore(CommsGlobal.get(this.tag.toString())
                             .getPacket(0)
                             .getScore());
  }

  public int getScore(){
    return this.score;
  }

  public void setScore(int score){
    this.score = score;
  }

  //=========================================================================//
  // Shooter methods                                                         //
  //=========================================================================//
  @Override
  public void updateShooter(){
    this.shooter.update();
    this.shoot();
  }

  @Override
  public void shoot(){
    if(isInputShoot()){
      this.shooter.shoot(this.getLocation(), this.getHeading());
    }
  }

  @Override
  public ArrayList<Bullet> getBullets(){
    return this.shooter.getMagazine();
  }

  //=========================================================================//
  // Sound methods                                                           //
  //=========================================================================//
  @Override
  public void updateSounds(){
    // TODO add conditional sound
  }

}
