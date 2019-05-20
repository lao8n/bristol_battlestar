package swarm_wars_library.graphics;

import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PConstants;
import swarm_wars_library.Images;

public class RenderSplashScreen{

  private Images images = Images.getInstance();
  private PApplet sketch;
  private PImage splashScreen; 

  public RenderSplashScreen(PApplet sketch){
    this.sketch = sketch;
    this.splashScreen = images.getSplashScreen();
    //this.backgroundImage = background.get(0, 0, sketch.width, sketch.height);
  }

  public void update(){
    this.sketch.noStroke();
    this.sketch.imageMode(PConstants.CORNERS);

    this.sketch.image(this.splashScreen, 
                                  0, 0,
                                  this.sketch.width, 
                                  this.sketch.height); 
                
  }
}