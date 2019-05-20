package swarm_wars_library.fsm_ui_old;

import processing.core.PApplet;

import swarm_wars_library.physics.Vector2D;

public class Arrow {
  private PApplet sketch;
  private Vector2D fromLocation;
  private Vector2D toLocation;

  //=========================================================================//
  // Arrow constructor                                                       //
  //=========================================================================//
  public Arrow(PApplet sketch, Vector2D fromLocation, Vector2D toLocation){
    this.sketch = sketch;
    this.fromLocation = fromLocation;
    this.toLocation = toLocation;
  }

  //=========================================================================//
  // Arrow update                                                            //
  //=========================================================================//
  public void update(){
    this.sketch.stroke(0, 235, 255);
    this.sketch.strokeWeight(5);
    this.sketch.fill(0, 235, 255);
    float a = PApplet.dist((float) this.fromLocation.getX(), 
                           (float) this.fromLocation.getY(), 
                           (float) this.toLocation.getX(), 
                           (float) this.toLocation.getY()) / 50;
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.toLocation.getX(), 
                          (float) this.toLocation.getY());
    this.sketch.rotate(PApplet.atan2((float) this.toLocation.getY()- 
                                     (float) this.fromLocation.getY(),
                                     (float) this.toLocation.getX() -
                                     (float) this.fromLocation.getX()));
    this.sketch.triangle(- a * 2 , - a, 0, 0, - a * 2, a);
    this.sketch.popMatrix();
    this.sketch.line((float) this.fromLocation.getX(), 
                     (float) this.fromLocation.getY(), 
                     (float) this.toLocation.getX(), 
                     (float) this.toLocation.getY());
  }
}
