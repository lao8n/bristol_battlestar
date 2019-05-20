package swarm_wars_library.fsm_ui_old;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class Circle {

  private PApplet sketch;
  private Vector2D location;
  private Vector2D dimensions;
  private int colourR = 0;
  private int colourG = 237;
  private int colourB = 255;

  //=========================================================================//
  // Circle constructor                                                      //
  //=========================================================================//
  public Circle(PApplet sketch, Vector2D location, Vector2D dimensions) {
    this.sketch = sketch;
    this.location = location;
    this.dimensions = dimensions;
  }

  //=========================================================================//
  // Circle update method                                                    //
  //=========================================================================//
  public void update() {
    //glow effect
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 30);
    this.sketch.ellipse((float) this.location.getX() + 6, 
                        (float) this.location.getY() + 6, 
                        (float) this.dimensions.getX(), 
                        (float) this.dimensions.getY());

    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 50);
    this.sketch.ellipse((float) this.location.getX()+4, 
                        (float) this.location.getY()+4,
                        (float) this.dimensions.getX(), 
                        (float) this.dimensions.getY());

    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 60);
    this.sketch.ellipse((float) this.location.getX()+2, 
                        (float) this.location.getY()+2,
                        (float) this.dimensions.getX(), 
                        (float) this.dimensions.getY());

    //inner colour
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.ellipse((float) this.location.getX(), 
                        (float) this.location.getY(),
                        (float) this.dimensions.getX(), 
                        (float) this.dimensions.getY());
  }

  //=========================================================================//
  // Circle change methods                                                   //
  //=========================================================================//
  public void changeColour() {
    if (this.colourR == 0) {
        this.colourR = 255;
        this.colourG = 0;
        this.colourB = 199;
    }
    else {
        this.colourR = 0;
        this.colourG = 237;
        this.colourB = 255;
    }
  }
}