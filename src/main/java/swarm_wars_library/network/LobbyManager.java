package swarm_wars_library.network;

import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class LobbyManager {

    private static Map<String, Channel> connectionManager;

    private static Map<String, Player> playerManager;

    private static LobbyManager lobbyManager = new LobbyManager();

    private static Channel channelToServer;

    public static synchronized LobbyManager getLobbyManager(){
        return lobbyManager;
    }

    private LobbyManager(){

    }

    public void init(){
        connectionManager = new HashMap<String, Channel>();
        playerManager = new HashMap<String, Player>();
        System.out.println("Initialized successfully");
    }

    public synchronized void addChannel(String sessionId, Channel socketChannel){
        if (socketChannel != null){
            connectionManager.put(sessionId, socketChannel);
        }
    }

    public Channel getChannel(String sessionId){
        return connectionManager.get(sessionId);
    }

    public synchronized void removeChannel(String sessionId){
        connectionManager.remove(sessionId);
    }

    public synchronized void addPlayer(String playerId, Player player){
        playerManager.put(playerId, player);
    }

    public Player getPlayer(String playerId){
        return playerManager.get(playerId);
    }

    public synchronized void removePlayer(String playerId){
        playerManager.remove(playerId);
    }

    public Map<String, Channel> getConnectionManager() {
        return connectionManager;
    }

    public static Channel getChannelToServer() {
        return channelToServer;
    }

    public static void setChannelToServer(Channel channelToServer) {
        LobbyManager.channelToServer = channelToServer;
    }
}
