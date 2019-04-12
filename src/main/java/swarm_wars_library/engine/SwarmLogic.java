package swarm_wars_library.engine;

import swarm_wars_library.swarm_algorithms.SwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.DefensiveShell;
import swarm_wars_library.swarm_algorithms.BoidsFlock;

public class SwarmLogic {

  private RigidBody rb;
  private CommsGlobal comms;
  private Transform transform;
  private int id;
  private static int counter = 1;
  private SwarmAlgorithm swarm_algo;

  public SwarmLogic(Transform transform, RigidBody rb){
    this.rb = rb;
    this.transform = transform;
    this.id = counter;
    counter++;
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
  }

  public void update(){
    this.swarm_algo.applySwarmAlgorithm();
  }
}
