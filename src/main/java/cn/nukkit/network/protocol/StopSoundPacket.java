package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class StopSoundPacket extends DataPacket {

    public String name;
    public boolean stopAll;

    @Override
    public int pid() {
        return ProtocolInfo.STOP_SOUND_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeString(this.name);
        byteBuf.writeBoolean(this.stopAll);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
