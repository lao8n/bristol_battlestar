package swarm_wars_library.network;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Sender {

    public void send(String id, String moves){
        String message = "{a:" + id + ",b:" + moves + "}";
        JSONObject jsonObject = new JSONObject(message);
        Map<String, Object> m = jsonObject.toMap();
        MessageHandler.putOneClient(m);
    }

    public static void main(String[] args){
        Sender s = new Sender();
        s.send("1", "w");
    }

}
