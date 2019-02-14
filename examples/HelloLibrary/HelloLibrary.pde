import processing.core.PApplet;

public class HelloLibrary extends PApplet {

	public void settings() {
		size(500, 500);
	}

	public void draw(){
		background(64);
		ellipse(mouseX, mouseY, 20, 20);
	}
  
	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "HelloLibrary" };
		PApplet.main(appletArgs);
    }
}