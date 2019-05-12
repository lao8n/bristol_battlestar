package swarm_wars_library.fsm;

import java.util.HashMap;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class FSMManager implements IFSMManagerUI, IFSMManagerSwarmLogic, 
  IFSMManagerAlgorithmSelect {

  private static FSMManager instance = new FSMManager();
  private Integer currentFSMStateId = 1;
  private HashMap<Integer, FSMStateTransition> mapFSMStateTransition = 
    new HashMap<Integer, FSMStateTransition>();

  //=========================================================================//
  // FSM Manager State Constructor                                           //
  //=========================================================================//
  private FSMManager(){
  }

  //=========================================================================//
  // FSM Manager Get Instance                                                //
  //=========================================================================//
  public static FSMManager getInstance(){
    return instance;
  }

  //=========================================================================//
  // FSM Manager UI Methods                                                  //
  //=========================================================================//
  @Override
  public void addFSMState(int stateId, FSMSTATE fsmState) {
    FSMStateTransition fsmStateTransition = 
      new FSMStateTransition(stateId, fsmState);
    this.mapFSMStateTransition.put(stateId, fsmStateTransition);
  }

  @Override
  public void addTransition(int fromStateId, int toStateId, 
    FSMVARIABLE variable, FSMCOMPARISON comparison, double value) {
    this.mapFSMStateTransition.get(fromStateId)
                              .addTransition(toStateId, variable, comparison, 
                                             value);
  }

  //=========================================================================//
  // FSM Manager Swarm Logic methods                                         //
  //=========================================================================//
  @Override
  public SWARMALGORITHM getSwarmAlgorithm(){
    this.currentFSMStateId = 
      this.mapFSMStateTransition.get(this.currentFSMStateId)
                                .getFSMStateId();
    return this.mapFSMStateTransition.get(this.currentFSMStateId)
                                     .getSwarmAlgorithm();
  }

  @Override
  public SWARMALGORITHM getStartingSwarmAlgorithm(){
    return SWARMALGORITHM.valueOf(this.currentFSMStateId);
  }

  @Override
  public double[] getSwarmAlgorithmWeights() {
    return this.mapFSMStateTransition.get(this.currentFSMStateId)
                                     .getSwarmAlgorithmWeights();
  }

  //=========================================================================//
  // FSM Manager Swarm Algorithm Select methods                              //
  //=========================================================================//
  @Override
  public HashMap<Integer, FSMStateTransition> getMapFSMStateTransition(){
    return this.mapFSMStateTransition;
  }

  
  @Override
  public void setSwarmAlgorithm(Integer stateId, SWARMALGORITHM 
    swarmAlgorithm){
    this.mapFSMStateTransition.get(stateId).setSwarmAlgorithm(swarmAlgorithm);
  }

  @Override
  public void setSwarmAlgorithmWeights(Integer stateId, double weight1, 
    double weight2, double weight3){
    this.mapFSMStateTransition.get(stateId)
                              .setSwarmAlgorithmWeights(weight1, 
                                                        weight2, 
                                                        weight3);
  }
}