package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface ISwarm{
  
  //=========================================================================//
  // Swarm methods                                                           //
  //=========================================================================//
  public void updateSwarm();
  public Vector2D getSwarmLocation();
  public double getSwarmHeading();
}