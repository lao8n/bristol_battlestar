package swarm_wars_library.fsm_ui;

import processing.core.PApplet;
import static processing.core.PConstants.CLOSE;
import static processing.core.PConstants.TWO_PI;
import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;

import swarm_wars_library.physics.Vector2D;

public class Star {
  private PApplet sketch;
  private Vector2D location;
  private Vector2D dimensions;
  private int nPoints;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Star constructor                                                        //
  //=========================================================================//
  public Star (PApplet sketch, Vector2D location, Vector2D dimensions, 
               int nPoints, int colourR, int colourG, int colourB) {
    this.sketch = sketch;
    this.location = location;
    this.dimensions = dimensions;
    this.nPoints = nPoints;
    this.colourR = colourR;
    this.colourG = colourG;
    this.colourB = colourB;
  }

  //=========================================================================//
  // Star update                                                             //
  //=========================================================================//
  public void update() {
    this.sketch.pushMatrix();
    this.sketch.translate((float) this.location.getX(), 
                          (float) this.location.getY());
    //this.sketch.rotate(this.sketch.frameCount / (float) -100.0);
    this.renderStar();
    this.sketch.popMatrix();
  }

  //=========================================================================//
  // Star render methods                                                     //
  //=========================================================================//   
  private void renderStar() {
    float angle = TWO_PI / this.nPoints;
    float halfAngle = angle / (float) 2.0;

    /*this.sketch.beginShape();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 60);
    for (float a = 0; a < TWO_PI; a += angle) {
        float sx = -1 + cos(a) * (float) this.dimensions.getY() + 2;
        float sy = -1 + sin(a) * (float) this.dimensions.getY() + 2;
        this.sketch.vertex(sx, sy);
        sx = cos(a + halfAngle) * (float) this.dimensions.getX() + 2;
        sy = sin(a + halfAngle) * (float) this.dimensions.getX() + 2;
        this.sketch.vertex(sx, sy);
    }
    this.sketch.endShape(CLOSE);

    this.sketch.beginShape();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 30);
    for (float a = 0; a < TWO_PI; a += angle) {
        float sx = (float) -2.5 + cos(a) * 
                    (float) this.dimensions.getY() + 5;
        float sy = (float) -2.5 + sin(a) * 
                    (float) this.dimensions.getY() + 5;
        this.sketch.vertex(sx, sy);
        sx = cos(a+halfAngle) * (float) this.dimensions.getX() + 5;
        sy = sin(a+halfAngle) * (float) this.dimensions.getX() + 5;
        this.sketch.vertex(sx, sy);
    }
    this.sketch.endShape(CLOSE);*/

    this.sketch.beginShape();
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    for (float a = 0; a < TWO_PI; a += angle) {
        float sx = cos(a) * (float) this.dimensions.getY();
        float sy = sin(a) * (float) this.dimensions.getY();
        this.sketch.vertex(sx, sy);
        sx = cos(a+halfAngle) * (float) this.dimensions.getX();
        sy = sin(a+halfAngle) * (float) this.dimensions.getX();
        this.sketch.vertex(sx, sy);
    }
    this.sketch.endShape(CLOSE);
  }

  //=========================================================================//
  // Star change methods                                                     //
  //=========================================================================//   
  public void changeColour() {
    if (this.colourR == 119) {
        this.colourR = 178;
        this.colourG = 248;
        this.colourB = 255;
    }
    else if (this.colourR == 181) {
          this.colourR = 238;
          this.colourG = 225;
          this.colourB = 225;
      }
    else  if (this.colourR == 221) {
        this.colourR = 166;
        this.colourG = 163;
        this.colourB = 176;
    }

  }

    public void changeColourSwap(String label) {
        if (label == "Special") {
            this.colourR = 252;
            this.colourG = 74;
            this.colourB = 85;
        }
        else if (label == "Defend") {
            this.colourR = 65;
            this.colourG = 136;
            this.colourB = 65;
        }
        else  if (label == "Scout") {
            this.colourR = 241;
            this.colourG = 189;
            this.colourB = 0;
        }

    }

  public int getColourR(){
    return this.colourB;
  }
}