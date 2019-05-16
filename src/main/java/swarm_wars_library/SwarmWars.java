package swarm_wars_library;

import java.util.ArrayList;
import processing.core.PApplet;

import sun.nio.ch.Net;
import swarm_wars_library.engine.CollisionDetection;
import swarm_wars_library.entities.AbstractEntity;
import swarm_wars_library.entities.Bot;
import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.entities.PlayerAI;
import swarm_wars_library.entities.PlayerN;
import swarm_wars_library.entities.Turret;
import swarm_wars_library.fsm.OtherFSMBuilder;
import swarm_wars_library.fsm_ui.UI;
import swarm_wars_library.game_screens.GAMESCREEN;
import swarm_wars_library.graphics.RenderLayers;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.input.Input;
import swarm_wars_library.map.Map;
import swarm_wars_library.network.MessageHandlerMulti;
import swarm_wars_library.network.NetworkClientFunctions;
import swarm_wars_library.sound.PlayBackgroundMusic;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;
import swarm_wars_library.swarm_select.SwarmSelect;

public class SwarmWars extends PApplet {

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
  SwarmSelect swarmSelect;
  UI fsmUI;
  PlayBackgroundMusic playBackgroundMusic;

  // Game screens 
  GAMESCREEN currentScreen = GAMESCREEN.FSMUI;
  int frameNumber;

  // Networking
  public static boolean playNetworkGame = true;

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
    // this.soundSetup();

    // NETWORKING - Starts server and gets ids
    this.networkSetup();
    this.frameNumber = 1;

    this.uiSetup();
  }
  //=========================================================================//
  // Processing Game Loop                                                    //
  //=========================================================================//
  public void draw() {
    switch(this.currentScreen){
      case START:
        // TODO: Let the user select mode
        break;
      case FSMUI:
        this.uiUpdate();
        break;
      case SWARMSELECT:
        this.swarmSelectUpdate();
        break;
      case GAME:
        this.networkUpdate();
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
      Bot bot = new Bot(ENTITY.PLAYER1_BOT, i, true);
      this.player1TakeDamage.add(bot);
    }

    // player2 setup
    this.player2 = new PlayerN(this, ENTITY.PLAYER2);
    this.player2TakeDamage.add(this.player2);
    this.player2DealDamage.addAll(player2.getBullets());
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER2_BOT, i, false);
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
  // Sound  Setup                                                            //
  //=========================================================================//
  public void soundSetup(){
    this.playBackgroundMusic = new PlayBackgroundMusic(this);
  }

  //=========================================================================//
  // Swarm Select Setup                                                      //
  //=========================================================================//
  public void swarmSelectSetup(){
    this.swarmSelect = new SwarmSelect(this);
  }

  //=========================================================================//
  // UI Setup                                                                //
  //=========================================================================//
  public void uiSetup(){
    this.fsmUI = new UI(this);
    if (playNetworkGame) networkConnect();
  }

  public void networkSetup() {
    if(!playNetworkGame) {
      // map.setPlayerId(1);
      // map.setEnemyId(2);
      Map.getInstance().setPlayerId(1);
      Map.getInstance().setEnemyId(2);
    } else {
      // start network
      NetworkClientFunctions.startNewtork();
      System.out.println("Step 1");
    }
  }

  public void networkConnect() {
    // TODO: Make a UI
    map.setPlayerId(NetworkClientFunctions.getPlayerIdFromUser());
    map.setEnemyId(map.getPlayerId() == 1 ? 2 : 1);
    NetworkClientFunctions.cleanBuffer();


    // NETWORKING this needs to be integrated with FSM and selection
    NetworkClientFunctions.sendConnect(map.getPlayerId());
    System.out.println("Step 2");
    // NetworkClientFunctions.sendSetup(map.getPlayerId());
  }

  public void networkingGameStart() {
    if(!playNetworkGame) return;
    NetworkClientFunctions.sendStart(map.getPlayerId());
    NetworkClientFunctions.awaitStart();
  }

  public void networkingSendPlayerInputs() {
    if(!playNetworkGame) return;
    NetworkClientFunctions.sendOperation(
            map.getPlayerId(),
            frameNumber,
            player1.getInput()
    );
  }

  public void networkingGetEnemyInputs() {
    if(!playNetworkGame) return;
    java.util.Map<String, Object> messageIn = NetworkClientFunctions.getPackage(map.getEnemyId(), frameNumber++);

    if(messageIn.containsKey("W")) player2.getInput().setMove(UP, (Integer) messageIn.get("W"));
    if(messageIn.containsKey("A")) player2.getInput().setMove(LEFT, (Integer) messageIn.get("A"));
    if(messageIn.containsKey("S")) player2.getInput().setMove(DOWN, (Integer) messageIn.get("S"));
    if(messageIn.containsKey("D")) player2.getInput().setMove(RIGHT, (Integer) messageIn.get("D"));

  }

  /*
  public void networkingGetEnemySetup() {
    if (!playNetworkGame) return;
    java.util.Map<String, Object> setup = null;
    while((setup = NetworkClientFunctions.getPackage(map.getEnemyId(), 0)) == null) {
      try{
        Thread.sleep(1000/60);
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    OtherFSMBuilder otherFSMBuilder = new OtherFSMBuilder();
    otherFSMBuilder.setOtherFSM(setup);
  }
  */

  public void networkUpdate() {
    this.networkingSendPlayerInputs();
    this.networkingGetEnemyInputs();
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
    // NETWORKING CAN WE PUT A BLOCKER HERE TO STOP ANY CHANGES HAPPENING BETWEEN PUT AND GET?
    // NETWORKING PUT DATA HERE
    // NETWORKING GET DATA HERE

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
  // Swarm Select update                                                     //
  //=========================================================================//
  public void swarmSelectUpdate(){
    this.swarmSelect.update();
    if(this.swarmSelect.getGameScreen() == GAMESCREEN.GAME){
      CommsGlobal.reset();
      this.commsSetup();
      this.entitiesSetup();
      CommsGlobal.update();
      this.renderSetup();
      this.currentScreen = this.swarmSelect.getGameScreen();
    }
  }

  //=========================================================================//
  // UI update                                                               //
  //=========================================================================//
  public void uiUpdate(){
    this.fsmUI.update();
    if(this.fsmUI.getGameScreen() == GAMESCREEN.SWARMSELECT){
      this.swarmSelectSetup();
      this.currentScreen = this.fsmUI.getGameScreen();
    }
  }

  //=========================================================================//
  // Event listeners                                                         //
  //=========================================================================//
  // TODO is it possible to have these in a switch statement rather than 
  // a switch statement inside them?

  public void keyPressed() {
    if(this.currentScreen.equals(GAMESCREEN.GAME)){
      this.player1.listenKeyPressed(this.keyCode);
    }
  }

  public void keyReleased() {
    if(this.currentScreen.equals(GAMESCREEN.GAME)) {
      this.player1.listenKeyReleased(this.keyCode);
    }
  }

  public void mousePressed() {
    switch(this.currentScreen){
      case START:
        break;
      case FSMUI:
        this.fsmUI.listenMousePressed();
        break;
      case SWARMSELECT:
        this.swarmSelect.listenMousePressed();
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
        this.fsmUI.listenMouseReleased();
        break;
      case SWARMSELECT:
        this.swarmSelect.listenMouseReleased();
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
}
