package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class DebugInfoPacket extends DataPacket {

    public long entityId;
    public String data;

    @Override
    public int pid() {
        return ProtocolInfo.DEBUG_INFO_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.entityId = byteBuf.readLong();
        this.data = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeLong(this.entityId);
        byteBuf.writeString(this.data);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
