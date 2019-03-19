package swarm_wars_library.engine;

import java.util.HashMap;

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

    public CommsChannel get(String channelName) {
        if(!commsMap.containsKey(channelName)) throw new Error("Channel doesn't exist");
        return commsMap.get(channelName);
    }
}
