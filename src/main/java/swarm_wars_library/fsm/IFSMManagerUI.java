package swarm_wars_library.fsm;

public interface IFSMManagerUI {

  //=========================================================================//
  // FSM Manager UI methods                                                  //
  //=========================================================================//
  public void addFSMState(int playerId, int stateId, FSMSTATE fsmState);
  public void addTransition(int playerId, int fromStateId, int toStateId,
    FSMVARIABLE variable, FSMCOMPARISON comparison, double value);
}