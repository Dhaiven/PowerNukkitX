package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class ShowProfilePacket extends DataPacket {

    public String xuid;

    @Override
    public int pid() {
        return ProtocolInfo.SHOW_PROFILE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.xuid = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeString(this.xuid);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
