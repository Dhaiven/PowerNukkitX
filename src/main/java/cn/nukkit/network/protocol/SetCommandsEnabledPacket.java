package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class SetCommandsEnabledPacket extends DataPacket {

    public boolean enabled;

    @Override
    public int pid() {
        return ProtocolInfo.SET_COMMANDS_ENABLED_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeBoolean(this.enabled);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
