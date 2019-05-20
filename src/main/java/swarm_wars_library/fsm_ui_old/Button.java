package swarm_wars_library.fsm_ui_old;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;

import swarm_wars_library.physics.Vector2D;

public class Button {
  private PApplet sketch;
  private String label;
  private Vector2D topLeftLocation;   
  private Vector2D buttonDimensions;  
  private int colourR = 0;
  private int colourG = 237;
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
    //glow effect
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 30);
    this.sketch.rect((float) this.topLeftLocation.getX() - 4, 
                     (float) this.topLeftLocation.getY() - 4, 
                     (float) this.buttonDimensions.getX() + 8, 
                     (float) this.buttonDimensions.getY() + 8, 
                     10);

    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 50);
    this.sketch.rect((float) this.topLeftLocation.getX()-2, 
                     (float) this.topLeftLocation.getY()-2, 
                     (float) this.buttonDimensions.getX() + 4, 
                     (float) this.buttonDimensions.getY() + 4, 
                     10);

    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB, 60);
    this.sketch.rect((float) this.topLeftLocation.getX()-1, 
                     (float) this.topLeftLocation.getY()-1, 
                     (float) this.buttonDimensions.getX() + 2, 
                     (float) this.buttonDimensions.getY() + 2, 
                     10);

    //inner colour
    this.sketch.noStroke();
    this.sketch.fill(this.colourR, this.colourG, this.colourB);
    this.sketch.rect((float) this.topLeftLocation.getX(), 
                     (float) this.topLeftLocation.getY(), 
                     (float) this.buttonDimensions.getX(), 
                     (float) this.buttonDimensions.getY(), 
                     10);

    this.sketch.textAlign(CENTER, CENTER);
    this.sketch.fill(255);
    this.sketch.text(label, 
                     (float) this.topLeftLocation.getX() + 
                     (float) this.buttonDimensions.getX() / 2, 
                     (float) this.topLeftLocation.getY() + 
                     (float) this.buttonDimensions.getY() / 2);
  }

  //=========================================================================//
  // Button Colour method                                                    //
  //=========================================================================//

  public void changeColour() {
    if (this.colourR == 0) {
      this.colourR = 255;
      this.colourG = 0;
      this.colourB = 199;
    }
    else {
      this.colourR = 0;
      this.colourG = 237;
      this.colourB = 255;
    }
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