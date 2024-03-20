package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class SetHealthPacket extends DataPacket {

    public int health;

    @Override
    public int pid() {
        return ProtocolInfo.SET_HEALTH_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(this.health);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
