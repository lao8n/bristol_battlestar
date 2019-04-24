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
  private int map_width = 1500;
  private int map_height = 1500;
  private int playerScale = 15;
  private int turretScale = 15;
  private int botScale = 8;
  private int bulletScale = 5;
  private int starScale = 1;
  private int numStars = 100;
  private int numBotsPerPlayer = 100;
  private int numTurrets = 5;
  private int numBulletsPerMagazine = 20;
  private List<Vector2D> backgroundStars;
  private Vector2D player1StartingLocation;
  private Vector2D player2StartingLocation;

  //make the constructor private so that this class cannot be
  //instantiated
  private Map(){
    this.backgroundStars = new ArrayList<Vector2D>();
    for(int i = 0; i < this.numStars; i++){
      Vector2D v2d = new Vector2D(Math.random() * map_width, 
                                  Math.random() * map_height);
      backgroundStars.add(i, v2d);
    }
    this.player1StartingLocation = 
      new Vector2D(this.map_width * Math.random(), 
                   this.map_height * Math.random());
    this.player2StartingLocation = 
      new Vector2D(this.map_width * Math.random(), 
                   this.map_height * Math.random());
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

  public Vector2D getPlayerStartingLocation(ENTITY tag){
    if(tag.equals(ENTITY.PLAYER1)){
      return this.player1StartingLocation;
    }
    return this.player2StartingLocation;
  }
}