package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class SetTimePacket extends DataPacket {

    public int time;

    @Override
    public int pid() {
        return ProtocolInfo.SET_TIME_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeVarInt(this.time);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
