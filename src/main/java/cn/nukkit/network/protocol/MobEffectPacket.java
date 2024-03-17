package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
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
    /**
     * @since v662
     */
    public long tick;

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeEntityRuntimeId(this.eid);
        byteBuf.writeByte((byte) this.eventId);
        byteBuf.writeVarInt(this.effectId);
        byteBuf.writeVarInt(this.amplifier);
        byteBuf.writeBoolean(this.particles);
        byteBuf.writeVarInt(this.duration);
        byteBuf.writeLongLE(this.tick);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
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
