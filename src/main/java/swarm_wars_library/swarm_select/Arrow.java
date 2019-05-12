package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class Arrow {
  private PApplet sketch;

  //=========================================================================//
  // Arrow constructor                                                       //
  //=========================================================================//
  public Arrow(PApplet sketch){
    this.sketch = sketch;
  }

  //=========================================================================//
  // Arrow update                                                            //
  //=========================================================================//
  public void update(Vector2D fromLocation, Vector2D toLocation){
    this.sketch.stroke(255, 255, 255);
    this.sketch.fill(255, 255, 255);
    float a = PApplet.dist((float) fromLocation.getX(), 
                           (float) fromLocation.getY(), 
                           (float) toLocation.getX(), 
                           (float) toLocation.getY()) / 50;
    this.sketch.pushMatrix();
    this.sketch.translate((float) toLocation.getX(), 
                          (float) toLocation.getY());
    this.sketch.rotate(PApplet.atan2((float) toLocation.getY()- 
                                     (float) fromLocation.getY(),
                                     (float) toLocation.getX() -
                                     (float) fromLocation.getX()));
    this.sketch.triangle(- a * 2 , - a, 0, 0, - a * 2, a);
    this.sketch.popMatrix();
    this.sketch.line((float) fromLocation.getX(), 
                     (float) fromLocation.getY(), 
                     (float) toLocation.getX(), 
                     (float) toLocation.getY());
  }
}
