package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class MobArmorEquipmentPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.MOB_ARMOR_EQUIPMENT_PACKET;
    }

    public long eid;
    public Item[] slots = new Item[4];

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.eid = byteBuf.readEntityRuntimeId();
        this.slots = new Item[4];
        this.slots[0] = byteBuf.readSlot();
        this.slots[1] = byteBuf.readSlot();
        this.slots[2] = byteBuf.readSlot();
        this.slots[3] = byteBuf.readSlot();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeEntityRuntimeId(this.eid);
        byteBuf.writeSlot(this.slots[0]);
        byteBuf.writeSlot(this.slots[1]);
        byteBuf.writeSlot(this.slots[2]);
        byteBuf.writeSlot(this.slots[3]);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
