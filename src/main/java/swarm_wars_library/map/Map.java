package swarm_wars_library.map;

import swarm_wars_library.engine.Vector2D;
import java.util.*;

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