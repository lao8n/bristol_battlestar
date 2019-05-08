package swarm_wars_library.engine;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.swarm_algorithms.AbstractSwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.DefendShellSwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.DefendFlockSwarmAlgorithm;
import swarm_wars_library.swarm_algorithms.ScoutRandomSwarmAlgorithm;

public class SwarmLogic {

  private ENTITY tag;
  private RigidBody rb;
  private Transform transform;
  private int id;
  private AbstractSwarmAlgorithm swarm_algo;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public SwarmLogic(ENTITY tag, Transform transform, RigidBody rb, int id){
    this.tag = tag;
    this.rb = rb;
    this.transform = transform;
    this.id = id;
    this.rb.setMaxSpeed(15);
  }

  //=========================================================================//
  // Swarm Algorithm Selection                                               //
  //=========================================================================//
  public void selectSwarmAlgorithm(String swarm_algorithm){
    if(swarm_algorithm == "defendShell"){
      swarm_algo = 
        new DefendShellSwarmAlgorithm(this.tag, this.id, this.transform, 
                                      this.rb);
    }
    else if (swarm_algorithm == "defendFlock"){
      swarm_algo = 
        new DefendFlockSwarmAlgorithm(this.tag, this.id, this.transform, 
                                      this.rb);
    }
    else if (swarm_algorithm == "scoutRandom"){
      swarm_algo = 
        new ScoutRandomSwarmAlgorithm(this.tag, this.id, this.transform, 
                                      this.rb);
    }
  }

  //=========================================================================//
  // Update                                                                  //
  //=========================================================================//
  public void update(){
    this.swarm_algo.applySwarmAlgorithm();
  }

  //=========================================================================//
  // Misc getters and setters                                                //
  //=========================================================================//
  public Transform getTransform(){
    return this.transform;
  }

  public void setTransform(Transform transform){
    this.transform = transform;
  }

  public int getId(){
    return this.id;
  }
}
