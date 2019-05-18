package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class TextExplanation {

  private PApplet sketch;
  private String label;
  private Vector2D topLeftLocation;
  private Vector2D dimensions;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Text Explanation constructor                                            //
  //=========================================================================//
  public TextExplanation(PApplet sketch, String label, Vector2D topLeftLocation, 
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
  // Text Explanation update method                                          //
  //=========================================================================//
  public void update() {
    this.sketch.stroke(255, 255, 255);
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 90);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.dimensions.getX(), 
                     (float) this.dimensions.getY());

    this.sketch.fill(255);
    this.sketch.text(this.label, 
                    (float) this.topLeftLocation.getX() + 20, 
                    (float) this.topLeftLocation.getY() + 20);
  }

  public void setLabel(String label){
    this.label = label;
  }
}