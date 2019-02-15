import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.core.PApplet; 
import processing.swarm_wars_library.engine.BoxCollider; 
import processing.swarm_wars_library.engine.GameObject; 
import processing.swarm_wars_library.engine.Mover; 
import processing.swarm_wars_library.engine.Vector2D; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 











public class SwarmWars extends PApplet {

	Player p;
	int moveForce = 10;
	EnvObject g;


	public void setup() {  
		p = new Player(new Vector2D(width/2, height/2));
		p.setScale(30, 20);
		g = new EnvObject(new Vector2D(100, 100));
		g.setScale(50, 40);
	}

	public void settings(){
		size(300, 300, "processing.awt.PGraphicsJava2D");
	}

	public void draw(){
		int i = 0;
		background(255);

		p.update();
		g.display();
		p.display();
		if(!BoxCollider.boundingCheck(p, g)){
			p.c = color(0, 0, 0);
			g.c = color(0, 0, 0);
		} else {
			p.c = color(random(0, 255), random(0, 255), random(0, 255));
			g.c = color(random(0, 255), random(0, 255), random(0, 255));
		}

	}



	/* ------ EVENT LISTENERS ------ */

	public void keyPressed() {
		p.setMove(keyCode, true);
	}

	public void keyReleased() {
		p.setMove(keyCode, false);
	}


	/* ------ ENV CLASS ------ */

	class EnvObject extends GameObject {
		int c = color(random(0, 255), random(0, 255), random(0, 255));
		EnvObject(Vector2D location_) {
			super(location_);
		}

		public void display(){
			pushMatrix();
			rectMode(CENTER);
			stroke(0);
			fill(c);
			translate((float)location.x, (float)location.y);
			rotate((float)heading);
			rect(0, 0, (float)scale.x, (float)scale.y);
			popMatrix();
		}
	}

	/* ------ PLAYER CLASS ------ */

	class Player extends Mover {
		boolean moveLeft, moveRight, moveUp, moveDown;
		int moveForce = 10;
		int c = color(random(0, 255), random(0, 255), random(0, 255));

		Player(Vector2D location_) {
			super(location_);
		}

		public void update(){
			/* I THINK WE SHOULD MOVE IN THE Y DIRECTION SLIGHTLY SLOWER THAN X - FOR ORTHO APPEARANCE */
			location.x += moveForce * (PApplet.parseInt(moveRight) - PApplet.parseInt(moveLeft));
			location.y += 0.8f * moveForce * (PApplet.parseInt(moveDown) - PApplet.parseInt(moveUp));

			heading = Math.atan2(mouseY - location.y, mouseX -location.x);

			rb.update(location, heading);
		}

		public void display(){
			pushMatrix();
			rectMode(CENTER);
			stroke(0);
			fill(c);
			translate((float)location.x, (float)location.y);
			rotate((float)heading);
			rect(0, 0, (float)scale.x, (float)scale.y);
			popMatrix();
		}

		public boolean setMove(int k, boolean b){
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

