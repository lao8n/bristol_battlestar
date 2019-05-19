package swarm_wars_library.map;

import java.util.ArrayList;
import java.util.List;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.physics.Vector2D;

/**
 * Map Class holds information about the underlying game map (not the 
 * rendered map which is different). It provides getter access to the
 * map width and map height, and to the position of the background stars
 * <p>
 * All positions of objects are now relative to the Map Class and its 
 * dimensions and not the sketch dimensions. Rendering is achieved by
 * translating between the two, but game logic should be with respect to 
 * Map dimensions only.
 * <p>
 * Map Class is implemented with a private constructor and a static single
 * instance. This uses the well-known singleton pattern.
 * Map Class is instantiated in SwarmWars.java and setMapDimensions() and 
 * setMapStars() are called in setup(). 
 * Map is called in SwarmAlgorithm.java where the concrete implementation 
 * of avoidEdge() is. For similar reasons Map is also accessed in Input and
 * edgeCheck().
 * <p> 
 * Issues
 * 1. Does Singleton pattern with static constructor work with networking?
 * 2. SwarmWars.java has in the game loop a for loop over star objects 
 *    to render them. Does this further mix game loop role to include both
 *    rendering and game logic?
 * 3. Currently, some actions occur off the game map, including bullets
 *    and collisions. I think the solution is to clean up rendering and
 *    being more explicit about where things happen. Currently there is a
 *    loop over entity objects which checks for map location and screen
 *    location before deciding whether to render. But as bullets are 
 *    not in entity list this doesn't work. Might need to work on better 
 *    solution with Valia/Aurora etc.
 * @author nd17792
 */
public class Map {

  private static Map instance = new Map();
  private int map_width = 2000;
  private int map_height = 2000;

  // Entity sizes
  private int playerScale = 15;
  private int turretScale = 15;
  private int botScale = 5;
  private int bulletScale = 5;
  private int missileScale=15;
  private int starScale = 1;
  private int numStars = 0;

  // Number of Entities
  private int numBotsPerPlayer = 150;
  private int numTurrets = 3;

  // Shooters
  private int shooterBulletTimer = 2;
  private int shooterMisiileTimer=10;
  private int numBulletsPerMagazine = 20;
  private int turretBulletForce = 15;
  private int playerNBulletForce = 25;
  private int playerAIBulletForce = 15;
  private int numMissilesPerMagazine = 20;

  // Movement
  private int playerMoveForce = 16;
  private int botMaxSpeed = 20;

  private List<Vector2D> backgroundStars;
  private Vector2D player1StartingLocation;
  private Vector2D player2StartingLocation;

  private int playerId;
  private int enemyId;


  private boolean gameEnded = false;

  //make the constructor private so that this class cannot be
  //instantiated
  private Map(){
    RandomGen.resetSeed();
    this.backgroundStars = new ArrayList<Vector2D>();
    for(int i = 0; i < this.numStars; i++){
      Vector2D v2d = new Vector2D(RandomGen.getRand() * map_width,
                                  RandomGen.getRand() * map_height);
      backgroundStars.add(i, v2d);
    }

    this.generateStartingPositions();

  }

  public void generateStartingPositions() {
    this.player1StartingLocation =
            new Vector2D(this.map_width * RandomGen.getRand(),
                    this.map_height * RandomGen.getRand());
    this.player2StartingLocation =
            new Vector2D(this.map_width * RandomGen.getRand(),
                    this.map_height * RandomGen.getRand());
  }

  //Get the only object available
  public static Map getInstance(){
     return instance;
  }

  public List<Vector2D> getMapStars(){
    return this.backgroundStars;
  }
  public int getMapWidth(){
    return this.map_width;
  }

  public int getMapHeight(){
    return this.map_height;
  }

  public int getPlayerScale(){
    return this.playerScale;
  }

  public int getBotScale(){
    return this.botScale;
  }

  public int getTurretScale(){
    return this.turretScale;
  }

  public int getBulletScale(){
    return this.bulletScale;
  }
  public int getMissileScale(){
    return this.missileScale;
  }

  public int getStarScale(){
    return this.starScale;
  }

  public int getNumBotsPerPlayer(){
    return this.numBotsPerPlayer;
  }

  public int getNumTurrets(){
    return this.numTurrets;
  }

  public int getNumBulletsPerMagazine(){
    return this.numBulletsPerMagazine;
  }

  public int getNumMissilesPerMagazine(){
    return this.numMissilesPerMagazine;
  }

  public Vector2D getPlayerStartingLocation(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1)){
      return this.player1StartingLocation;
    }
    return this.player2StartingLocation;
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public int getEnemyId() {
    return enemyId;
  }

  public void setEnemyId(int enemyId) {
    this.enemyId = enemyId;
  }

  public int getTurretBulletForce() {
    return turretBulletForce;
  }

  public int getBulletTimer() {
    return shooterBulletTimer;
  }

  public int getMissileTimer() {
    return shooterMisiileTimer;
  }

  public int getPlayerNBulletForce() {
    return playerNBulletForce;
  }

  public int getPlayerAIBulletForce() {
    return playerAIBulletForce;
  }

  public int getPlayerMoveForce() {
    return playerMoveForce;
  }

  public int getBotMaxSpeed() {
    return botMaxSpeed;
  }

  public boolean gameEnded() {
    return gameEnded;
  }

  public void setGameEnded(boolean gameEnded) {
    this.gameEnded = gameEnded;
  }
}