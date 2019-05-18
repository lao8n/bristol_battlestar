package swarm_wars_library.network;

public class Server {

    public static void main(String[] args) throws Exception{
        new Thread(new Runnable() {
            public void run() {
                try{
                    GameServer.run();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
