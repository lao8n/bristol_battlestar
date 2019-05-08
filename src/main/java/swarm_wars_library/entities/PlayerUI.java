package swarm_wars_library.entities;

import processing.core.PApplet;

import swarm_wars_library.engine.AIMovement;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.map.Map;
import swarm_wars_library.physics.Vector2D;

public class PlayerUI extends AbstractEntity implements IAIMovement {
  private AIMovement aiMovement;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public PlayerUI(PApplet sketch, ENTITY tag){
    super(tag, Map.getInstance().getPlayerScale());
    this.aiMovement = new AIMovement(this.tag);
    this.setLocation(Map.getInstance().getPlayerStartingLocation(this.tag));
    this.updateCommsPacket();
    this.sendCommsPacket();  
  }

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  @Override
  public void collidedWith(ENTITY tag){
    // NA
  }

  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  @Override
  public void update(){
    this.updateAIMovement();

    // Comms & explode last
    this.updateCommsPacket();
    this.sendCommsPacket();
  }

  //=========================================================================//
  // AI Movement methods                                                     //
  //=========================================================================//
  @Override
  public void updateAIMovement(){
    this.aiMovement.update();
    this.setLocation(this.getAIMovementLocation());
  }

  @Override
  public Vector2D getAIMovementLocation(){
    return this.aiMovement.getLocation();
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

}