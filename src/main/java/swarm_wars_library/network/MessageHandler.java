package swarm_wars_library.network;

import java.util.*;

/**
 * Created by ComingWind on 2019/2/19.
 */
public class MessageHandler {

    /**
     * For server sending buffer
     */
    private static Queue<Map<String, Object>> out = new LinkedList<Map<String, Object>>();

    /**
     * For server receiving buffer
     */
    private static Queue<Map<String, Object>> in = new LinkedList<Map<String, Object>>();

    /**
     * For client sending buffer
     */
    private static Queue<Map<String, Object>> outClient = new LinkedList<Map<String, Object>>();

    /**
     * For client receiving buffer
     */
    private static Queue<Map<String, Object>> inClient = new LinkedList<Map<String, Object>>();

    public static Queue getOutInstance() {
        return out;
    }

    public static Queue getInInstance() {
        return in;
    }

    public static Queue getOutInstanceClient() {
        return outClient;
    }

    public static Queue getInInstanceClient() {
        return inClient;
    }

    /**
     * Server
     * @return
     */
    public static synchronized Map<String, Object> getOneAndRemove() {
        // If has element in queue, return it
        if (out.size() != 0) {
            return out.poll();
        }
        return null;
    }

    public static synchronized Map<String, Object> getOne() {
        if (out.size() != 0){
            return out.peek();
        }
        return null;
    }

    public static synchronized void removeOne() {
        if (out.size() != 0){
            out.poll();
        }
    }

    public static synchronized void putOne(Map<String, Object> m) {
        out.offer(m);
    }

    /**
     * Server
     * @param
     */
    public static synchronized Map<String, Object> getOneAndRemove0() {
        if (in.size() != 0){
            return in.poll();
        }
        return null;
    }

    public static synchronized Map<String, Object> getOne0() {
        if (in.size() != 0){
            return in.peek();
        }
        return null;
    }

    public static synchronized void putOne0(Map<String, Object> m){
        in.offer(m);
    }

    public static synchronized void removeOne0() {
        if (in.size() != 0){
            in.poll();
        }
    }

    /**
     * Client
     * @return
     */
    public static synchronized Map<String, Object> getOneAndRemoveClient() {
        // If has element in queue, return it
        if (outClient.size() != 0) {
            return outClient.poll();
        }
        return null;
    }

    public static synchronized Map<String, Object> getOneClient() {
        if (outClient.size() != 0){
            return outClient.peek();
        }
        return null;
    }

    public static synchronized void removeOneClient() {
        if (outClient.size() != 0){
            outClient.poll();
        }
    }

    public static synchronized void putOneClient(Map<String, Object> m) {
        outClient.offer(m);
    }

    /**
     * Client
     * @param
     */
    public static synchronized Map<String, Object> getOneAndRemoveClient0() {
        if (inClient.size() != 0){
            return inClient.poll();
        }
        return null;
    }

    public static synchronized Map<String, Object> getOneClient0() {
        if (inClient.size() != 0){
            return inClient.peek();
        }
        return null;
    }

    public static synchronized void putOneClient0(Map<String, Object> m){
        inClient.offer(m);
    }

    public static synchronized void removeOneClient0() {
        if (inClient.size() != 0){
            inClient.poll();
        }
    }

}
