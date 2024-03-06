package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.UsingItem;
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
    public void decode() {
    }

    @Override
    public void encode() {
        this.reset();
        this.putLShort(itemId);
        this.putLInt(action.getId());
    }
}
