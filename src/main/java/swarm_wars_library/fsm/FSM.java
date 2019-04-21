package swarm_wars_library.fsm;
import java.util.*;

public class FSM {

    private ArrayList<State> stateList;
    private ArrayList<Transition> transitionList;
    private int numStates;
    Transition transition;

    public FSM(int numStates) {
      this.numStates = numStates;
      stateList = new ArrayList<State>();
      for (int i = 0; i < numStates; i++) {
        stateList.add(State.ORBIT);
      }
      transitionList = new ArrayList<Transition>();
      for (int i = 0; i < this.numStates; i++) {
        transitionList.add(new Transition());
      }
    }

    public void setState(int location, State state) {
      stateList.set(location, state);
    }

    public State getState(int location) {
      if (location >= stateList.size()) {
        location = location % stateList.size();
      }
      State state = stateList.get(location);
      return state;
    }

    //transition related functions
    public Transition getTransition(int location) {
      return transitionList.get(location);
    }
}
