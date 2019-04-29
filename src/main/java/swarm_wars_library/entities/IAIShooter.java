package swarm_wars_library.entities;

public interface IAIShooter extends IShooter, IAI{
  
  //=========================================================================//
  // Shooter-AI methods                                                   //
  //=========================================================================//
  public boolean isAIShoot();
  public boolean shootInterval();
}