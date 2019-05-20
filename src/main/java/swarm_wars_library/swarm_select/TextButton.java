package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import processing.core.PConstants;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

import static processing.core.PConstants.*;

public class TextButton {

  private PApplet sketch;
  private String label;
  private Vector2D topLeftLocation;
  private Vector2D dimensions;
  private int colourR;
  private int colourG;
  private int colourB;

  //=========================================================================//
  // Text Button constructor                                                 //
  //=========================================================================//
  public TextButton(PApplet sketch, String label, Vector2D topLeftLocation, 
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
  // Text Button update method                                               //
  //=========================================================================//
  public void update() {
    this.sketch.rectMode(CORNER);
    this.sketch.stroke(255, 255, 255);
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 90);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.dimensions.getX(), 
                     (float) this.dimensions.getY());

    this.sketch.fill(255);
    this.sketch.textAlign(CENTER, CENTER);
    double textCenterX = this.topLeftLocation.getX() + this.dimensions.getX() / 2;
    double textCenterY = this.topLeftLocation.getY() + this.dimensions.getY() / 2;

//    this.sketch.textSize();
    this.sketch.text(this.label,
                    (float) textCenterX,
                    (float) textCenterY);
  }

  public void setLabel(String label){
    this.label = label;
  }

  public void setRGB(SWARMALGORITHM swarmAlgorithm){
    int r = 255;
    int g = 255;
    int b = 255;
    switch(swarmAlgorithm.getFSMState()){
      case SPECIAL:
        r = 252;
        g = 74;
        b = 85;
        break;
      case DEFEND:
        r = 65;
        g = 136;
        b = 65;
        break;
      case SCOUT:
        r = 241;
        g = 189;
        b = 0;
        break;
      default: 
        break;
    }
    this.colourR = r;
    this.colourG = g;
    this.colourB = b;
  }
}