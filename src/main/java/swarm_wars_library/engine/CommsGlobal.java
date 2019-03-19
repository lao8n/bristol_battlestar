package swarm_wars_library.engine;

public class CommsGlobal {
    private HashMap<String, CommsChannel> commsMap;

    CommsGlobal(){

    }

    // channelName is Tag - i.e player or envObjects
    void add(String channelName, CommsChannel commsChannel) {
        if(commsMap.containsKey(channelName)) throw new Error("Channel already exists");
        commsMap.put(channelName, commsChannel);
    }

    CommsChannel get(String channelName) {
        if(!commsMap.containsKey(channelName)) throw new Error("Channel doesn't exist");
        return commsMap.get(channelName);
    }
}
