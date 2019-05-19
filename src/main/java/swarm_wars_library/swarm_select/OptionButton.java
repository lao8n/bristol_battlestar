package swarm_wars_library.swarm_select;

import processing.core.PApplet;

import processing.core.PConstants;
import processing.core.PImage;
import static processing.core.PConstants.CENTER;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class OptionButton {

  private PApplet sketch;
  private Vector2D topLeftLocation;
  private Vector2D dimensions;
  private PImage defaultImage;
  private PImage specialSuicide;
  private PImage specialGhost;
  private PImage specialStar;
  private PImage specialSacrifice;
  private PImage defendShell;
  private PImage defendFlock;
  private PImage defendInvincible; 
  private PImage defendHibernate; 
  private PImage scoutRandom;
  private PImage scoutBee;
  private PImage scoutAnt;
  private PImage scoutPSO;

  //=========================================================================//
  // Option Button constructor                                               //
  //=========================================================================//
  public OptionButton(PApplet sketch, Vector2D topLeftLocation, 
    Vector2D dimensions) {
    this.sketch = sketch;
    this.topLeftLocation = topLeftLocation;
    this.dimensions = dimensions;
    this.defaultImage = sketch.loadImage("resources/images/defaultImage.png");

    this.specialSuicide = sketch.loadImage("resources/images/specialSuicide.png");
    this.specialGhost = sketch.loadImage("resources/images/specialGhost.png");
    this.specialStar = sketch.loadImage("resources/images/specialStar.png");
    this.specialSacrifice = sketch.loadImage("resources/images/specialSacrifice.png"); 

    this.defendShell = sketch.loadImage("resources/images/defendShell.png");
    this.defendFlock = sketch.loadImage("resources/images/defendFlock.png");
    this.defendInvincible = sketch.loadImage("resources/images/defendInvincible.png"); 
    this.defendHibernate = sketch.loadImage("resources/images/defendHibernate.png"); 

    this.scoutRandom = sketch.loadImage("resources/images/scoutRandom.png");
    this.scoutBee = sketch.loadImage("resources/images/scoutBee.png");
    this.scoutAnt = sketch.loadImage("resources/images/scoutAnt.png");
    this.scoutPSO = sketch.loadImage("resources/images/scoutPSO.png"); 
  }

  //=========================================================================//
  // Option Button update method                                             //
  //=========================================================================//
  public void update(SWARMALGORITHM swarmAlgorithm, int i) {
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
    this.sketch.stroke(r, g, b);
    this.sketch.fill(0, 0, 0);
    PImage x = this.defaultImage;
    switch(swarmAlgorithm){
      case DEFENDSHELL:
        x = this.defendShell;
        break;
      case DEFENDFLOCK:
        x = this.defendFlock;
        break;
      case DEFENDHIBERNATE:
        x = this.defendHibernate;
        break;
      case DEFENDINVINCIBLE:
        x = this.defendInvincible;
        break;
      case SCOUTRANDOM:
        x = this.scoutRandom;
        break;
      case SCOUTBEE:
        x = this.scoutBee;
        break;
      case SCOUTANT:
        x = this.scoutAnt;
        break;
      case SCOUTPSO:
        x = this.scoutPSO;
        break;
      case SPECIALSUICIDE:
        x = this.specialSuicide;
        break;
      case SPECIALGHOST:
        x = this.specialGhost;
        break;
      case SPECIALSTAR:
        x = this.specialStar;
        break;
      case SPECIALSACRIFICE:
        x = this.specialSacrifice;
        break;
      default:
        break;
    }
    this.sketch.imageMode(PConstants.CORNER);
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
      this.sketch.fill(r, g, b, (float) 99.9);
      this.sketch.rect((float) this.topLeftLocation.getX(), 
                       (float) this.topLeftLocation.getY(), 
                       (float) this.dimensions.getX(), 
                       (float) this.dimensions.getY());
      this.sketch.pushMatrix();
      this.sketch.fill(255, 255, 255);
      this.sketch.textSize(60);
      this.sketch.textAlign(CENTER, CENTER);
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