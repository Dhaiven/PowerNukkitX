package cn.nukkit.network.protocol;

import lombok.Getter;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class MobEffectPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.MOB_EFFECT_PACKET;
    }

    public long eid;
    public Event eventId;
    public int effectId;
    public int amplifier = 0;
    public boolean particles = true;
    public int duration = 0;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(this.eid);
        this.putByte((byte) this.eventId.getId());
        this.putVarInt(this.effectId);
        this.putVarInt(this.amplifier);
        this.putBoolean(this.particles);
        this.putVarInt(this.duration);
    }

    @Getter
    public enum Event {
        ADD(1),
        MODIFY(2),
        REMOVE(3);

        private final int id;

        Event(int id) {
            this.id = id;
        }
    }
}
