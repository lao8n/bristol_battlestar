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
      for (int i = 0; i < numStates; i++) {
        transitionList.add(new Transition());
      }
    }

    public void setState(int position, State state) {
      stateList.set(position, state);
    }

    public State getState(int position) {
      if (position >= stateList.size()) {
        position = position % stateList.size();
      }
      State state = stateList.get(position);
      return state;
    }

    //transition related functions
    public Transition getTransition(int position) {
      return transitionList.get(position);
    }
}
