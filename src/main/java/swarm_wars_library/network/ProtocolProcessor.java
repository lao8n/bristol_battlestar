package swarm_wars_library.network;

import org.json.JSONObject;
import swarm_wars_library.SwarmWars;
import swarm_wars_library.fsm.OtherFSMBuilder;
import swarm_wars_library.game_screens.GameOver;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Vector2D;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
            if (beforeStart(m)) return;
            if (whileSetup(m)) return;
            if (m.get(Headers.PLAYER) != null && (Integer)m.get(Headers.PLAYER) == map.getPlayerId()){
                return;
            }
            if (updateTurret(m)) return;
            if (updateHealthPack(m)) return;
            if (endGame(m)) return;
            createBuffer(m);
            saveToBuffer(m);
        }
    }

    private boolean beforeStart(Map m) {
        if (m.get(Headers.TYPE).equals(Constants.START)) {
            MessageHandlerMulti.gameStarted = true;
            if(!gotSeed) {
                int randSeed = (Integer) m.get(Headers.RANDOM_SEED);
                RandomGen.setSeed(randSeed);
                gotSeed = true;
            }
            return true;
        }
        return false;
    }

    private boolean whileSetup(Map m) {
        if ((Integer) m.get(Headers.TYPE) == Constants.SETUP) {
            OtherFSMBuilder otherFSMBuilder = new OtherFSMBuilder();
            otherFSMBuilder.setOtherFSM(m);
            List<Double> locations = (ArrayList) m.get(Headers.TURRETS);
            System.out.println("Initial turrets locations: " + locations);
            List<Double> hpLocations = (ArrayList) m.get(Headers.HEALTH_PACKS);
            System.out.println("Initial health locations: " + hpLocations);
            for(int i = 0; i < this.map.getNumTurrets(); i++){
                this.map.storeTurretLocation(i, 
                                             new Vector2D(locations.get(2*i), locations.get(2*i+1)));
            }
            for (int i = 0; i < this.map.getNumHealthPack(); i++) {
                this.map.storeHealthPackLocation(i, new Vector2D(hpLocations.get(2*i), hpLocations.get(2*i+1)));
            }
            return true;
        }
        return false;
    }

    private boolean updateTurret(Map m) {
        if (m.get(Headers.TYPE).equals(Constants.UPDATE_TURRET)) {
            System.out.println(m.get(Headers.TURRET_VERSION));
            System.out.println(m.get(Headers.TURRET_LOCATION));
            this.map.storeTurretLocation((Integer) m.get(Headers.TURRET_ID), 
                                            new Vector2D((Double) ((ArrayList)m.get(Headers.TURRET_LOCATION)).get(0),
                                            (Double) ((ArrayList) m.get(Headers.TURRET_LOCATION)).get(1)));
            this.map.storeTurretVersion((Integer) m.get(Headers.TURRET_ID), 
                                         (Integer) m.get(Headers.TURRET_VERSION));
            return true;
        }
        return false;
    }

    private boolean updateHealthPack(Map m) {
        if (m.get(Headers.TYPE).equals(Constants.UPDATE_HEALTHPACK)) {
            System.out.println(m.get(Headers.HEALTH_PACK_VERSION));
            System.out.println(m.get(Headers.HEALTH_PACK_LOCATION));
            this.map.storeHealthPackLocation((Integer) m.get(Headers.HEALTH_PACK_ID),
                                            new Vector2D((Double) ((ArrayList) m.get(Headers.HEALTH_PACK_LOCATION)).get(0),
                                                    (Double) ((ArrayList) m.get(Headers.HEALTH_PACK_LOCATION)).get(1)));
            this.map.storeHealthPackVersion((Integer) m.get(Headers.HEALTH_PACK_ID),
                                            (Integer) m.get(Headers.HEALTH_PACK_VERSION));
            return true;
        }
        return false;
    }

    private boolean endGame(Map m) {
        if(m.get(Headers.TYPE).equals(Constants.END)) {
            System.out.println("Received game ended....");
            this.map.setGameEnded(true);

            if(m.containsKey(Headers.WINNERID)){
                GameOver.getInstance().setWinningPlayer((Integer) m.get(Headers.WINNERID));
            } else { throw new Error("END package doesn't have winner Id"); }

            swarm_wars_library.map.Map.getInstance().setGameEnded(true);
            MessageHandlerMulti.gameStarted = false;
            return true;
        }
        return false;
    }

    private void createBuffer(Map m) {
        if (!MessageHandlerMulti.isBufferExist((Integer) m.get(Headers.PLAYER))) {
            try{
                MessageHandlerMulti.createNewBuffer((Integer) m.get(Headers.PLAYER));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void saveToBuffer(Map m) {
        MessageHandlerMulti.clientReceivePackage((Integer)m.get(Headers.PLAYER), m);
    }
}
