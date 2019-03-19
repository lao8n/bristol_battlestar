import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.core.PApplet; 
import swarm_wars_library.engine.BoxCollider; 
import swarm_wars_library.engine.SwarmLogic; 
import swarm_wars_library.engine.Vector2D; 
import swarm_wars_library.engine.CommsChannel; 
import swarm_wars_library.engine.CommsPacket; 
import swarm_wars_library.engine.Entity; 
import swarm_wars_library.engine.Tag; 
import swarm_wars_library.engine.EnVar; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 







//import swarm_wars_library.engine.GameObject;
//import swarm_wars_library.engine.Mover;








/*control which screen is active by setting/updating gameScreen var
0: initial screen
1: game screen - game object
2: Game screen - entity
3: game-over screen
*/

public class SwarmWars extends PApplet {

  //Entity(tag, scale, hasRender, hasInput, hasShooter, hasHealth, hasComms, hasRb)
  Entity player;

  //MAKE SOME BOTS
  ArrayList < Entity > entityList = new ArrayList < Entity > ();

  int MAXSCREENS = 3;
  int gameScreen = 2;
  int initScreenTimer = 120;
  int numBots = 100;
  CommsChannel comms = new CommsChannel(numBots + 1);
  // EnVar envar;
  Entity bot;

  public void setup() {

    // envar = new EnVar();
    // entityList.add(envar);

    player = new Entity(this, Tag.PLAYER, 30, true, true, true, false, true, true);
    player.setComms(comms);
    entityList.add(player);

    //add bots
    for (int i = 0; i < numBots; i++) {
      bot = new Entity(this, Tag.P_BOT, 5, true, true, false, false, true, true);
      bot.setSwarmLogic();
      bot.setComms(comms);
      entityList.add(bot);
      System.out.println("bot created: " + i);
    }
  }

  public void settings() {
    size(500, 500, "processing.awt.PGraphicsJava2D");
  }

  public void draw() {
    //display contents of the current screen
    if (gameScreen == 0) {
      initScreen();
    } else if (gameScreen == 1) {
      gameScreen();
    } else if (gameScreen == 2) {
      gameScreenEntity();
      collisionDetector();
    } else {
      gameOverScreen();
    }
  }

  /*--------GAME SCREENS ----*/

  public void initScreen() {
    background(0);
    textAlign(CENTER);
    text("welcome to\n\nSWARM WARS\n\n\nMove: WASD", width / 2, height / 2);

    //after timer, switch to game
    if (initScreenTimer-- < 0) {
      gameScreen = 1;
    }
  }

  public void gameScreen() {
    background(25, 25, 76);
  }

  public void collisionDetector(){
    for(int i = 0; i < entityList.size(); i++){
      for(int j = 0; j < entityList.size(); j++){
        BoxCollider.boundingCheck(entityList.get(i), entityList.get(j));
      }
    }
  }

  public void gameScreenEntity() {
    background(25, 25, 76);
    
    // update all bots
    for (int j = 0; j < entityList.size(); j++) {
      entityList.get(j).update();
    }
  }

  public void gameOverScreen() {
    background(0, 0, 0);
  }

  public void changeScreen(int k) {
    //TODO add more checks here, only change screens in certain cases
    if (k == 'n' || k == 'N') {
      gameScreen++;
      if (gameScreen > MAXSCREENS) {
        gameScreen = 0;
      }
    }
    //add pause screen on 'p'
  }

  public static void main(String[] passedArgs) {
    String[] appletArgs = new String[] {
      "SwarmWars"
    };
    PApplet.main(appletArgs);
  }

  /* ------ EVENT LISTENERS ------ */
  public void keyPressed() {
    //changeScreen(keyCode);
    player.input.setMove(keyCode, 1);
  }

  public void keyReleased() {
    player.input.setMove(keyCode, 0);
  }
}
