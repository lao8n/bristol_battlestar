package swarm_wars_library.entities;

import java.util.ArrayList;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.engine.AI;
import swarm_wars_library.engine.Shooter;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class Turret extends AbstractEntity implements IShooterAI{

  private AI ai;
  private Shooter shooter;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================// 
  public Turret(ENTITY tag){
    super(tag, Map.getInstance().getTurretScale());
    this.ai = new AI();
    this.shooter = new Shooter(this.tag, 5);
    this.setLocation(new Vector2D(Math.random() * Map.getInstance()
                                                     .getMapWidth(), 
                                  Math.random() * Map.getInstance()
                                                     .getMapHeight()));
    this.updateCommsPacket();
    this.sendCommsPacket();  
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  @Override
  public void update(){
    if(this.isState(STATE.ALIVE)){
      this.updateAI();
      this.updateShooter();
    }
    if(this.isState(STATE.DEAD)){
      this.setState(STATE.ALIVE);
      this.setLocation(new Vector2D(Math.random() * Map.getInstance()
                                                       .getMapWidth(), 
                                    Math.random() * Map.getInstance()
                                                       .getMapHeight()));
    }
    // Comms & explode last
    this.updateCommsPacket();
    this.sendCommsPacket();
    this.updateExplode2Dead();
  }

  //=========================================================================//
  // AI methods                                                              //
  //=========================================================================//
  @Override
  public void updateAI(){
    this.ai.update(this.getAITarget(), this.getAILocation());
  }

  @Override
  public Vector2D getAILocation(){
    return this.getLocation();
  }

  @Override
  public Vector2D getAITarget(){
    // TODO make it look for closer of Player1 or Player2
    return CommsGlobal.get("PLAYER1").getPacket(0).getLocation();
  }

  public double getAIHeading(){
    return this.ai.getHeading();
  }

  //=========================================================================//
  // Comms method                                                            //
  //=========================================================================//
  @Override
  public void updateCommsPacket(){
    this.commsPacket.setLocation(this.getLocation());
    this.commsPacket.setState(this.getState());
    this.commsPacket.setVelocity(this.getVelocity());
  }

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  @Override
  public void collidedWith(ENTITY tag){
    this.setState(STATE.EXPLODE);
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
    if(isAIShoot()){
      this.shooter.shoot(this.getAILocation(), this.getAIHeading());
    }
  }

  @Override
  public ArrayList<Bullet> getBullets(){
    return this.shooter.getMagazine();
  }

  //=========================================================================//
  // Shooter-AI methods                                                   //
  //=========================================================================//
  public boolean isAIShoot(){
    if(this.ai.getInRange()){
      return true;
    }
    return false;
  }
}