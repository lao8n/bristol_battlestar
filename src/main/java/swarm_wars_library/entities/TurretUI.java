package swarm_wars_library.entities;

import swarm_wars_library.map.Map;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Vector2D;

public class TurretUI extends AbstractEntity{

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================// 
  public TurretUI(ENTITY tag){
    super(tag, Map.getInstance().getTurretScale());
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
}