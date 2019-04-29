package swarm_wars_library.entities;

import java.util.List;

import swarm_wars_library.physics.Vector2D;

public interface IAI {
  //=========================================================================//
  // AI methods                                                              //
  //=========================================================================//
  public void updateAI();
  public Vector2D getAILocation();
  public List<Vector2D> getAITarget();
}