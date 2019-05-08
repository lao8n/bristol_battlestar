package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class Line {

  private PApplet sketch;
  private Vector2D startLocation;
  private Vector2D finishLocation;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Line constructor                                                        //
  //=========================================================================//
  public Line(PApplet sketch, Vector2D startLocation, Vector2D finishLocation, 
                int colourR, int colourG, int colourB) {
    this.sketch = sketch;
    this.startLocation = startLocation;
    this.finishLocation = finishLocation;
    this.colourR = colourR;
    this.colourG = colourG;
    this.colourB = colourB;
  }

  //=========================================================================//
  // Line update method                                                      //
  //=========================================================================//
  public void update(){
    // this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.stroke(this.colourR, this.colourG, this.colourB);
    this.sketch.line((float) this.startLocation.getX(), 
                     (float) this.startLocation.getY(),
                     (float) this.finishLocation.getX(), 
                     (float) this.finishLocation.getY());

  }

}