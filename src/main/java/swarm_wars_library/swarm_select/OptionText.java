package swarm_wars_library.swarm_select;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;

import swarm_wars_library.physics.Vector2D;

public class OptionText {

  private PApplet sketch;
  private String label;
  private Vector2D topLeftLocation;
  private Vector2D dimensions;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Option Text constructor                                                 //
  //=========================================================================//
  public OptionText(PApplet sketch, String label, Vector2D topLeftLocation, 
      Vector2D dimensions, int colourR, int colourG, int colourB){
    this.sketch = sketch;
    this.label = label;
    this.topLeftLocation = topLeftLocation;
    this.dimensions = dimensions;
    this.colourR = colourR;
    this.colourG = colourG;
    this.colourB = colourB;
  }

  //=========================================================================//
  // Option Text update method                                               //
  //=========================================================================//
  public void update() {
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.topLeftLocation.getX(), 
                          (float) this.topLeftLocation.getY());
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.rotate(PApplet.radians(270));
    this.sketch.textAlign(CENTER);
    this.sketch.text(this.label, 
                    (float) - this.dimensions.getX() / 2, 
                    (float) 0);
    this.sketch.popMatrix();
  }
}