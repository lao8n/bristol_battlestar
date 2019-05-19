package swarm_wars_library.entities;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class Bullet extends AbstractEntity{

  int bulletForce; 

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public Bullet(ENTITY tag, int bulletForce){
    super(tag, Map.getInstance().getBulletScale());
    this.state = STATE.DEAD;
    this.bulletForce = bulletForce;
    this.updateCommsPacket();
    this.sendCommsPacket();  
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  @Override
  public void update(){
    if(this.isState(STATE.ALIVE)){
      this.updateBullet();
    }
    // Comms & explode last
    this.updateCommsPacket();
    this.sendCommsPacket();
    this.updateExplode2Dead();
  }

  //=========================================================================//
  // Bullet method                                                           //
  //=========================================================================//
  public void updateBullet(){
    if(this.isState(STATE.ALIVE)){
      this.setLocation(Vector2D.add(this.getLocation(),
                                    this.getVelocity()));
    }
  }

  public void shootBullet(Vector2D location, double heading){
    this.setLocation(location);
    this.setHeading(heading);
    this.setVelocity(new Vector2D(this.bulletForce * Math.cos(heading),
                                  this.bulletForce * Math.sin(heading)));
    this.setState(STATE.ALIVE);
  }

  //=========================================================================//
  // Comms method                                                            //
  //=========================================================================//
  @Override
  public void updateCommsPacket(){
    this.commsPacket.setState(this.getState());
    this.commsPacket.setLocation(this.getLocation());
    this.commsPacket.setVelocity(this.getVelocity());
    this.commsPacket.setHeading(this.getHeading());
  }

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  @Override
  public void collidedWith(ENTITY tag){
    this.setState(STATE.EXPLODE);
    if(tag.equals(ENTITY.TURRET)){
      CommsGlobal.get(Tag.getShooterTag(this.tag).toString())
                 .getPacket(0)
                 .addScore(10);
    }
  }
}