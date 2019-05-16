package swarm_wars_library.engine;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.physics.RigidBody;
import swarm_wars_library.physics.Transform;
import swarm_wars_library.fsm.FSMManager;
import swarm_wars_library.swarm_algorithms.*;

public class SwarmLogic {

  private ENTITY tag;
  private RigidBody rb;
  private Transform transform;
  private int id;
  private AbstractSwarmAlgorithm swarm_algo;
  private FSMManager fsmManager = FSMManager.getInstance();
  private SWARMALGORITHM lastSwarmAlgorithm = SWARMALGORITHM.DEFENDFLOCK;

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
  public void selectSwarmAlgorithm(SWARMALGORITHM swarmAlgorithm){
    switch(swarmAlgorithm){
      case DEFENDSHELL:
        this.swarm_algo = 
          new DefendShellSwarmAlgorithm(this.tag, this.id, this.transform, 
                                        this.rb);
        break;
      case DEFENDFLOCK:
        this.swarm_algo = 
          new DefendFlockSwarmAlgorithm(this.tag, this.id, this.transform, 
                                        this.rb);
        break;
      case SCOUTRANDOM:
        this.swarm_algo = 
          new ScoutRandomSwarmAlgorithm(this.tag, this.id, this.transform, 
                                        this.rb);
        break;
      case SCOUTBEE:
        this.swarm_algo = 
          new ScoutBeeSwarmAlgorithm(this.tag, this.id, this.transform, 
                                     this.rb);
        break;
      case ATTACKSUICIDE:
        this.swarm_algo =
          new AttackSuicideSwarmAlgorithm(this.tag, this.id, this.transform,
                                          this.rb);
        break;
      default:
        break;
    }
  }

  //=========================================================================//
  // Update                                                                  //
  //=========================================================================//
  public void update(boolean transitionsFlag){
    if(transitionsFlag){
      if(this.lastSwarmAlgorithm != this.fsmManager.getSwarmAlgorithm()){
        this.selectSwarmAlgorithm(fsmManager.getSwarmAlgorithm());
      }
      this.swarm_algo.applySwarmAlgorithm();
      this.lastSwarmAlgorithm = fsmManager.getSwarmAlgorithm();
    }
    else {
      this.swarm_algo.applySwarmAlgorithm();
    }
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
