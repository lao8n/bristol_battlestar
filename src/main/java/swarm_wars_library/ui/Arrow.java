package swarm_wars_library.ui;

public class Arrow {
    float x1;
    float y1;
    float x2;
    float y2;

    Arrow(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    void Draw() {
        stroke(0, 235, 255);
        strokeWeight(5);
        fill(0, 235, 255);
        float a = dist(x1, y1, x2, y2) / 50;
        pushMatrix();
        translate(x2, y2);
        rotate(atan2(y2 - y1, x2 - x1));
        triangle(- a * 2 , - a, 0, 0, - a * 2, a);
        popMatrix();
        line(x1, y1, x2, y2);
    }

}
