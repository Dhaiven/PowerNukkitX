package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @since 15-10-13
 */
@ToString
public class PlayStatusPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.PLAY_STATUS_PACKET;
    }

    public Status status;

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeInt(this.status.ordinal());
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
