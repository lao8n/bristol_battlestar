package swarm_wars_library.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class JSONDecoder extends ByteToMessageDecoder {

    public final int BASE_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= BASE_LENGTH) {
            int length = in.readInt();
            byte[] data = new byte[length];
            if (in.readableBytes() < length){
                return;
            }
            in.readBytes(data);
            GameProtocol gameProtocol = new GameProtocol(length, data);
            out.add(gameProtocol);
        }
    }
}
