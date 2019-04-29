package swarm_wars_library.comms;

import java.util.HashMap;
import java.util.Map;

/*
    CommsGlobal is a collection of CommsChannels that allows shared information across all the Entities
    There are different groups, i.e CommsChannels. so that there is a PLAYER comms or ENEMY comms
 */
public class CommsGlobal {
    private static HashMap<String, CommsChannel> commsMap = new HashMap<String, CommsChannel>();

    // channelName is Tag - i.e player or envObjects
    public static void add(String channelName, CommsChannel commsChannel) {
        if(commsMap.containsKey(channelName)) throw new Error("Channel already exists");
        commsMap.put(channelName, commsChannel);
    }

    // returns LIVE CommsChannel
    public static CommsChannel get(String channelName) {
        if(!commsMap.containsKey(channelName)) throw new Error("Channel doesn't exist");
        return commsMap.get(channelName);
    }

    public static void update() {
        for(Map.Entry<String, CommsChannel> entry: commsMap.entrySet()){
            entry.getValue().update();
        }
    }
}
