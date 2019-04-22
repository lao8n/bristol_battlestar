package swarm_wars_library.ui;

public class Circle {
    float posx;
    float posy;
    float w;
    float h;
    int ColourR = 0;
    int ColourG = 237;
    int ColourB = 255;

    Circle(float posx, float posy, float w, float h) {
        this.posx = posx;
        this.posy = posy;
        this.w = w;
        this.h = h;
    }

    public void Draw() {
        //glow effect
        noStroke();
        fill(ColourR, ColourG, ColourB, 30);
        ellipse(posx+6, posy+6, w, h);

        noStroke();
        fill(ColourR, ColourG, ColourB, 50);
        ellipse(posx+4, posy+4, w, h);

        noStroke();
        fill(ColourR, ColourG, ColourB, 60);
        ellipse(posx+2, posy+2, w, h);

        //inner colour
        noStroke();
        fill(ColourR, ColourG, ColourB);
        ellipse(posx, posy, w, h);
    }

    public void changecolor() {
        if (ColourR == 0) {
            ColourR = 255;
            ColourG = 0;
            ColourB = 199;
        }
        else {
            ColourR = 0;
            ColourG = 237;
            ColourB = 255;
        }
    }
}