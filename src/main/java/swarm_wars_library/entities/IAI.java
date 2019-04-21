package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface IAI {
  //=========================================================================//
  // AI methods                                                              //
  //=========================================================================//
  public void updateAI();
  public Vector2D getAILocation();
  public Vector2D getAITarget();
}