package swarm_wars_library;

import java.util.ArrayList;

import processing.core.PApplet;

import swarm_wars_library.engine.CollisionDetection;
import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.Bot;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.PlayerAI;
import swarm_wars_library.entities.PlayerN;
import swarm_wars_library.entities.Turret;
import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.graphics.RenderLayers;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;
import swarm_wars_library.network.NetworkClientFunctions;
import swarm_wars_library.network.GameClient;
import swarm_wars_library.ui.UI;


public class SwarmWars extends PApplet {

  // Player Id for networking
  private static int playerId;
  private static int enemyId;
  private int frameNumber;

  // Players
  PlayerN player1;
  PlayerN player2;

  // Entity lists that has all our game things.
  ArrayList <AbstractEntity> player1TakeDamage;  
  ArrayList <AbstractEntity> player1DealDamage;
  ArrayList <AbstractEntity> player2TakeDamage;
  ArrayList <AbstractEntity> player2DealDamage;
  ArrayList <AbstractEntity> gameObjectsTakeDamage;
  ArrayList <AbstractEntity> gameObjectsDealDamage;

  // Game Backend Objects
  Map map = Map.getInstance();
  RenderLayers renderLayers;
  UI ui;

  // Game screens 
  GAMESCREEN currentScreen = GAMESCREEN.FSMUI;

  //=========================================================================//
  // Processing Settings                                                     //
  //=========================================================================//
  public void settings() {
    this.size(1200, 800, "processing.awt.PGraphicsJava2D");
  }

  //=========================================================================//
  // Processing Setup                                                        //
  //=========================================================================//
  public void setup() {
    this.frameRate(60);
    this.uiSetup();
    this.commsSetup();
    this.entitiesSetup();
    CommsGlobal.update();
    this.renderSetup();
    this.networkSetup();
  }
  //=========================================================================//
  // Processing Game Loop                                                    //
  //=========================================================================//
  public void draw() {
    switch(this.currentScreen){
      case START:
        break;
      case FSMUI:
        this.uiUpdate();
        break;
      case GAME:
        this.checkCollisions();
        this.entitiesUpdate();
        this.renderLayersUpdate();
        CommsGlobal.update();
        break;
      case GAMEOVER:
        break;
      default:
        // TODO Add error
    }
  }

  //=========================================================================//
  // Processing Main                                                         //
  //=========================================================================//
  public static void main(String[] passedArgs) {
    // Start networking client
    playerId = NetworkClientFunctions.getPlayerIdFromUser();
    enemyId = playerId == 1 ? 0 : 1;

    new Thread(new Runnable() {
      public void run() {
        try {
          GameClient.run();
        }catch (Exception e){
          e.printStackTrace();
        }
      }
    }).start();

    try {
      GameClient.countDownLatch.await();
      Thread.sleep(10000);
    } catch (Exception e) {
      System.out.println("FAILED");
      e.printStackTrace();
    }

    // Start PApplet
    String[] appletArgs = new String[] {
            "swarm_wars_library.SwarmWars"
    };

    PApplet.main(appletArgs);
  }

  //=========================================================================//
  // Comms Setup                                                             //
  //=========================================================================//
  public void commsSetup(){
    // player1 setup
    CommsGlobal.add("PLAYER1", new CommsChannel(1));
    CommsGlobal.add("PLAYER1_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("PLAYER1_BULLET", 
      new CommsChannel(1 * map.getNumBulletsPerMagazine()));

    // player2 setup
    CommsGlobal.add("PLAYER2", new CommsChannel(1));
    CommsGlobal.add("PLAYER2_BOT", 
      new CommsChannel(map.getNumBotsPerPlayer()));
    CommsGlobal.add("PLAYER2_BULLET", 
      new CommsChannel(1 * map.getNumBulletsPerMagazine()));

    // game objects setup
    CommsGlobal.add("TURRET", new CommsChannel(map.getNumTurrets()));
    CommsGlobal.add("TURRET_BULLET", 
      new CommsChannel(map.getNumTurrets() * map.getNumBulletsPerMagazine()));
  }

  //=========================================================================//
  // Entities Setup                                                          //
  //=========================================================================//
  public void entitiesSetup(){

    this.player1TakeDamage = new ArrayList<AbstractEntity>();  
    this.player1DealDamage = new ArrayList<AbstractEntity>();
    this.player2TakeDamage = new ArrayList<AbstractEntity>();
    this.player2DealDamage = new ArrayList<AbstractEntity>();
    this.gameObjectsTakeDamage = new ArrayList<AbstractEntity>();
    this.gameObjectsDealDamage = new ArrayList<AbstractEntity>();

    // player1 setup
    this.player1 = new PlayerN(this, ENTITY.PLAYER1);
    this.player1TakeDamage.add(this.player1);
    this.player1DealDamage.addAll(player1.getBullets());
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER1_BOT, "boids_flock", i);
      this.player1TakeDamage.add(bot);
    }

    // player2 setup
    this.player2 = new PlayerN(this, ENTITY.PLAYER2);
    this.player2TakeDamage.add(this.player2);
    this.player2DealDamage.addAll(player2.getBullets());
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER2_BOT, "boids_flock", i);
      this.player2TakeDamage.add(bot);
    }

    // turrets setup
    for(int i = 0; i < this.map.getNumTurrets(); i++){
      Turret turret = new Turret(ENTITY.TURRET);
      this.gameObjectsTakeDamage.add(turret);
      this.gameObjectsDealDamage.addAll(turret.getBullets());
    }
  }

  //=========================================================================//
  // Render Setup                                                            //
  //=========================================================================//
  public void renderSetup(){
    this.renderLayers = new RenderLayers(this);
  }

  //=========================================================================//
  // UI Setup                                                                //
  //=========================================================================//
  public void uiSetup(){
    this.ui = new UI(this);
  }

  //=========================================================================//
  // Network Setup                                                           //
  //=========================================================================//
  public void networkSetup(){
    this.frameNumber = 0;
    NetworkClientFunctions.cleanBuffer();
    NetworkClientFunctions.sendConnect(this.playerId);
    NetworkClientFunctions.sendSetup(this.playerId);

    NetworkClientFunctions.sendStart(this.playerId);
    NetworkClientFunctions.awaitStart();
  }

  //=========================================================================//
  // Collision checks                                                        //
  //=========================================================================//
  public void checkCollisions() {
    // Game Objects -> Player1
    CollisionDetection.checkCollisions(this.gameObjectsDealDamage,
                                       this.player1TakeDamage);
    // Game Objects -> Player2
    CollisionDetection.checkCollisions(this.gameObjectsDealDamage,
                                       this.player2TakeDamage);
    // Player1 -> Game Objects
    CollisionDetection.checkCollisions(this.player1DealDamage,
                                       this.gameObjectsTakeDamage);
    // Player1 -> Player2
    CollisionDetection.checkCollisions(this.player1DealDamage,
                                       this.player2TakeDamage);
    // Player2 -> Game Objects
    CollisionDetection.checkCollisions(this.player2DealDamage,
                                       this.gameObjectsTakeDamage);
    // Player2 -> Player1
    CollisionDetection.checkCollisions(this.player2DealDamage,
                                       this.player1TakeDamage);
  }

  //=========================================================================//
  // Entities update                                                         //
  //=========================================================================//
  public void entitiesUpdate(){

    NetworkClientFunctions.sendOperation(playerId, frameNumber, player1.getInput());
    java.util.Map<String, Object> messageIn = NetworkClientFunctions.getPackage(playerId, frameNumber++);
    if(messageIn.containsKey("W")) player2.getInput().setMoveUp((Integer) messageIn.get("W"));
    if(messageIn.containsKey("A")) player2.getInput().setMoveLeft((Integer) messageIn.get("A"));
    if(messageIn.containsKey("S")) player2.getInput().setMoveDown((Integer) messageIn.get("S"));
    if(messageIn.containsKey("D")) player2.getInput().setMoveRight((Integer) messageIn.get("D"));


    // Update game entities
    for(int i = 0; i < this.gameObjectsTakeDamage.size(); i++){
      this.gameObjectsTakeDamage.get(i).update();
    }
    for(int i = 0; i < this.gameObjectsDealDamage.size(); i++){
      this.gameObjectsDealDamage.get(i).update();
    }
    // Update player1
    for(int i = 0; i < this.player1TakeDamage.size(); i++){
      this.player1TakeDamage.get(i).update();
    }
    for(int i = 0; i < this.player1DealDamage.size(); i++){
      this.player1DealDamage.get(i).update();
    }
    // Update player2
    for(int i = 0; i < this.player2TakeDamage.size(); i++){
      this.player2TakeDamage.get(i).update();
    }
    for(int i = 0; i < this.player2DealDamage.size(); i++){
      this.player2DealDamage.get(i).update();
    }
  }

  //=========================================================================//
  // RenderLayers update                                                     //
  //=========================================================================//
  public void renderLayersUpdate(){
    this.renderLayers.update();
  }

  //=========================================================================//
  // UI update                                                               //
  //=========================================================================//
  public void uiUpdate(){
    this.ui.update();
    this.currentScreen = this.ui.getGameScreen();
  }


  //=========================================================================//
  // Event listeners                                                         //
  //=========================================================================//
  // TODO is it possible to have these in a switch statement rather than 
  // a switch statement inside them?

  public void keyPressed() {
    this.player1.listenKeyPressed(this.keyCode);
  }

  public void keyReleased() {
    this.player1.listenKeyReleased(this.keyCode);
  }

  public void mousePressed() {
    switch(this.currentScreen){
      case START:
        break;
      case FSMUI:
        this.ui.listenMousePressed();
        break;
      case GAME:
        this.player1.listenMousePressed();
        break;
      case GAMEOVER:
        break;
      default:
        // TODO Add error
    }
  }
  public void mouseReleased() {
    switch(this.currentScreen){
      case START:
        break;
      case FSMUI:
        this.ui.listenMouseReleased();
        break;
      case GAME:
        this.player1.listenMouseReleased();
        break;
      case GAMEOVER:
        break;
      default:
        // TODO Add error
    }
  }

  public static int getPlayerId() {
    return playerId;
  }
}
