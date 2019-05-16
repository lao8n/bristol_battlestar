package swarm_wars_library.fsm;

import java.util.HashMap;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class FSMManager implements IFSMManagerUI, IFSMManagerSwarmLogic, 
  IFSMManagerAlgorithmSelect {

  private static FSMManager instance = new FSMManager();
  private HashMap<Integer, Integer> currentFSMStateId = new HashMap<>();
  private HashMap<Integer, HashMap<Integer, FSMStateTransition>> mapFSMStateTransition =
    new HashMap<Integer, HashMap<Integer, FSMStateTransition>>();
  private HashMap<Integer, HashMap<Integer, FSMSTATE>> mapFSMStates =
          new HashMap<Integer, HashMap<Integer, FSMSTATE>>();

  //=========================================================================//
  // FSM Manager State Constructor                                           //
  //=========================================================================//
  private FSMManager(){
    currentFSMStateId.put(1,1);
    currentFSMStateId.put(2,1);
    mapFSMStateTransition.put(1, new HashMap<Integer, FSMStateTransition>());
    mapFSMStateTransition.put(2, new HashMap<Integer, FSMStateTransition>());
    mapFSMStates.put(1, new HashMap<Integer, FSMSTATE>());
    mapFSMStates.put(2, new HashMap<Integer, FSMSTATE>());
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
  public void addFSMState(int playerId, int stateId, FSMSTATE fsmState) {
    FSMStateTransition fsmStateTransition = 
      new FSMStateTransition(stateId, fsmState);
    if (this.mapFSMStates.get(playerId) == null) {
      System.out.println("WDNMD");
    }
    this.mapFSMStates.get(playerId).put(stateId, fsmState);
    this.mapFSMStateTransition.get(playerId).put(stateId, fsmStateTransition);
  }

  @Override
  public void addTransition(int playerId, int fromStateId, int toStateId,
    FSMVARIABLE variable, FSMCOMPARISON comparison, double value) {
    this.mapFSMStateTransition.get(playerId).get(fromStateId)
                              .addTransition(toStateId, variable, comparison, 
                                             value);
  }

  //=========================================================================//
  // FSM Manager Swarm Logic methods                                         //
  //=========================================================================//
  @Override
  public SWARMALGORITHM getSwarmAlgorithm(int playerId){
    this.currentFSMStateId.put(playerId, this.mapFSMStateTransition.get(playerId)
            .get(this.currentFSMStateId.get(playerId))
            .getFSMStateId());

    return this.mapFSMStateTransition.get(playerId).get(this.currentFSMStateId.get(playerId))
                                     .getSwarmAlgorithm();
  }

  @Override
  public SWARMALGORITHM getStartingSwarmAlgorithm(int playerId){
    return SWARMALGORITHM.valueOf(this.currentFSMStateId.get(playerId));
  }

  @Override
  public double[] getSwarmAlgorithmWeights(int playerId) {
    return this.mapFSMStateTransition.get(playerId).get(this.currentFSMStateId.get(playerId))
                                     .getSwarmAlgorithmWeights();
  }

  //=========================================================================//
  // FSM Manager Swarm Algorithm Select methods                              //
  //=========================================================================//
  @Override
  public HashMap<Integer, FSMStateTransition> getMapFSMStateTransition(int playerId){
    return this.mapFSMStateTransition.get(playerId);
  }

  @Override
  public HashMap<Integer, FSMSTATE> getFSMStates(int playerId) {
    return mapFSMStates.get(playerId);
  }

  
  @Override
  public void setSwarmAlgorithm(int playerId, Integer stateId, SWARMALGORITHM
    swarmAlgorithm){
    this.mapFSMStateTransition.get(playerId).get(stateId).setSwarmAlgorithm(swarmAlgorithm);
  }

  @Override
  public void setSwarmAlgorithmWeights(int playerId, Integer stateId, double weight1,
    double weight2, double weight3){
    this.mapFSMStateTransition.get(playerId).get(stateId)
                              .setSwarmAlgorithmWeights(weight1, 
                                                        weight2, 
                                                        weight3);
  }
}