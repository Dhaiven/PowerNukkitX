package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

@ToString
public class CompletedUsingItemPacket extends DataPacket {

    public int itemId;
    public UsingItem.Action action;

    @Override
    public int pid() {
        return ProtocolInfo.COMPLETED_USING_ITEM_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeShortLE(itemId);
        byteBuf.writeIntLE(action);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
