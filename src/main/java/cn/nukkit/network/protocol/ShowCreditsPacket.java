package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class ShowCreditsPacket extends DataPacket {

    public static final int STATUS_START_CREDITS = 0;
    public static final int STATUS_END_CREDITS = 1;

    public long eid;
    public int status;

    @Override
    public int pid() {
        return ProtocolInfo.SHOW_CREDITS_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.eid = byteBuf.readEntityRuntimeId();
        this.status = byteBuf.readVarInt();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeEntityRuntimeId(this.eid);
        byteBuf.writeVarInt(this.status);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
