package swarm_wars_library.map;

public class Map {

  private static Map instance = new Map();
  private int map_width;
  private int map_height;

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

  public int getMapWidth(){
    return this.map_width;
  }

  public int getMapHeight(){
    return this.map_height;
  }
}