package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface IEntity {
  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update();

  //=========================================================================//
  // Collision method                                                        //
  //=========================================================================//
  public void collidedWith(ENTITY tag);

  //=========================================================================//
  // State methods                                                           //
  //=========================================================================//

  public boolean isState(STATE state);
  public STATE getState();
  public void setState(STATE state);
  public void updateExplode2Dead();

  //=========================================================================//
  // Tag method                                                              //
  //=========================================================================//
  public ENTITY getTag();

  //=========================================================================//
  // Transform getters and setter methods                                    //
  //=========================================================================//
  public Vector2D getLocation();
  public double getScale();
}