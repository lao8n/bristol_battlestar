package swarm_wars_library.game_screens;

import swarm_wars_library.game_screens.GAMESCREEN;

import processing.core.PApplet;

import static processing.core.PConstants.*;

public class StartText {
  
  private PApplet sketch;
  private GAMESCREEN currentScreen;
  private String lines = "TEST";
  private float y = 0;
  private float width = 1000;
  private float height = 1000;

  public StartText(PApplet sketch){
    this.sketch = sketch;
    this.currentScreen = GAMESCREEN.START;
    y = height/2;
  }


  //=========================================================================//
  // Update method                                                           //
  //=========================================================================//
  public void update(){
    this.sketch.background(0);
    this.sketch.translate(width/2, height/2);

    this.sketch.fill(255,255,0);
    this.sketch.textSize(25);
    this.sketch.textAlign(CENTER);
    this.sketch.rotateX(PI/4);
    this.sketch.text(lines, -width/2, y, width, height*10);

    y-=2;
  }

  //=========================================================================//
  // Game screen method                                                      //
  //=========================================================================//
  public GAMESCREEN getGameScreen(){
    return this.currentScreen;
  }

}
