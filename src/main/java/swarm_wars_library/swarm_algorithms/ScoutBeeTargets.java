package swarm_wars_library.swarm_algorithms;

import java.util.Set;
import java.util.HashSet;

import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Vector2D;

public class ScoutBeeTargets {
  private static ScoutBeeTargets instance = new ScoutBeeTargets();
  private Set<Vector2D> setTargets = new HashSet<Vector2D>();


  //=========================================================================//
  // Scout Bee Swarm Rules Constructor                                       //
  //=========================================================================//
  private ScoutBeeTargets(){
  }

  //=========================================================================//
  // Scout Bee Get Instance                                                  //
  //=========================================================================//
  public static ScoutBeeTargets getInstance(){
    return instance;
  }

  //=========================================================================//
  // Scout Bee Target Getters and Setters                                    //
  //=========================================================================//
  public Vector2D getRandomTarget(){
    Vector2D[] arrayTargets = this.setTargets.toArray(new Vector2D[0]);
    return arrayTargets[RandomGen.getInt(arrayTargets.length)];
  }

  public int getTargetSize(){
    return this.setTargets.size();
  }

  public void addTarget(Vector2D target){
    this.setTargets.add(target);
  }

  public void removeTarget(Vector2D target){
    this.setTargets.remove(target);
  }
}