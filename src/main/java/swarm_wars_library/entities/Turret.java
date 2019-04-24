package swarm_wars_library.entities;

import java.util.List;
import java.util.ArrayList;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.engine.AI;
import swarm_wars_library.engine.Shooter;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class Turret extends AbstractEntity implements IAIShooter{

  private AI ai;
  private Shooter shooter;
  private int shootInterval = 0;

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
  public List<Vector2D> getAITarget(){
    // TODO make it look for closer of Player1 or Player2
    List<Vector2D> aiTargets = new ArrayList<Vector2D>();
    if(CommsGlobal.get("PLAYER1").getPacket(0).getState().equals(STATE.ALIVE)){
      aiTargets.add(CommsGlobal.get("PLAYER1").getPacket(0).getLocation());
    }
    if(CommsGlobal.get("PLAYER2").getPacket(0).getState().equals(STATE.ALIVE)){
      aiTargets.add(CommsGlobal.get("PLAYER2").getPacket(0).getLocation());
    }
    return aiTargets;
  }

  public double getAIHeading(){
    return this.ai.getHeading();
  }

  //=========================================================================//
  // AI-Shooter methods                                                      //
  //=========================================================================//
  @Override
  public boolean isAIShoot(){
    if(this.shootInterval() && this.ai.getInRange()){
      return true;
    }
    return false;
  }

  @Override
  public boolean shootInterval(){
    if(this.shootInterval % 5 == 0){
      this.shootInterval = 0;
      return true;
    }
    return false;
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
    this.shootInterval++;
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
}