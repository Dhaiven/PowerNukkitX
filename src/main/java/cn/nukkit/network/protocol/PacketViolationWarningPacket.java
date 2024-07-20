package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class PacketViolationWarningPacket extends DataPacket {

    public Type type;
    public Severity severity;
    public int packetId;
    public String context;

    @Override
    public int pid() {
        return ProtocolInfo.PACKET_VIOLATION_WARNING_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.type = Type.values()[byteBuf.readVarInt() + 1];
        this.severity = Severity.values()[byteBuf.readVarInt()];
        this.packetId = byteBuf.readVarInt();
        this.context = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVarInt(this.type.ordinal() - 1);
        byteBuf.writeVarInt(this.severity.ordinal());
        byteBuf.writeVarInt(this.packetId);
        byteBuf.writeString(this.context);
    }

    public enum Type {
        UNKNOWN,
        MALFORMED_PACKET
    }

    public enum Severity {
        UNKNOWN,
        WARNING,
        FINAL_WARNING,
        TERMINATING_CONNECTION
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
