package swarm_wars_library.fsm;

import java.util.HashMap;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public interface IFSMManagerAlgorithmSelect {

  //=========================================================================//
  // FSM Manager Swarm Algorithm Select methods                              //
  //=========================================================================//
  public HashMap<Integer, FSMStateTransition> getMapFSMStateTransition(int playerId);
  public HashMap getFSMStates(int playerId);
  public void setSwarmAlgorithm(int playerId, Integer stateId,
    SWARMALGORITHM swarmAlgorithm);
  public void setSwarmAlgorithmWeights(int playerId, Integer stateId, double weight1,
    double weight2, double weight3);
}