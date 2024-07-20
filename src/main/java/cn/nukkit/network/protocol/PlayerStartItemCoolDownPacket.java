package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerStartItemCoolDownPacket extends DataPacket {

    private String itemCategory;
    private int coolDownDuration;

    @Override
    public int pid() {
        return ProtocolInfo.PLAYER_START_ITEM_COOL_DOWN_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.itemCategory = byteBuf.readString();
        this.coolDownDuration = byteBuf.readVarInt();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeString(itemCategory);
        byteBuf.writeVarInt(coolDownDuration);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
