package swarm_wars_library.fsm;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public interface IFSMManagerSwarmLogic {

  //=========================================================================//
  // FSM Manager Swarm Logic methods                                         //
  //=========================================================================//
  public SWARMALGORITHM getSwarmAlgorithm();
  public double[] getSwarmAlgorithmWeights();
  public SWARMALGORITHM getStartingSwarmAlgorithm();
}