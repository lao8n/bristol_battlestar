import processing.core.PApplet;

import swarm_wars_library.engine.BoxCollider;
import swarm_wars_library.engine.SwarmLogic;
//import swarm_wars_library.engine.GameObject;
//import swarm_wars_library.engine.Mover;
import swarm_wars_library.engine.Vector2D;
import swarm_wars_library.engine.CommsChannel;
import swarm_wars_library.engine.CommsPacket; 
import swarm_wars_library.engine.Entity; 
import swarm_wars_library.engine.Tag; 
import swarm_wars_library.engine.EnVar;

/*control which screen is active by setting/updating gameScreen var
0: initial screen
1: game screen - game object
2: Game screen - entity
3: game-over screen
*/

public class SwarmWars extends PApplet {

  //Entity(tag, scale, hasRender, hasInput, hasShooter, hasHealth, hasComms, hasRb)
	Entity player = new Entity(this, Tag.PLAYER, 30, true, true, false, false, true, true);

	Entity bot1 = new Entity(this, Tag.P_BOT, 5, true, true, false, false, true, true);
	Entity bot2 = new Entity(this, Tag.P_BOT, 5, true, true, false, false, true, true);


	//MAKE SOME BOTS
	ArrayList<Entity> entityList = new ArrayList<Entity>();

	int MAXSCREENS = 3;
	int gameScreen = 2;
	int initScreenTimer = 120;
	int numBots = 100;
	CommsChannel comms = new CommsChannel(numBots+1);
	// EnVar envar;
	Entity bot; 

	void setup() {
	
		// envar = new EnVar();
		// entityList.add(envar);

		player.setComms(comms);
		entityList.add(player);

		//add bots
		for (int i = 0; i < numBots; i++){
			bot = new Entity(this, Tag.P_BOT, 5, true, true, false, false, true, true);
			bot.setSwarmLogic();
			bot.setComms(comms);
			entityList.add(bot);
			System.out.println("bot created: " + i);
		}
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
			gameScreenEntity();
	    } else {
			gameOverScreen();
		}
	}

	/*--------GAME SCREENS ----*/

 void initScreen(){
    background(0);
    textAlign(CENTER);
  	text("welcome to\n\nSWARM WARS\n\n\nMove: WASD", width/2, height/2);
  
  	//after timer, switch to game
  	if (initScreenTimer-- < 0){
    	gameScreen = 1;
  	}
 	}

	void gameScreen(){
		background(25, 25, 76);		
	}

	void gameScreenEntity(){
		background(25, 25, 76);

		// update all bots
	  	for (int j = 0; j < entityList.size(); j++){
			entityList.get(j).update();
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

	/* ------ DISPLAY CLASS ------ */
	//class Display {
	//	 public void display(GameObject go){
	    //drawBackground();
	//			pushMatrix();
	//			rectMode(CENTER);
	//			stroke(0);
	//			translate((float)go.getLocationX(), (float)go.getLocationY());
	//			rotate((float)go.getHeading());
	//			drawObject(go);
//				popMatrix();
	//	 }

     //TODO : add tracking, moving background image
	//	 private void drawBackground(){
//		    background(25,25,76); //dark blue
		 //}

//		 private void drawObject(GameObject go){
//			 if (go.getGOTag() == "PLAYER"){
//				 drawShip(go);
//			 } else if (go.getGOTag() == "ENV"){
//				 drawEnv(go);

				//will add drawBullet, drawEnemy, drawBase etc
	//		 } else {
				 //do nothing
//			 }
//		 }

	//	 private void drawShip(GameObject go){
		 	 //go.setScale(30, 20);
		//	 go.setScale(20,10);
		//	 if (go.getHasCollision()){
		//	    fill(random(0, 255),random(0, 255),random(0, 255));
		//	 }
			 //shadow
		//	 noStroke();
		//	 fill(33,11,232, 20);
		//	 rect(0, 0, (float)go.getScaleX()*1.75, (float)go.getScaleY()*1.75);
		//	 fill(33,11,232, 50);
		//	 rect(0, 0, (float)go.getScaleX()*1.5, (float)go.getScaleY()*1.5);
		//	 fill(33,11,232, 70);
		//	 rect(0, 0, (float)go.getScaleX()*1.2, (float)go.getScaleY()*1.2);
			 //wing shadows
	//		 fill(33, 11, 232, 70);
	//		 rect(-(float)go.getScaleX()/1.75, (float)go.getScaleY()/2.5,
	///		 			(float)go.getScaleX(), (float)go.getScaleY());
	//		 rect(-(float)go.getScaleX()/1.75, -(float)go.getScaleY()/2.5,
		//	 			(float)go.getScaleX(), (float)go.getScaleY());
			 //wings
			// fill(33, 11, 232);
			// rect(-(float)go.getScaleX()/1.5, (float)go.getScaleY()/2,
			 //			(float)go.getScaleX(), (float)go.getScaleY());
			// rect(-(float)go.getScaleX()/1.5, -(float)go.getScaleY()/2,
			 //			(float)go.getScaleX(), (float)go.getScaleY());
			 //ship head
	//		 fill(77, 77, 255);
	//		 rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
	//	 }

		// private void drawEnv(GameObject go){
		 //  noStroke();
   		// go.setScale(50, 40);
		   //fill(128, 128, 128);
			 //if (go.getHasCollision()){
			//		fill(random(0, 255),random(0, 255),random(0, 255));
			 //}
			 //rect(0, 0, (float)go.getScaleX(), (float)go.getScaleY());
		// }
	//}

	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "SwarmWars" };
		PApplet.main(appletArgs);
    }

	/* ------ EVENT LISTENERS ------ */
	void keyPressed() {
	  //changeScreen(keyCode);
		player.input.setMove(keyCode, 1);
	}

	void keyReleased() {
		player.input.setMove(keyCode, 0);
	}
}

