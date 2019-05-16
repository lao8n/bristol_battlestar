package swarm_wars_library.network;

import java.net.InetSocketAddress;

/**
 * Created by ComingWind on 2019/2/17.
 */
public class Player {

    private String id;

    private InetSocketAddress inetSocketAddress;

    public Player(String id, InetSocketAddress inetSocketAddress) {
        this.id = id;
        this.inetSocketAddress = inetSocketAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }
}
