package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import processing.core.PImage;

import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class OptionButton {

  private PApplet sketch;
  private Vector2D topLeftLocation;
  private Vector2D dimensions;
  private PImage defaultImage;
  private PImage defendShell;
  private PImage defendFlock;
  private PImage scoutRandom;
  private PImage scoutBee;

  //=========================================================================//
  // Option Button constructor                                               //
  //=========================================================================//
  public OptionButton(PApplet sketch, Vector2D topLeftLocation, 
    Vector2D dimensions) {
    this.sketch = sketch;
    this.topLeftLocation = topLeftLocation;
    this.dimensions = dimensions;
    this.defaultImage = sketch.loadImage("resources/images/default.png");
    this.defendShell = sketch.loadImage("resources/images/defendShell.png");
    this.defendFlock = sketch.loadImage("resources/images/defendFlock.png");
    this.scoutRandom = sketch.loadImage("resources/images/scoutRandom.png");
    this.scoutBee = sketch.loadImage("resources/images/scoutBee.png");
  }

  //=========================================================================//
  // Option Button update method                                             //
  //=========================================================================//
  public void update(SWARMALGORITHM swarmAlgorithm) {
    this.sketch.stroke(255, 255, 255);
    this.sketch.fill(0, 0, 0);
    PImage x = this.defaultImage;
    switch(swarmAlgorithm){
      case DEFENDSHELL:
        x = this.defendShell;
        break;
      case DEFENDFLOCK:
        x = this.defendFlock;
        break;
      case SCOUTRANDOM:
        x = this.scoutRandom;
        break;
      case SCOUTBEE:
        x = this.scoutBee;
        break;
      default:
        break;
    }
    this.sketch.image(x, 
                      (float) this.topLeftLocation.getX(), 
                      (float) this.topLeftLocation.getY(), 
                      (float) this.dimensions.getX(), 
                      (float) this.dimensions.getY());
    this.sketch.fill(255, 255, 255, 0);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.dimensions.getX(), 
                     (float) this.dimensions.getY());
  }

  public void update(){
    this.sketch.stroke(255, 255, 255);
    this.sketch.fill(0, 0, 0);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.dimensions.getX(), 
                     (float) this.dimensions.getY());
    this.sketch.fill(255);
  }
}