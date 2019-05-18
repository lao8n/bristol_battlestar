package swarm_wars_library;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;

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
import swarm_wars_library.game_screens.GameOver;
import swarm_wars_library.graphics.RenderLayers;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.map.Map;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.network.Constants;
import swarm_wars_library.network.Headers;
import swarm_wars_library.network.NetworkClientFunctions;
import swarm_wars_library.sound.PlayBackgroundMusic;
import swarm_wars_library.swarm_select.SwarmSelect;
import swarm_wars_library.sound.SoundMixer; 

public class SwarmWars extends PApplet {

  // To play single player set playNetworkGame to false

  // Networking
  public static boolean playNetworkGame = true;

  // Players
  PlayerN playerMe;
  PlayerN playerEnemy;
  PlayerAI playerAI;

  // Entity lists that has all our game things.
  ArrayList <AbstractEntity> player1TakeDamage;  
  ArrayList <AbstractEntity> player1DealDamage;
  ArrayList <AbstractEntity> player2TakeDamage;
  ArrayList <AbstractEntity> player2DealDamage;
  ArrayList <AbstractEntity> gameObjectsTakeDamage;
  ArrayList <AbstractEntity> gameObjectsDealDamage;

  // Game Backend Objects
  Map map;
  RenderLayers renderLayers;
  SwarmSelect swarmSelect;
  UI fsmUI;
  PlayBackgroundMusic playBackgroundMusic;
  GameOver gameOver;

  // Game screens 
  GAMESCREEN currentScreen = GAMESCREEN.FSMUI;
  int frameNumber;

  // sound setup
  SoundMixer soundMixer;

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
    // NETWORK - networking setup needs map for Id but map uses randgen before 
    // seed.....
    this.map = Map.getInstance();
    this.networkSetup();
    this.uiSetup();
    this.soundSetup();
    this.gameOverSetup();

    // NETWORKING - Starts server and gets ids
    this.frameNumber = 1;
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
        this.updateMouse();
        this.playerMe.updateInputBuffer();
        this.networkUpdate();
        this.checkCollisions();
        this.entitiesUpdate();
        this.renderLayersUpdate();
        CommsGlobal.update();
        this.checkForGameOver();
        break;
      case GAMEOVER:
        this.gameOverUpdate();
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
  // Game Setup                                                              //
  //=========================================================================//
  public void gameSetup(){
    RandomGen.resetSeed();
    CommsGlobal.reset();
    this.commsSetup();
    this.entitiesSetup();
    CommsGlobal.update();
    this.renderSetup();
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

    this.map.generateStartingPositions();

    // setup networking game
    if(playNetworkGame) {
      // setup if player is player 1
      if (playNetworkGame && map.getPlayerId() == 1) {
        this.playerMe = new PlayerN(this, ENTITY.PLAYER1);
        this.playerEnemy = new PlayerN(this, ENTITY.PLAYER2);
        this.player1TakeDamage.add(this.playerMe);
        this.player1DealDamage.addAll(playerMe.getBullets());
        this.player2TakeDamage.add(this.playerEnemy);
        this.player2DealDamage.addAll(playerEnemy.getBullets());
      }
      // setup if player is player 2
      if (map.getPlayerId() == 2) {
        this.playerEnemy = new PlayerN(this, ENTITY.PLAYER1);
        this.playerMe = new PlayerN(this, ENTITY.PLAYER2);
        this.player1TakeDamage.add(this.playerEnemy);
        this.player1DealDamage.addAll(playerEnemy.getBullets());
        this.player2TakeDamage.add(this.playerMe);
        this.player2DealDamage.addAll(playerMe.getBullets());
      }
    }

    // single player
    if(!playNetworkGame){
      this.playerMe = new PlayerN(this, ENTITY.PLAYER1);
      this.playerAI = new PlayerAI(this, ENTITY.PLAYER2);
      this.player1TakeDamage.add(this.playerMe);
      this.player1DealDamage.addAll(playerMe.getBullets());
      this.player2TakeDamage.add(this.playerAI);
      this.player2DealDamage.addAll(playerAI.getBullets());
    }

    // setup bots
    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER1_BOT, i, true);
      this.player1TakeDamage.add(bot);
      this.player1DealDamage.add(bot);
    }

    for(int i = 0; i < this.map.getNumBotsPerPlayer(); i++){
      Bot bot = new Bot(ENTITY.PLAYER2_BOT, i, false);
      this.player2TakeDamage.add(bot);
      this.player2DealDamage.add(bot);
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
    this.soundMixer = new SoundMixer(this);
  }

  //=========================================================================//
  // Swarm Select Setup                                                      //
  //=========================================================================//
  public void swarmSelectSetup(){
    CommsGlobal.reset();
    this.swarmSelect = new SwarmSelect(this);
  }

  //=========================================================================//
  // UI Setup                                                                //
  //=========================================================================//
  public void uiSetup(){
    this.fsmUI = new UI(this);
    if (playNetworkGame) networkConnect();
  }

  //=========================================================================//
  // Networking Setup                                                        //
  //=========================================================================//
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

  //=========================================================================//
  // Networking Update                                                       //
  //=========================================================================//
  public void networkUpdate() {
    this.networkingSendPlayerInputs();
    this.networkingGetEnemyInputs();
    frameNumber++;
  }

  public void networkingSendPlayerInputs() {
    if(!playNetworkGame) return;
    int id = map.getPlayerId();
    java.util.Map<String, Object> m = new HashMap<String, Object>();
    m.put(Headers.TYPE, Constants.OPERATION);
    m.put(Headers.PLAYER, id);
    m.put(Headers.W, playerMe.getInputUp());
    m.put(Headers.S, playerMe.getInputDown());
    m.put(Headers.A, playerMe.getInputLeft());
    m.put(Headers.D, playerMe.getInputRight());
    m.put(Headers.MOUSE_X, playerMe.getInputMouseX());
    m.put(Headers.MOUSE_Y, playerMe.getInputMouseY());
    m.put(Headers.MOUSE_PRESSED, playerMe.getInputMouse());
    NetworkClientFunctions.sendOperation(id, frameNumber, m);
  }

  public void networkingGetEnemyInputs() {
    if(!playNetworkGame) return;
    java.util.Map<String, Object> messageIn = NetworkClientFunctions.getPackage(map.getEnemyId(), frameNumber);

    if(messageIn == null && map.gameEnded()){
      return;
    }

    if(messageIn.containsKey("W")) playerEnemy.setInputUp((Integer) messageIn.get("W"));
    if(messageIn.containsKey("S")) playerEnemy.setInputDown((Integer) messageIn.get("S"));
    if(messageIn.containsKey("A")) playerEnemy.setInputLeft((Integer) messageIn.get("A"));
    if(messageIn.containsKey("D")) playerEnemy.setInputRight((Integer) messageIn.get("D"));

    if(messageIn.containsKey("X")) playerEnemy.setInputMouseX((Integer) messageIn.get("X"));
    if(messageIn.containsKey("Y")) playerEnemy.setInputMouseY((Integer) messageIn.get("Y"));
    if(messageIn.containsKey("C")) playerEnemy.setInputMouse((Integer) messageIn.get("C"));
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
      if(!this.player1DealDamage.getClass().equals(Bot.class)){
        this.player1DealDamage.get(i).update();
      }
    }
    // Update player2
    for(int i = 0; i < this.player2TakeDamage.size(); i++){
      this.player2TakeDamage.get(i).update();
    }
    for(int i = 0; i < this.player2DealDamage.size(); i++){
      if(!this.player1DealDamage.getClass().equals(Bot.class)){
        this.player2DealDamage.get(i).update();
      }
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
      this.gameSetup();
      this.currentScreen = this.swarmSelect.getGameScreen();
      this.swarmSelect.resetGameScreen();
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
      this.fsmUI.resetGameScreen();
    }
  }

  //=========================================================================//
  // Game over                                                               //
  //=========================================================================//

  public void gameOverSetup(){
    this.gameOver = new GameOver(this);
  }

  public void checkForGameOver() {
    if(playerMe.getHealth() <= 0){
      System.out.println("Dead....");
      map.setGameEnded(true);
    }
    if(playNetworkGame && playerEnemy.getHealth() <= 0){
      System.out.println("Enemy dead...");
      map.setGameEnded(true);
    }

    if(!playNetworkGame && playerAI.getHealth() <= 0){
      System.out.println("Enemy dead...");
      map.setGameEnded(true);
    }

    if(map.gameEnded()){
      this.currentScreen = GAMESCREEN.GAMEOVER;
      if(playNetworkGame) NetworkClientFunctions.sendEnd(map.getPlayerId());
    }
  }

  public void gameOverUpdate() {
    this.gameOver.update();
    if(this.gameOver.getGameScreen() == GAMESCREEN.FSMUI){
      this.currentScreen = this.gameOver.getGameScreen();
      this.gameOver.resetCurrentScreen();
      this.uiSetup();
      this.frameNumber = 1;
      map.setGameEnded(false);
    }
  }

  //=========================================================================//
  // Event listeners                                                         //
  //=========================================================================//
  // TODO is it possible to have these in a switch statement rather than 
  // a switch statement inside them?

  public void keyPressed() {
    switch(this.currentScreen) {
      case GAME:
        this.playerMe.listenKeyPressed(this.keyCode);
        break;
      default:
    }
  }

  public void keyReleased() {
      switch(this.currentScreen) {
        case GAME:
          this.playerMe.listenKeyReleased(this.keyCode);
          break;
        default:
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
        this.playerMe.listenMousePressed();
        break;
      case GAMEOVER:
        this.gameOver.listenMousePressed();
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
        this.playerMe.listenMouseReleased();

        break;
      case GAMEOVER:
        this.gameOver.listenMouseReleased();
        break;
      default:
        // TODO Add error
    }
  }

  private void updateMouse() {
    this.playerMe.setInputMouseX(this.mouseX);
    this.playerMe.setInputMouseY(this.mouseY);
  }
}
