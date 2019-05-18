package swarm_wars_library.game_screens;

import swarm_wars_library.game_screens.GAMESCREEN;

import processing.core.PApplet;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

public class StartText {
  
  private PApplet sketch;
  private GAMESCREEN currentScreen;

  public StartText(PApplet sketch){
    this.sketch = sketch;
    this.currentScreen = GAMESCREEN.START;
  }


  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.sketch.fill(255, 235, 32);
    this.sketch.textSize(30);
    this.sketch.textAlign(LEFT, TOP);
    this.sketch.text("SCORE: ", 5, 55);  }

  //=========================================================================//
  // Game screen method                                                      //
  //=========================================================================//
  public GAMESCREEN getGameScreen(){
    return this.currentScreen;
  }

}