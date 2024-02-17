package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class HurtArmorPacket extends DataPacket {


    public int cause;


    public int damage;


    public long armorSlots;

    @Override
    public void decode() {
        this.cause = getVarInt();
        this.damage = getVarInt();
        this.armorSlots = getUnsignedVarLong();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.cause);
        this.putVarInt(this.damage);
        this.putUnsignedVarLong(this.armorSlots);
    }

    @Override
    public int pid() {
        return ProtocolInfo.HURT_ARMOR_PACKET;;
    }
}
