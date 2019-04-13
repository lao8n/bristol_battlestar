package swarm_wars_library.map;

import swarm_wars_library.engine.Vector2D;
import java.util.*;

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
  private int map_width;
  private int map_height;
  private List<Vector2D> backgroundStars;

  //make the constructor private so that this class cannot be
  //instantiated
  private Map(){}

  //Get the only object available
  public static Map getInstance(){
     return instance;
  }
  
  public void setMapDimensions(int map_width, int map_height){
    this.map_width = map_width;
    this.map_height = map_height;
  }

  public void setMapStars(){
    this.backgroundStars = new ArrayList<Vector2D>();
    for(int i = 0; i < 1000; i++){
      Vector2D v2d = new Vector2D(Math.random() * map_width, 
                                  Math.random() * map_height);
      backgroundStars.add(i, v2d);
    }
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
}