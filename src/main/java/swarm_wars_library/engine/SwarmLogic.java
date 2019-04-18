package swarm_wars_library.engine;

import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.swarm_algorithms.SwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.DefensiveShell;
import swarm_wars_library.swarm_algorithms.BoidsFlock;
import swarm_wars_library.swarm_algorithms.ScoutShell;
/**
 * SwarmLogic Class is a manager class for Swarm behaviour. It includes,
 * getter and setter methods for Transform, id and Global Comms and 
 * crucially the update method which runs the applySwarmAlgorithm() method.
 * What method this is depends upon the constructed class (currently
 * DefensiveShell, BoidsFlock, and ScoutShell) and their specific 
 * implementations.
 * <p>
 * The idea here is to follow the State design pattern, where each State
 * (in this case Swarm Algorithm) implements a different version of the 
 * Swarm Rule interface (although here we use an abstract class to allow 
 * state and concrete implementation). The advantage of this is we can
 * follow the adage 'code to the interface not the implementation'.
 * <p>
 * Issues
 * 1. There is currently on way to switch between the Swarm Algorithms. In
 *    fact, the choice is hard-coded in as the starting implementation in 
 *    the bot section of SwarmWars (search for 
 *    bot.selectStartingSwarmAlgorithm). How this should work with FSM/player
 *    input wasn't clear to me so I wanted to wait before proceeding.
 */
public class SwarmLogic {

  private RigidBody rb;
  private CommsGlobal comms;
  private Transform transform;
  private int id;
  private static int counter = 1;
  private SwarmAlgorithm swarm_algo;

  public SwarmLogic(Transform transform, RigidBody rb, int id){
    this.rb = rb;
    this.transform = transform;
    this.id = id;
    rb.setMaxSpeed(15);
  }

  public Transform getTransform(){
    return this.transform;
  }

  public void setTransform(Transform transform){
    this.transform = transform;
  }

  public int getId(){
    return this.id;
  }

  public void setComms(CommsGlobal comms){
    this.comms = comms;
  }

  public void selectSwarmAlgorithm(String swarm_algorithm){
    if(swarm_algorithm == "defensive_shell"){
      swarm_algo = new DefensiveShell(this.comms, this.id, this.transform, this.rb);
    }
    else if (swarm_algorithm == "boids_flock"){
      swarm_algo = new BoidsFlock(this.comms, this.id, this.transform, this.rb);
    }
    else if (swarm_algorithm == "scout_shell"){
      swarm_algo = new ScoutShell(this.comms, this.id, this.transform, this.rb);
    }
  }

  public void update(){
    this.swarm_algo.applySwarmAlgorithm();
  }
}
