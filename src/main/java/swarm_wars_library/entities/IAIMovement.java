package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface IAIMovement{
  
  //=========================================================================//
  // Movement methods                                                        //
  //=========================================================================//
  public void updateAIMovement();
  public Vector2D getAIMovementLocation();
}