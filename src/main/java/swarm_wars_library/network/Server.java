package swarm_wars_library.network;

public class Server {

    public static void main(String[] args) throws Exception{
        if (args.length == 0) {
            Logger.getInstance().setLogging(false);
        }else if (args[0].equalsIgnoreCase("log")){
            Logger.getInstance().setLogging(true);
        }else {
            System.out.println("Incorrect parameters");
        }
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
