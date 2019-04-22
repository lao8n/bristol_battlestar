package swarm_wars_library.ui;

public class Button {
    String label;
    float x;    // top left corner x position
    float y;    // top left corner y position
    float w;    // width of button
    float h;    // height of button
    int ColourR = 0;
    int ColourG = 237;
    int ColourB = 255;

    Button(String labelB, float xpos, float ypos, float widthB, float heightB) {
        label = labelB;
        x = xpos;
        y = ypos;
        w = widthB;
        h = heightB;
    }

    void Draw() {
        //glow effect
        noStroke();
        fill(ColourR, ColourG, ColourB, 30);
        rect(x-4, y-4, w+8, h+8, 10);

        noStroke();
        fill(ColourR, ColourG, ColourB, 50);
        rect(x-2, y-2, w+4, h+4, 10);

        noStroke();
        fill(ColourR, ColourG, ColourB, 60);
        rect(x-1, y-1, w+2, h+2, 10);

        //inner colour
        noStroke();
        fill(ColourR, ColourG, ColourB);
        rect(x, y, w, h, 10);

        textAlign(CENTER, CENTER);
        fill(255);
        text(label, x + (w / 2), y + (h / 2));
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

    public void OnmouseClicked(float xpos, float ypos, float widthB,
                               float heightB, int mousex, int mousey) {
        if(mousex >= xpos && mousex <=  xpos+widthB &&
                mousey >= ypos && mousey <= ypos+heightB) {
            changecolor();
        }
    }

    public void changeLabel(String label) {
        this.label = label;
    }
}