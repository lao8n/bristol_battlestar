package swarm_wars_library.entities;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class Missile extends AbstractEntity{

  int missileForece;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public Missile(ENTITY tag, int missileForece){
    super(tag, Map.getInstance().getMissileScale());
    this.state = STATE.DEAD;
    this.missileForece = missileForece;
    this.updateCommsPacket();
    this.sendCommsPacket();  
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  @Override
  public void update(){
    if(this.isState(STATE.ALIVE)){
      this.updateMissile();
    }
    // Comms & explode last
    this.updateCommsPacket();
    this.sendCommsPacket();
    this.updateExplode2Dead();
  }

  //=========================================================================//
  // Missile method                                                          //
  //=========================================================================//
  public void updateMissile(){
    if(this.isState(STATE.ALIVE)){
      this.setLocation(Vector2D.add(this.getLocation(),
                                    this.getVelocity()));
    }
  }

  public void shootMissile(Vector2D location, double heading){
    this.setLocation(location);
    this.setHeading(heading);
    this.setVelocity(new Vector2D(this.missileForece * Math.cos(heading),
                                  this.missileForece * Math.sin(heading)));
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