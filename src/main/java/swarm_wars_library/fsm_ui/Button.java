package swarm_wars_library.fsm_ui;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;

import swarm_wars_library.physics.Vector2D;

public class Button {
  private PApplet sketch;
  private String label;
  private Vector2D topLeftLocation;   
  private Vector2D buttonDimensions;  
  private int colourR = 255;
  private int colourG = 255;
  private int colourB = 255;

  //=========================================================================//
  // Button constructor                                                      //
  //=========================================================================//
  public Button(PApplet sketch, String labelB, Vector2D topLeftLocation, 
                Vector2D buttonDimensions) {
    this.sketch = sketch;
    this.label = labelB;
    this.topLeftLocation = topLeftLocation;
    this.buttonDimensions = buttonDimensions;
  }

  //=========================================================================//
  // Button update                                                           //
  //=========================================================================//
  public void update(){

    //inner colour
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.buttonDimensions.getX(), 
                     (float) this.buttonDimensions.getY(), 
                     10);

    this.sketch.textAlign(CENTER, CENTER);
    this.sketch.fill(0);
    this.sketch.text(label, 
                     (float) this.topLeftLocation.getX() + 
                     (float) this.buttonDimensions.getX() / 2, 
                     (float) this.topLeftLocation.getY() + 
                     (float) this.buttonDimensions.getY() / 2);
    //this.sketch.textSize(6);
  }


  //=========================================================================//
  // Button label methods                                                    //
  //=========================================================================//

  public String getLabelString(){
    return this.label;
  }

  public void changeLabel(String label) {
    this.label = label;
  }
}