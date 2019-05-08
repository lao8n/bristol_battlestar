package swarm_wars_library.swarm_select;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;

import swarm_wars_library.physics.Vector2D;

public class Label {
  private PApplet sketch;
  private int colour;
  private String label;
  private Vector2D location;

  //=========================================================================//
  // Label constructor                                                       //
  //=========================================================================//
  public Label(PApplet sketch, int colour, String label, Vector2D location){
    this.sketch = sketch;
    this.colour = colour;
    this.label = label;
    this.location = location;
  }

  //=========================================================================//
  // Label update                                                            //
  //=========================================================================//
  public void update(){
    this.sketch.textAlign(CENTER, CENTER);
    this.sketch.fill(colour);
    this.sketch.text(label, 
                     (float) this.location.getX(), 
                     (float) this.location.getY());    
  }

  //=========================================================================//
  // Label methods                                                           //
  //=========================================================================//
  public void changeLabel(String newLabel) {
    this.label = newLabel;
  }

  public String getLabelString(){
    return this.label;
  }
}