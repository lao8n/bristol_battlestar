package swarm_wars_library.network;

import io.netty.channel.Channel;
import org.json.JSONObject;
import swarm_wars_library.map.RandomGen;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.*;

public class MessageHandlerMulti{

    private static TerminalLogger tlogger = TerminalLogger.getInstance();

    private static Queue<Map<String, Object>> serverBuffer =
            new LinkedList<Map<String, Object>>();

    private static Queue<Map<String, Object>> clientSendBuffer = new LinkedList<Map<String, Object>>();

    private static volatile Map<Integer, Queue<Map<String, Object>>> clientReceiveBuffer =
            new HashMap<Integer, Queue<Map<String, Object>>>();

    private static Map<Integer, Map<String, Object>> setupBuffer = new HashMap<Integer, Map<String, Object>>();

    private static volatile Map<Integer, Integer> Frames = new HashMap();

    public static volatile boolean gameStarted = false;

    private static volatile int readyPlayers = 0;

    public static Map<String, Object> getPackage(int playerNumber, int frame){
        Queue<Map<String, Object>> q = clientReceiveBuffer.get(playerNumber);
        Map<String, Object> tmp = null;
        while(q != null && q.size() != 0) {
            tmp = q.peek();
            tlogger.log("Player Number: " + playerNumber + " , Frame Number: " + tmp.get(Headers.FRAME));
            int frameNow = (Integer) tmp.get(Headers.FRAME);
            if (frameNow < frame){
                tlogger.log("Current package frame is less than wanted");
                q.poll();
            }else if (frameNow == frame){
                tlogger.log("Successfully got one frame package, frame: " + tmp.get(Headers.FRAME));
                tmp = q.poll();
                break;
            }else if (frameNow > frame) {
                tlogger.log("No package found");
                break;
            }
        }
        return tmp;
    }

    public static boolean isBufferExist(int playerNumber) {
        return clientReceiveBuffer.get(playerNumber) != null;
    }

    public static void clientReceivePackage(int playerNumber, Map<String, Object> m) {
        // TODO: About starting
        tlogger.log("Received player: " + m.get(Headers.PLAYER) + " frame: " + m.get(Headers.FRAME));
        clientReceiveBuffer.get(playerNumber).offer(m);
    }

    public static synchronized void createNewBuffer(int playerNumber) throws Exception{
        if (clientReceiveBuffer.get(playerNumber) != null){
            throw new Exception("Already exists player");
        }
        // Create a new queue
        Queue<Map<String, Object>> q = new LinkedList<Map<String, Object>>();
        clientReceiveBuffer.put(playerNumber, q);
        tlogger.log("New player buffer created, player ID:" + playerNumber);
    }

    public static synchronized void putPackage(Map<String, Object> pack){
        clientSendBuffer.offer(pack);
    }

    public static synchronized void sendpackage() throws InterruptedException{
        if (serverBuffer.size() == 0) {
            // tlogger.log("No message in sending buffer");
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
        try {
            Logger.getInstance().log("Sent package successfully", "Server");
        }catch (IOException e){
            e.printStackTrace();
        }
        Thread.sleep(Constants.ServerSleep);
    }

    public static void refreshClientReceiveBuffer() {
        clientReceiveBuffer =
                new HashMap<Integer, Queue<Map<String, Object>>>();
        tlogger.log("Refreshed client receiving buffer");
    }

    public static synchronized void serverReceivePackage(Map<String, Object> pack){
        // TODO: Add logic
        // If the package is START, then the frame counter starts
        switch ((Integer) pack.get(Headers.TYPE)) {
            case Constants.OPERATION:
                tlogger.log("Case: OPERATION");
                // Add a new header frame
                pack.put(Headers.FRAME, Frames.get((Integer)pack.get(Headers.PLAYER)));
                int frame = Frames.get((Integer)pack.get(Headers.PLAYER));
                Frames.put((Integer)pack.get(Headers.PLAYER), ++frame);
                serverBuffer.offer(pack);
                break;
            case Constants.SETUP:
                // Receiving all setup packages means all ready, game starts
                tlogger.log("Case: SETUP");
                pack.put(Headers.FRAME, 0);
                setupBuffer.put((Integer) pack.get(Headers.PLAYER), pack);
                readyPlayers++;
                break;
            case Constants.START:
                tlogger.log("Case: START");
                if (readyPlayers == Frames.size() && Frames.size() > 1){
                    for (Map<String, Object> m : setupBuffer.values()) {
                        serverBuffer.offer(m);
                    }
                    pack.put(Headers.RANDOM_SEED, (int)(RandomGen.getRand()*Integer.MAX_VALUE));
                    serverBuffer.offer(pack);
                    setupBuffer = new HashMap<Integer, Map<String, Object>>();
                    try {
                        Logger.getInstance().log("All game starts", "Server");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case Constants.CONNECT:
                tlogger.log("Case: CONNECT");
                Frames.put((Integer)pack.get(Headers.PLAYER), 1);
                break;
            case Constants.END:
                tlogger.log("Case: END");
                Frames.remove((Integer)pack.get(Headers.PLAYER));
                clientReceiveBuffer.remove((Integer)pack.get(Headers.PLAYER));
                readyPlayers--;
                break;
        }

    }

    public static synchronized void sendPackageClient() throws InterruptedException{
        if (clientSendBuffer.size() == 0){
//            tlogger.log("No message in sending buffer");
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
