package swarm_wars_library.network;

import swarm_wars_library.entities.PlayerN;
import swarm_wars_library.input.Input;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NetworkClientFunctions {

    private static int interval = 1000/60;
    private static int sleepInterval = 0;
    private static TerminalLogger tlogger = TerminalLogger.getInstance();

    public static void startNewtork() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    GameClient.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            GameClient.countDownLatch.await();
            Thread.sleep(sleepInterval);
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }

    public static int getPlayerIdFromUser() {
        System.out.println("Enter your id");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void cleanBuffer() {
        MessageHandlerMulti.refreshClientReceiveBuffer();
    }

    public static void sendConnect(int id) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put(Headers.TYPE, Constants.CONNECT);
        m.put(Headers.PLAYER, id);
        MessageHandlerMulti.putPackage(m);
        System.out.println("Sent CONNECT");
        try{Thread.sleep(interval);}
        catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }

    public static void sendSetup(int id) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put(Headers.TYPE, Constants.SETUP);
        m.put(Headers.PLAYER, id);
        MessageHandlerMulti.putPackage(m);
        System.out.println("Sent SETUP");
        try{Thread.sleep(interval);}
        catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }

    public static void sendStart(int id) {
        if(id == 1){
            System.out.println("Try to send START");
            while (!MessageHandlerMulti.gameStarted) {
                Map<String, Object> m = new HashMap();
                m.put(Headers.TYPE, Constants.START);
                MessageHandlerMulti.putPackage(m);
                try{Thread.sleep(3000);}
                catch (Exception e) {
                    System.out.println("FAILED");
                    e.printStackTrace();
                }
            }
            System.out.println("Sent START");
        }
    }

    public static void awaitStart() {
        System.out.println("Game not started yet");
        while (!MessageHandlerMulti.gameStarted) {
            try{Thread.sleep(interval);}
            catch (Exception e) {
                 System.out.println("FAILED");
                e.printStackTrace();
            }
        }
        System.out.println("Game started");
    }


    public static void sendOperation(int id, int frame, Map<String, Object> m) {
        MessageHandlerMulti.putPackage(m);
        // System.out.println("Sent OPERATION - Player:" + id + " Frame:" + frame);
    }

    public static Map getPackage(int id, int frame) {
        Map<String, Object> rev = null;
        int getId = id;
        while (rev == null) {
            rev = MessageHandlerMulti.getPackage(getId, frame);

            if(swarm_wars_library.map.Map.getInstance().gameEnded()){
                System.out.println("Game over but waiting...");
                return null;
            }

            try{Thread.sleep(interval);}
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // System.out.println("Get OPERATION - Player " + id + " try to get player:" + getId + " frame:" + frame);
        return rev;
    }

    public void checkGameEnd(int id) {

    }

    public static void sendEnd(int id, int winnerId) {
        Map m = new HashMap<String, Object>();
        m.put(Headers.TYPE, Constants.END);
        m.put(Headers.PLAYER, id);
        m.put(Headers.WINNERID, winnerId);
        MessageHandlerMulti.putPackage(m);
        System.out.println("Player " + id + " Game ends");
    }

    public static void threadSleep(){
        try{Thread.sleep(interval);}
        catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
        }
    }
}
