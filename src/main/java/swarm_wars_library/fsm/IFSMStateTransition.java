package swarm_wars_library.fsm;

import java.util.ArrayList;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public interface IFSMStateTransition {

  //=========================================================================//
  // FSM State Transition methods                                            //
  //=========================================================================//
  public Integer getFSMStateId();
  public FSMSTATE getFSMState();
  public void addTransition(Integer toStateId, FSMVARIABLE variable, 
    FSMCOMPARISON fsmComparison, double value);
  public ArrayList<Integer> getTransitions();
  public SWARMALGORITHM getSwarmAlgorithm();
  public void setSwarmAlgorithm(SWARMALGORITHM swarmAlgorithm);
  public double[] getSwarmAlgorithmWeights();
  public void setSwarmAlgorithmWeights(double weight1, double weight2, 
    double weight3);
}