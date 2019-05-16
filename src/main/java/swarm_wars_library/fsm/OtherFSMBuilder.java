package swarm_wars_library.fsm;

import swarm_wars_library.network.Constants;
import swarm_wars_library.network.Headers;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
*
* */

public class OtherFSMBuilder {

    private FSMManager fsmManager = FSMManager.getInstance();

    public OtherFSMBuilder() {

    }

    public void setOtherFSM(Map pack) {
        int otherPlayerId = (Integer) pack.get(Headers.PLAYER);
        Map states = (Map) pack.get(Headers.STATES);
        for (Object i : states.keySet()) {
            String j = (String) i;
            int k = (Integer) states.get(j);
            FSMSTATE fsmstate = FSMSTATE.values()[k];
            this.fsmManager.addFSMState(otherPlayerId, Integer.parseInt(j), fsmstate);
        }
        System.out.println(states.toString());

        Map swarmLogics = (Map) pack.get(Headers.SWARM_LOGIC);
        for (Object i : swarmLogics.keySet()) {
            String j = (String) i;
            int k = (Integer) swarmLogics.get(j);
            SWARMALGORITHM s = SWARMALGORITHM.values()[k];
            this.fsmManager.setSwarmAlgorithm(otherPlayerId, Integer.parseInt(j), s);
        }
        System.out.println(swarmLogics.toString());

        List transitions = (ArrayList) pack.get(Headers.TRANSITIONS);
        for (int i = 0; i < transitions.size(); i++) {
            Map transition = (Map) transitions.get(i);
            int from_state = (Integer) transition.get(String.valueOf(Constants.FROM_STATE));
            int to_state = (Integer) transition.get(String.valueOf(Constants.TO_STATE));
            FSMVARIABLE fsmvariable = FSMVARIABLE.values()[(Integer) transition.get(String.valueOf(Constants.FSMVARIABLE))];
            FSMCOMPARISON fsmcomparison = FSMCOMPARISON.values()[(Integer) transition.get(String.valueOf(Constants.FSMCOMPARISON))];
            int value = (Integer) transition.get(String.valueOf(Constants.VALUE));
            this.fsmManager.addTransition(otherPlayerId, from_state, to_state, fsmvariable, fsmcomparison, value);
        }
        System.out.println(transitions.toString());

    }


}
