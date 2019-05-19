package swarm_wars_library.graphics;

import processing.core.PApplet;
import processing.core.PConstants;
import swarm_wars_library.entities.Missile;

import static processing.core.PConstants.*;

public class RenderPlayer2NumM {
    private int Mnum=0;
    private PApplet sketch;
    public RenderPlayer2NumM(PApplet sketch){
        this.sketch = sketch;
    }
    public void update(int missilenum){
        this.Mnum=missilenum;
        this.sketch.fill(255, 22, 65);
        this.sketch.textSize(30);
        this.sketch.textAlign(PConstants.LEFT,PConstants.TOP);
        this.sketch.text("Missiles: " + Mnum, this.sketch.width/2-150, 50);
    }
}