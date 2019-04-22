package swarm_wars_library.ui;

public class Label {
    int Colour;
    String label;
    float posx;
    float posy;
    boolean clicked = false;

    Label(int Colour, String label, float posx, float posy) {
        this.Colour = Colour;
        this.label = label;
        this.posx = posx;
        this.posy = posy;
    }

    public void Draw() {
        textAlign(CENTER, CENTER);
        fill(Colour);
        text(label, posx, posy);
    }

    public void changeLabel(String newLabel) {
        label = newLabel;
    }

}