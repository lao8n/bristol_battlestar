package swarm_wars_library.entities;

import java.util.List;
import java.util.ArrayList;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.engine.AIShooter;
import swarm_wars_library.engine.Shooter;
import swarm_wars_library.map.Map;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer; 


public class Turret extends AbstractEntity implements IAIShooter, ISound{

  private AIShooter aiShooter;
  private Shooter shooter;
  private int shootInterval = 0;
  private int bulletForce = Map.getInstance().getTurretBulletForce();

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================// 
  public Turret(ENTITY tag){
    super(tag, Map.getInstance().getTurretScale());
    this.aiShooter = new AIShooter();
    this.shooter = new Shooter(this.tag, bulletForce);
    this.setLocation(new Vector2D(RandomGen.getRand() * Map.getInstance()
                                                     .getMapWidth(), 
                                  RandomGen.getRand() * Map.getInstance()
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
      this.setLocation(new Vector2D(RandomGen.getRand() * Map.getInstance()
                                                       .getMapWidth(), 
                                    RandomGen.getRand() * Map.getInstance()
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
    this.aiShooter.update(this.getAITarget(), this.getAILocation());
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
    return this.aiShooter.getHeading();
  }

  //=========================================================================//
  // AI-Shooter methods                                                      //
  //=========================================================================//
  @Override
  public boolean isAIShoot(){
    if(this.shootInterval() && this.aiShooter.getInRange()){
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
    SoundMixer.playTurretExplosion();
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

      SoundMixer.turretShoot();
      this.shooter.shoot(this.getAILocation(), this.getAIHeading());
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