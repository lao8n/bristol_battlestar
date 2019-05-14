package swarm_wars_library.fsm;

import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

import java.util.Map;

/*
*
* */

public class OtherFSMBuilder {
/*
    private FSMManager fsmManager = FSMManager.getInstance();

    private int otherPlayerId;

    public OtherFSMBuilder() {

    }

    public void setOtherFSM(Map pack) {
        otherPlayerId = pack.get();
        this.fsmManager.getMapFSMStateTransition().get(1).fsmState()
        this.fsmManager.addFSMState(otherPlayerId, 1, FSMSTATE.DEFEND);
        this.fsmManager.addFSMState(otherPlayerId, 2, FSMSTATE.SCOUT);
        // this.fsmManager.addFSMState(3, FSMSTATE.ATTACK);
        // this.fsmManager.addFSMState(3, FSMSTATE.DEFEND);
        // this.fsmManager.addFSMState(5, FSMSTATE.SCOUT);

        // Then add transitions after
        this.fsmManager.addTransition(otherPlayerId, 1,
                2,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.GREATERTHAN,
                500);
        this.fsmManager.addTransition(otherPlayerId, 2,
                1,
                FSMVARIABLE.ENEMYDISTANCE,
                FSMCOMPARISON.LESSTHAN,
                300);


        this.fsmManager.setSwarmAlgorithm(otherPlayerId, 1,SWARMALGORITHM.SCOUTBEE);
        this.fsmManager.setSwarmAlgorithm(otherPlayerId, 2, SWARMALGORITHM.ATTACK);
    }

*/

}
