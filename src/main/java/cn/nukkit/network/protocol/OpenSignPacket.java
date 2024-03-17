package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class OpenSignPacket extends DataPacket {
    private BlockVector3 position;
    private boolean frontSide;

    @Override
    public int pid() {
        return ProtocolInfo.OPEN_SIGN;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.position = byteBuf.readBlockVector3();
        this.frontSide = byteBuf.readBoolean();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeBlockVector3(position);
        byteBuf.writeBoolean(frontSide);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
