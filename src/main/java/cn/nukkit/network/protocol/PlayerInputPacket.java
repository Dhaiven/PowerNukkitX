package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class PlayerInputPacket extends DataPacket {

    public float motionX;
    public float motionY;

    public boolean jumping;
    public boolean sneaking;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.motionX = byteBuf.readFloatLE();
        this.motionY = byteBuf.readFloatLE();
        this.jumping = byteBuf.readBoolean();
        this.sneaking = byteBuf.readBoolean();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {

    }

    @Override
    public int pid() {
        return ProtocolInfo.PLAYER_INPUT_PACKET;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
