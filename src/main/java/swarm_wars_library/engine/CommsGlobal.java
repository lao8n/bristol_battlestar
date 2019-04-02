package swarm_wars_library.engine;

import java.util.HashMap;
import java.util.Map;

/*
    CommsGlobal is a collection of CommsChannels that allows shared information across all the Entities
    There are different groups, i.e CommsChannels. so that there is a PLAYER comms or ENEMY comms
 */
public class CommsGlobal {
    private HashMap<String, CommsChannel> commsMap;

    public CommsGlobal(){
        commsMap = new HashMap<String, CommsChannel>();
    }

    // channelName is Tag - i.e player or envObjects
    public void add(String channelName, CommsChannel commsChannel) {
        if(commsMap.containsKey(channelName)) throw new Error("Channel already exists");
        commsMap.put(channelName, commsChannel);
    }

    // returns LIVE CommsChannel
    public CommsChannel get(String channelName) {
        if(!commsMap.containsKey(channelName)) throw new Error("Channel " + channelName + " doesn't exist");
        return commsMap.get(channelName);
    }

    public void update() {
        for(Map.Entry<String, CommsChannel> entry: commsMap.entrySet()){
            entry.getValue().update();
        }
    }
}
