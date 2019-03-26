package swarm_wars_library.network;

import io.netty.channel.Channel;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MessageHandlerMulti{

    private static Queue<Map<String, Object>> serverBuffer =
            new LinkedList<Map<String, Object>>();

    private static Queue<Map<String, Object>> clientSendBuffer = new LinkedList<Map<String, Object>>();

    private static Map<Integer, Queue<Map<String, Object>>> clientReceiveBuffer =
            new HashMap<Integer, Queue<Map<String, Object>>>();

    public static synchronized Map<String, Object> getPackage(int playerNumber, int frame){
        Queue<Map<String, Object>> q = clientReceiveBuffer.get(playerNumber);
        Map<String, Object> tmp = null;
        while(q.size() != 0) {
            tmp = q.peek();
            int frameNow = (Integer) tmp.get(Headers.FRAME);
            if (frameNow < frame){
                q.poll();
            }else if (frameNow == frame){
                q.poll();
                break;
            }else if (frameNow > frame) {
                tmp = null;
                break;
            }
        }
        return tmp;
    }

    public static boolean isBufferExist(int playerNumber) {
        return clientReceiveBuffer.get(playerNumber) != null;
    }

    public static synchronized void clientReceivePackage(int playerNumber, Map<String, Object> m) {
        Queue<Map<String, Object>> q = clientReceiveBuffer.get(playerNumber);
        q.offer(m);
    }

    public static synchronized void createNewBuffer(int playerNumber) throws Exception{
        if (clientReceiveBuffer.get(playerNumber) != null){
            throw new Exception("Already exists player");
        }
        // Create a new queue
        Queue<Map<String, Object>> q = new LinkedList<Map<String, Object>>();
        clientReceiveBuffer.put(playerNumber, q);
        System.out.println("New player buffer created, player ID:" + playerNumber);
    }

    public static synchronized void putPackage(Map<String, Object> pack){
        clientSendBuffer.offer(pack);
    }

    public static synchronized void sendpackage() throws InterruptedException{
        if (serverBuffer.size() == 0) {
            // System.out.println("No message in sending buffer" + i++);
            // TODO: 决定发送频率
            Thread.sleep(Constants.ServerSleep);
            return;
        }
        JSONObject j = new JSONObject(serverBuffer.poll());
        GameProtocol g = new GameProtocol(j.toString().length(), j.toString().getBytes());
        for (Channel channel : LobbyManager.getLobbyManager().getConnectionManager().values()) {
            // Synchronized way, if one player lagged, everyone will be blocked
            channel.writeAndFlush(g);
        }
        Thread.sleep(Constants.ServerSleep);
    }

    public static synchronized void refreshClientReceiveBuffer() {
        clientReceiveBuffer =
                new HashMap<Integer, Queue<Map<String, Object>>>();
    }

    public static synchronized void serverReceivePackage(Map<String, Object> pack) {
        serverBuffer.offer(pack);
    }

    public static synchronized void sendPackageClient() throws InterruptedException{
        if (clientSendBuffer.size() == 0){
            // System.out.println("No message in sending buffer" + i++);
            // TODO: 确定发送频率
            Thread.sleep(Constants.ClientSleep);
            return;
        }
        JSONObject j = new JSONObject(clientSendBuffer.poll());
        GameProtocol g = new GameProtocol(j.toString().length(), j.toString().getBytes());
        LobbyManager.getChannelToServer().writeAndFlush(g);
        Thread.sleep(Constants.ClientSleep);
    }

}
