package swarm_wars_library.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class GameClient {

    private static final int port = 10000;

    private static Scanner scanner = new Scanner(System.in);

    private static Logger logger = Logger.getInstance();

//    private static String ip = "35.246.75.108";

    private static String ip = "127.0.0.1";
    
    protected static final int bossGroupSize = Runtime.getRuntime().availableProcessors();

    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void run() throws Exception {
        getIP();
        logger.log("Starting client");
        EventLoopGroup workerGroup = new NioEventLoopGroup(bossGroupSize);
        final SendMessageHandler service = new SendMessageHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel c) throws Exception {
                            ChannelPipeline cp = c.pipeline();
                            cp.addLast(new JSONEncoder());
                            cp.addLast(new LineBasedFrameDecoder(1024));
                            cp.addLast(new JSONDecoder());
                            cp.addLast(new ClientHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            countDownLatch.countDown();
            new Thread(new Runnable() {
                public void run(){
                    try {
                        logger.log("Message sending thread starts", "Client");
                        service.sendMessage0();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            service.setStatus(false);
        }
    }

    public static void getIP(){
        // System.out.println("Please enter server IP address");
        // ip = scanner.nextLine();
        System.out.println("Connecting to " + ip);
    }

    public static void main(String[] args) throws Exception{
        run();
    }

}
