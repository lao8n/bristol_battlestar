package swarm_wars_library.network;

public class SendMessageHandler {

    private boolean running = true;

    public SendMessageHandler() {}

    public void sendMessage() throws Exception{
        while (running) {
            MessageHandlerMulti.sendpackage();
        }
    }

    public void sendMessage0() throws Exception{
        while (running) {
            MessageHandlerMulti.sendPackageClient();
        }
    }

    public void setStatus(boolean running) {
        this.running = running;
    }

}
