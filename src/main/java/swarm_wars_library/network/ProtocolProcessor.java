package swarm_wars_library.network;

import org.json.JSONObject;
import swarm_wars_library.SwarmWars;
import swarm_wars_library.fsm.OtherFSMBuilder;
import swarm_wars_library.map.RandomGen;

import java.util.Map;

public class ProtocolProcessor {

    swarm_wars_library.map.Map map;

    private boolean gotSeed = false;

    private final static ProtocolProcessor processor = new ProtocolProcessor();

    public static ProtocolProcessor getProcessorInstance() {
        return processor;
    }

    private ProtocolProcessor() {
        map = swarm_wars_library.map.Map.getInstance();
    }

    public void process(GameProtocol msg) {
        synchronized (processor){
            // 1. Get Content
            String content = new String(msg.getContent());
            // 2. Convert content to JSON object
            JSONObject j = new JSONObject(content);
            // 3. Add to a static map for game to get information
            MessageHandlerMulti.serverReceivePackage(j.toMap());
        }
    }

    public void process0(GameProtocol msg) {
        synchronized (processor){
            String content = new String(msg.getContent());
            JSONObject j = new JSONObject(content);
            Map m = j.toMap();
            if (m.get(Headers.TYPE).equals(Constants.START)) {
                MessageHandlerMulti.gameStarted = true;

                if(!gotSeed) {
                    int randSeed = (Integer) m.get(Headers.RANDOM_SEED);
                    RandomGen.setSeed(randSeed);
                    gotSeed = true;
                }

                return;
            }
            if ((Integer) m.get(Headers.TYPE) == Constants.SETUP) {
                OtherFSMBuilder otherFSMBuilder = new OtherFSMBuilder();
                otherFSMBuilder.setOtherFSM(m);
                return;
            }
            // TODO: Only for test
            if (m.get(Headers.PLAYER) != null && (Integer)m.get(Headers.PLAYER) == map.getPlayerId()){
                return;
            }
            // TODO ends here

            if(m.get(Headers.TYPE).equals(Constants.END)) {
                System.out.println("Received game ended....");
                swarm_wars_library.map.Map.getInstance().setGameEnded(true);
                return;
            }

            if (!MessageHandlerMulti.isBufferExist((Integer) m.get(Headers.PLAYER))) {
                try{
                    MessageHandlerMulti.createNewBuffer((Integer) m.get(Headers.PLAYER));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            MessageHandlerMulti.clientReceivePackage((Integer)j.toMap().get(Headers.PLAYER), j.toMap());
        }
    }
}
