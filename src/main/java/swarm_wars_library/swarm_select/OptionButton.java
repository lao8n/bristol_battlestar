package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import processing.core.PImage;
import swarm_wars_library.fsm.FSMSTATE;
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
  public void update(SWARMALGORITHM swarmAlgorithm, int i) {
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

    if(i > 0){
      int r = 255;
      int g = 255;
      int b = 255;
      switch(swarmAlgorithm.getFSMState()){
        case ATTACK:
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
      this.sketch.fill(r, g, b, (float) 99.9);
      this.sketch.rect((float) this.topLeftLocation.getX(), 
                       (float) this.topLeftLocation.getY(), 
                       (float) this.dimensions.getX(), 
                       (float) this.dimensions.getY());
      this.sketch.pushMatrix();
      this.sketch.fill(255, 255, 255);
      this.sketch.textSize(60);
      this.sketch.text(Integer.toString(i), 
                       (float) this.topLeftLocation.getX(), 
                       (float) this.topLeftLocation.getY(), 
                       (float) this.dimensions.getX(), 
                       (float) this.dimensions.getY());
      this.sketch.textSize(15);
      this.sketch.popMatrix();
    }
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

  //=========================================================================//
  // Location/dimension getters/setters                                      //
  //=========================================================================//
  public Vector2D getDimensions(){
    return this.dimensions;
  }

  public Vector2D getTopLeftLocation(){
    return this.topLeftLocation;
  }
}