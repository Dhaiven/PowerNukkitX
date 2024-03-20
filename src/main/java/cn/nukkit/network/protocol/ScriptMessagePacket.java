package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScriptMessagePacket extends DataPacket {
    private String channel;
    private String message;

    @Override
    public int pid() {
        return ProtocolInfo.SCRIPT_MESSAGE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.channel = byteBuf.readString();
        this.message = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeString(channel);
        byteBuf.writeString(message);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
