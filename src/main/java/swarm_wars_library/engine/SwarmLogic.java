package swarm_wars_library.engine;

import swarm_wars_library.entities.ENTITY;
import swarm_wars_library.map.Map;
import swarm_wars_library.entities.STATE;
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
  private int playerId;
  private int botMaxSpeed = Map.getInstance().getBotMaxSpeed();
  private STATE state = STATE.ALIVE;

  //=========================================================================//
  // Constructor                                                             //
  //=========================================================================//
  public SwarmLogic(ENTITY tag, Transform transform, RigidBody rb, int id){
    this.tag = tag;
    this.rb = rb;
    this.transform = transform;
    this.id = id;
    this.rb.setMaxSpeed(botMaxSpeed);
    this.playerId = ENTITY.entityToPlayerId(tag);
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
      // case SCOUTANT:
      //   this.swarm_algo =
      //           new ScoutBeeSwarmAlgorithm(this.tag, this.id, this.transform,
      //                   this.rb);
      //   break;
      case SPECIALSUICIDE:
        this.swarm_algo =
          new SpecialSuicideSwarmAlgorithm(this.tag, this.id, this.transform,
                                          this.rb);
        this.setState(swarm_algo.getState());
        break;
      case SPECIALGHOST:
        this.swarm_algo =
                new SpecialGhostSwarmAlgorithm (this.tag, this.id, this.transform,
                        this.rb);
        break;
      // case SPECIALSTAR:
      //   this.swarm_algo =
      //         new SpecialSuicideSwarmAlgorithm(this.tag, this.id, this.transform,
      //                 this.rb);
      //   break;
      default:
        break;
    }
  }

  public void selectStartingSwarmAlgorithm() {
    this.selectSwarmAlgorithm(this.fsmManager.getStartingSwarmAlgorithm(playerId));
  }

  //=========================================================================//
  // Update                                                                  //
  //=========================================================================//
  public void update(boolean transitionsFlag){
    if(transitionsFlag){
      if(this.lastSwarmAlgorithm != this.fsmManager.getSwarmAlgorithm(playerId)){
        this.selectSwarmAlgorithm(fsmManager.getSwarmAlgorithm(playerId));
      }
      this.swarm_algo.applySwarmAlgorithm();
      this.lastSwarmAlgorithm = fsmManager.getSwarmAlgorithm(playerId);
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

  public STATE getState() { return this.state; }

  public void setState(STATE state) { this.state = state; }
}
