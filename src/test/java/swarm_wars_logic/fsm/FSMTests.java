package swarm_wars_logic.fsm;

import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.beans.Transient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import swarm_wars_library.fsm.FSM;
import swarm_wars_library.fsm.Transition;
import swarm_wars_library.fsm.State;


class FSMTests {

  @Test
  @DisplayName("FSM Set state test")
  void SetStateTest() {
    FSM fsm = new FSM(4);
    assertEquals(fsm.getState(0), State.ORBIT);
    fsm.setState(1, State.ATTACK);
    assertEquals(fsm.getState(1), State.ATTACK);
  }

  @Test
  @DisplayName("FSM Get state test")
  void GetStateTest() {
    FSM fsm = new FSM(4);
    assertEquals(fsm.getState(1), State.ORBIT);
    assertEquals(fsm.getState(5), State.ORBIT);
  }


}
