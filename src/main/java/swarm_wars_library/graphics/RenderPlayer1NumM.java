package swarm_wars_library.graphics;

import processing.core.PApplet;
import swarm_wars_library.entities.Missile;

import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

public class RenderPlayer1NumM {
    private int Mnum=0;
    private PApplet sketch;
    public RenderPlayer1NumM(PApplet sketch){
        this.sketch = sketch;
    }
    public void update(int missilenum){
        this.Mnum=missilenum;
        this.sketch.fill(0, 101, 255);
        this.sketch.textSize(30);
        this.sketch.textAlign(LEFT, TOP);
        this.sketch.text("Missile number: " + Mnum, 5, 155);
    }
}
