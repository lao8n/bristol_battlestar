import processing.core.PApplet;

import swarm_wars_library.engine.BoxCollider;
import swarm_wars_library.engine.GameObject;
import swarm_wars_library.engine.Mover;
import swarm_wars_library.engine.Vector2D;


public class SwarmWars extends PApplet {

	Display display = new Display();
	Player p;
	int moveForce = 10;
	EnvObject g;


	void setup() {  
		p = new Player(new Vector2D(width/2, height/2));
		g = new EnvObject(new Vector2D(100, 100));
	}

	public void settings(){
		size(300, 300, "processing.awt.PGraphicsJava2D");
	}

	void draw(){
		int i = 0;
		background(25, 25, 76);

		p.update();
		display.display(g);
		display.display(p);
		//loop over all objects and set hasCollisions to false at start of loop
		BoxCollider.clearCollision(p); BoxCollider.clearCollision(g);
    //this will loop over all game objects as needed to check for collisions
		BoxCollider.boundingCheck(p, g);
	}



	/* ------ EVENT LISTENERS ------ */

	void keyPressed() {
		p.setMove(keyCode, true);
	}

	void keyReleased() {
		p.setMove(keyCode, false);
	}

	class Display {
		 public void display(GameObject go){
		    //drawBackground();
				pushMatrix();
				rectMode(CENTER);
				stroke(0);
				translate((float)go.getLocationX(), (float)go.getLocationY());
				rotate((float)go.getHeading());
				drawObject(go);
				popMatrix();
		 }

     //TODO : add tracking, moving background image
		 private void drawBackground(){
		    background(25,25,76); //dark blue
		 }

		 private void drawObject(GameObject go){
			 if (go.getGOTag() == "PLAYER"){
				 drawShip(go);
			 } else if (go.getGOTag() == "ENV"){
				 drawEnv(go);

				//will add drawBullet, drawEnemy, drawBase etc
			 } else {
				 //do nothing
			 }
		 }

		 private void drawShip(GameObject go){
		 	 //go.setScale(30, 20);
			 go.setScale(20,10);
			 if (go.getHasCollision()){
			    fill(random(0, 255),random(0, 255),random(0, 255));
			 }
			 //shadow
			 noStroke();
			 fill(33,11,232, 20);
			 rect(0, 0, (float)go.getScaleX()*1.75, (float)go.getScaleY()*1.75);
			 fill(33,11,232, 50);
			 rect(0, 0, (float)go.getScaleX()*1.5, (float)go.getScaleY()*1.5);
			 fill(33,11,232, 70);
			 rect(0, 0, (float)go.getScaleX()*1.2, (float)go.getScaleY()*1.2);
			 //wing shadows
			 fill(33, 11, 232, 70);
			 rect(-(float)go.getScaleX()/1.75, (float)go.getScaleY()/2.5, 
			 			(float)go.getScaleX(), (float)go.getScaleY());
			 rect(-(float)go.getScaleX()/1.75, -(float)go.getScaleY()/2.5, 
			 			(float)go.getScaleX(), (float)go.getScaleY());
			 //wings
			 fill(33, 11, 232);
			 rect(-(float)go.getScaleX()/1.5, (float)go.getScaleY()/2, 
			 			(float)go.getScaleX(), (float)go.getScaleY());
			 rect(-(float)go.getScaleX()/1.5, -(float)go.getScaleY()/2, 
			 			(float)go.getScaleX(), (float)go.getScaleY());
			 //ship head
			 fill(77, 77, 255);
			 rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
		 }

		 private void drawEnv(GameObject go){
		   noStroke();
   		 go.setScale(50, 40);
		   fill(128, 128, 128);
			 if (go.getHasCollision()){
					fill(random(0, 255),random(0, 255),random(0, 255));
			 }
			 rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
		 }
	}

	/* ------ ENV CLASS ------ */

	class EnvObject extends GameObject {
		EnvObject(Vector2D location_) {
			super(location_);
			setGOTag("ENV");
		}

	}

	/* ------ PLAYER CLASS ------ */

	class Player extends Mover {
		boolean moveLeft, moveRight, moveUp, moveDown;
		int moveForce = 10;

		Player(Vector2D location_) {
			super(location_);
			setMoverTag("PLAYER");
		}

		void update(){
			/* I THINK WE SHOULD MOVE IN THE Y DIRECTION SLIGHTLY SLOWER THAN X -
			 FOR ORTHO APPEARANCE */
			setLocationXY(getLocationX() + 
										moveForce * (int(moveRight) - int(moveLeft)),
										getLocationY() + 
										0.8 * moveForce * (int(moveDown) - int(moveUp)));
			setHeading(Math.atan2(mouseY - getLocationY(),
														mouseX - getLocationX()));

			updateMover(getLocation(), getHeading());
		}

		boolean setMove(int k, boolean b){
			switch(k) {
				case 'W':
				case 'w':
				case UP:
					return moveUp = b;
				case 'A':
				case 'a':
				case LEFT:
					return moveLeft = b;
				case 'S':
				case 's':
				case DOWN:
					return moveDown = b;
				case 'D':
				case 'd':
				case RIGHT:
					return moveRight = b;

				default:
					return b;
			}
		}
	}
  
	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "SwarmWars" };
		PApplet.main(appletArgs);
    }
}

