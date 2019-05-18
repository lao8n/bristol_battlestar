package swarm_wars_library.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.log("Connection established", "Client");
        LobbyManager.setChannelToServer(ctx.channel());
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        // TODO: add logging
        // 1. Convert message to GameProtocol object
        GameProtocol g = (GameProtocol) msg;
        //logger.log("In Protocol Process Handler, message is: " + g.toString(), "Client");
        // 2. Process message
        ProtocolProcessor processor = ProtocolProcessor.getProcessorInstance();
        processor.process0(g);
        //logger.log("Successfully stored in buffer","Client");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        System.out.println(cause.getCause().getMessage());
        ctx.close();
    }

}
