package swarm_wars_library.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

public class GameServer {

    private static final int port = 10000;

    private static final String IP = "0.0.0.0";

    private static Logger logger = Logger.getInstance();

    protected static final int bossGroupSize = Runtime.getRuntime().availableProcessors();

    protected static final int workerGroupSize = 4;

    public static boolean started = false;

    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void run() throws Exception {
        logger.log("Server Starting", "Server");
        LobbyManager lobbyManager = LobbyManager.getLobbyManager();
        lobbyManager.init();
        logger.log("Lobby manager initialized successfully", "Server");
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossGroupSize);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerGroupSize);
        final SendMessageHandler service = new SendMessageHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel c) throws Exception {
                            ChannelPipeline cp = c.pipeline();
                            cp.addLast(new JSONEncoder());
                            cp.addLast(new LineBasedFrameDecoder(1024));
                            cp.addLast(new JSONDecoder());
                            cp.addLast(new ProtocolProcessHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = serverBootstrap.bind(IP, port).sync();
            started = true;
            countDownLatch.countDown();
            new Thread(new Runnable() {
                public void run(){
                    try {
                        logger.log("Message sending thread starts", "Server");
                        System.out.println("****************");
                        service.sendMessage();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            service.setStatus(false);
        }
    }

    public static void main(String[] args) throws Exception{
        run();
    }
}
