import processing.core.PApplet;

import swarm_wars_library.engine.BoxCollider;
import swarm_wars_library.engine.GameObject;
import swarm_wars_library.engine.Mover;
import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.SwarmBot;

/*control which screen is active by setting/updating gameScreen var
0: initial screen
1: game screen
2: game-over screen
*/

public class SwarmWars extends PApplet {

	ArrayList<GameObject> objectList = new ArrayList<GameObject>();

	int MAXSCREENS = 2;
	int gameScreen = 0;
	Display display = new Display();
	Player p;
	GameObject gameObj, gameObjNext;


	void setup() {
		objectList.add(new EnvObject(new Vector2D(100, 100)));
		p = new Player(new Vector2D(width/2, height/2));
		objectList.add(p);
	}

	public void settings(){
		size(500, 500, "processing.awt.PGraphicsJava2D");
	}

	void draw(){
	//display contents of the current screen
   if (gameScreen == 0){
	    initScreen();
    } else if (gameScreen == 1){
	    gameScreen();
    } else if (gameScreen == 2){
	    gameOverScreen();
    }
	}

	/*--------GAME SCREENS ----*/

 void initScreen(){
    background(0);
    textAlign(CENTER);
    text("BATTLESTAR \n\nClick to n to start", height/2, width/2);
 }

  void gameScreen(){
		int i, j;
		background(25, 25, 76);
		for(i = 0; i < objectList.size(); i++){
			gameObj = objectList.get(i);
			gameObj.update();
			display.display(gameObj);
		}

		//loop over all objects and set hasCollisions to false at start of loop
		for(i = 0; i < objectList.size(); i++){
			gameObj = objectList.get(i);
			BoxCollider.clearCollision(gameObj);

			//this will loop over all game objects as needed to check for collisions
			for(j = i + 1; j < objectList.size(); j++){

				if(i != j){
					gameObjNext = objectList.get(j);
					BoxCollider.boundingCheck(gameObj, gameObjNext);
				}
			}
		}
	}

  void gameOverScreen(){
	    background(0, 0, 0);
	}

	void changeScreen(int k){
	   //TODO add more checks here, only change screens in certain cases
	   if (k == 'n' || k == 'N'){
		    gameScreen++;
				if (gameScreen > MAXSCREENS){
				   gameScreen = 0;
				}
		 }

		 //add pause screen on 'p'
	}

	/* ------ EVENT LISTENERS ------ */

	void keyPressed() {
	  changeScreen(keyCode);
		p.setMove(keyCode, true);
	}

	void keyReleased() {
		p.setMove(keyCode, false);
	}

	/* ------ DISPLAY CLASS ------ */
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

		@Override
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

    //TODO move these to Java methods to access keyboard input, to move
		//this whole class to a .java file
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
