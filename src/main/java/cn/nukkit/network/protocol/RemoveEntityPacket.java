package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class RemoveEntityPacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.REMOVE_ENTITY_PACKET;

    public long eid;

    @Override
    public int pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {

        byteBuf.writeEntityUniqueId(this.eid);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
