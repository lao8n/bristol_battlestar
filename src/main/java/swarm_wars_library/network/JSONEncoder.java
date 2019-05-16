package swarm_wars_library.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class JSONEncoder extends MessageToByteEncoder<GameProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GameProtocol msg, ByteBuf out){

        out.writeInt(msg.getContentLength());

        out.writeBytes(msg.getContent());

        out.writeBytes("\n".getBytes());

    }

}
