package swarm_wars_library.entities;

import java.util.ArrayList;

public interface IShooter{
  
  //=========================================================================//
  // Shooter methods                                                         //
  //=========================================================================//
  public void updateShooter();
  public void shoot();
  public ArrayList<Bullet> getBullets();
}