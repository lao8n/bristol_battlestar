package swarm_wars_library.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProtocolProcessHandler extends ChannelInboundHandlerAdapter{

    private Logger logger = Logger.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        // 1. Convert message to GameProtocol object
        GameProtocol g = (GameProtocol) msg;
        logger.log("In Protocol Process Handler, message is: " + g.toString(), "Server");
        // 2. Process message
        ProtocolProcessor processor = ProtocolProcessor.getProcessorInstance();
        processor.process(g);
        logger.log("Successfully stored in buffer","Server");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LobbyManager lobbyManager = LobbyManager.getLobbyManager();
        lobbyManager.addChannel(ctx.channel().id().asLongText(), ctx.channel());
        logger.log("Remote connection came in, ip: " + ctx.channel().remoteAddress().toString(), "Server");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LobbyManager lobbyManager = LobbyManager.getLobbyManager();
        lobbyManager.removeChannel(ctx.channel().id().asLongText());
        logger.log("Remote connection disconnected, ip: " + ctx.channel().remoteAddress().toString(), "Server");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        MessageHandlerMulti.Frames = new HashMap<>();
        MessageHandlerMulti.clientReceiveBuffer = new HashMap<>();
        MessageHandlerMulti.readyPlayers = 0;
        Map<String, Object> pack = new HashMap<>();
        pack.put(Headers.TYPE, Constants.END);
        MessageHandlerMulti.serverBuffer.offer(pack);
        ctx.close();
    }

}
