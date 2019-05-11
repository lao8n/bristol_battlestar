package swarm_wars_library.fsm;

public interface IFSMManagerUI {

  //=========================================================================//
  // FSM Manager UI methods                                                  //
  //=========================================================================//
  public void addFSMState(int stateId, FSMSTATE fsmState);
  public void addTransition(int fromStateId, int toStateId, 
    FSMVARIABLE variable, FSMCOMPARISON comparison, double value);
}