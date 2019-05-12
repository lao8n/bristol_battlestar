package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public interface ISwarm{
  
  //=========================================================================//
  // Swarm methods                                                           //
  //=========================================================================//
  public void updateSwarm();
  public Vector2D getSwarmLocation();
  public double getSwarmHeading();
  public void setSwarmAlgorithm(SWARMALGORITHM swarmAlgorithm);
}