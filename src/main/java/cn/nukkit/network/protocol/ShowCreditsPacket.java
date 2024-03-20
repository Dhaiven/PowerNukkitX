package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class ShowCreditsPacket extends DataPacket {

    public long eid;
    public Status status;

    @Override
    public int pid() {
        return ProtocolInfo.SHOW_CREDITS_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.eid = byteBuf.readEntityRuntimeId();
        this.status = Status.values()[byteBuf.readVarInt()];
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeEntityRuntimeId(this.eid);
        byteBuf.writeVarInt(this.status.ordinal());
    }

    public enum Status {
        START,
        END;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
