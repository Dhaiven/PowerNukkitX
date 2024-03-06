package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
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
    public void decode() {
        this.eid = this.getEntityRuntimeId();
        //this.slots = new Item[4];
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] = this.getSlot();
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(this.eid);
        for (int i = 0; i < slots.length; i++) {
            this.putSlot(this.slots[i]);
        }
    }
}
