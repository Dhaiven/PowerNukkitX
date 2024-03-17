package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author xtypr
 * @since 2016/1/5
 */
@ToString
public class ChangeDimensionPacket extends DataPacket {

    public int dimension;

    public float x;
    public float y;
    public float z;

    public boolean respawn;

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {

        byteBuf.writeVarInt(this.dimension);
        byteBuf.writeVector3f(this.x, this.y, this.z);
        byteBuf.writeBoolean(this.respawn);
    }

    @Override
    public int pid() {
        return ProtocolInfo.CHANGE_DIMENSION_PACKET;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
