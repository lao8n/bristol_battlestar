package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class Circle {

  private PApplet sketch;
  private Vector2D location;
  private Vector2D dimensions;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Circle constructor                                                      //
  //=========================================================================//
  public Circle(PApplet sketch, Vector2D location, Vector2D dimensions, 
                int colourR, int colourG, int colourB) {
    this.sketch = sketch;
    this.location = location;
    this.dimensions = dimensions;
    this.colourR = colourR;
    this.colourG = colourG;
    this.colourB = colourB;
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

    
    //outer colour
    this.sketch.noStroke();
    this.sketch.fill(this.colourR - 30, this.colourG - 30, this.colourB - 30);
    this.sketch.ellipse((float) this.location.getX(), 
                        (float) this.location.getY(),
                        (float) this.dimensions.getX(), 
                        (float) this.dimensions.getY());

    //inner colour
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.ellipse((float) this.location.getX(), 
                        (float) this.location.getY(),
                        (float) this.dimensions.getX() - 10, 
                        (float) this.dimensions.getY() - 10);
  }
}