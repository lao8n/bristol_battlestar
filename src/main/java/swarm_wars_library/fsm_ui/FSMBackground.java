package swarm_wars_library.fsm_ui;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;

import swarm_wars_library.physics.Vector2D;

public class FSMBackground {
    private PApplet sketch;
    private String label;
    private Vector2D topLeftLocation;
    private Vector2D buttonDimensions;
    private int colourR;
    private int colourG;
    private int colourB;
    private int alpha;

    //=========================================================================//
    // Button constructor                                                      //
    //=========================================================================//
    public FSMBackground(PApplet sketch, String labelB, Vector2D topLeftLocation,
                  Vector2D buttonDimensions, int colourR, int colourG, int colourB,
                  int alpha) {
        this.sketch = sketch;
        this.label = labelB;
        this.topLeftLocation = topLeftLocation;
        this.buttonDimensions = buttonDimensions;
        this.colourR = colourR;
        this.colourG = colourG;
        this.colourB = colourB;
        this.alpha = alpha;
    }

    //=========================================================================//
    // Button update                                                           //
    //=========================================================================//
    public void update(){
        // //glow effect
        // this.sketch.stroke(255);
        // this.sketch.rect((float) this.topLeftLocation.getX() - 4,
        //         (float) this.topLeftLocation.getY() - 4,
        //         (float) this.buttonDimensions.getX() + 8,
        //         (float) this.buttonDimensions.getY() + 8,
        //         0);


        //inner colour
        this.sketch.stroke(255);
        this.sketch.fill(this.colourR, this.colourG, this.colourB, this.alpha);
        this.sketch.rect((float) this.topLeftLocation.getX(),
                (float) this.topLeftLocation.getY(),
                (float) this.buttonDimensions.getX(),
                (float) this.buttonDimensions.getY(),
                0);

        this.sketch.textAlign(CENTER, CENTER);
        this.sketch.fill(255);
        this.sketch.text(label,
                (float) this.topLeftLocation.getX() +
                        (float) this.buttonDimensions.getX() / 2,
                (float) this.topLeftLocation.getY() +
                        (float) this.buttonDimensions.getY() / 2);
    }


    //=========================================================================//
    // Button change colour methods                                            //
    //=========================================================================//
    public void changeColour(int colourR, int colourG, int colourB, int alpha) {
        if (colourR == 0) {
            this.colourR = colourR;
            this.colourG = colourG;
            this.colourB = colourB;
            this.alpha = alpha;
        }
        else {
            this.colourR = colourR;
            this.colourG = colourG;
            this.colourB = colourB;
            this.alpha =   alpha;
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