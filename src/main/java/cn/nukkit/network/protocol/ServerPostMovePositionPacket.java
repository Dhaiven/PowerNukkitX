package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.connection.util.HandleByteBuf;

public class ServerPostMovePositionPacket extends DataPacket {

    public Vector3f position;

    @Override
    public int pid() {
        return ProtocolInfo.SERVER_POST_MOVE_POSITION;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.position = byteBuf.readVector3f();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeVector3f(this.position);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
