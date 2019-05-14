package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface IInput{
  
  //=========================================================================//
  // Input methods                                                           //
  //=========================================================================//
  public void updateInput();
  public Vector2D getInputLocation();
  public double getInputHeading();
  public boolean getInputLeft();
  public boolean getInputRight();
  public boolean getInputUp();
  public boolean getInputDown();
  public void listenKeyPressed(int keyCode);
  public void listenKeyReleased(int keyCode);
  public void listenMousePressed();
  public void listenMouseReleased();
}