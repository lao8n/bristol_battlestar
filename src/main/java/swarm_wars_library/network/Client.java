package swarm_wars_library.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception{
        System.out.println("Enter your id");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        new Thread(new Runnable() {
            public void run() {
                try {
                    GameClient.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        GameClient.countDownLatch.await();
        System.out.println("Start sending messages");
        Thread.sleep(5000);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put(Headers.TYPE, Constants.SETUP);
        m.put(Headers.PLAYER, id);
        MessageHandlerMulti.putPackage(m);
        while(true){
            m = new HashMap<String, Object>();
            m.put(Headers.TYPE, Constants.OPERATION);
            m.put(Headers.PLAYER, id);
            m.put(Headers.W, 0);
            m.put(Headers.A, 1);
            m.put(Headers.FRAME, 10);
            MessageHandlerMulti.putPackage(m);
            Thread.sleep(3000);
        }
    }

}
