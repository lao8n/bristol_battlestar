package swarm_wars_library.entities;

import swarm_wars_library.physics.Vector2D;

public interface IInput{
  
  //=========================================================================//
  // Input methods                                                           //
  //=========================================================================//
  public void updateInput();
  public Vector2D getInputLocation();
  public double getInputHeading();
  public int getInputLeft();
  public int getInputRight();
  public int getInputUp();
  public int getInputDown();
  public int getInputMouseX();
  public int getInputMouseY();
  public int getInputMouseLeft();
  public int getInputMouseRight();
  public void setInputUp(int b);
  public void setInputDown(int b);
  public void setInputLeft(int b);
  public void setInputRight(int b);
  public void setInputMouseX(int mouseX);
  public void setInputMouseY(int mouseY);
  public void setInputMouseLeft(int input);
  public void setInputMouseRight(int input);
  public void listenKeyPressed(int keyCode);
  public void listenKeyReleased(int keyCode);
  public void listenMousePressed(boolean flag);
  public void listenMouseReleased();
  public void updateInputBuffer();
}