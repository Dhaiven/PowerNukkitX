package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.ToString;

/**
 * @since 15-10-15
 */
@ToString
public class InteractPacket extends DataPacket {

    public Action action;
    public long target;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.action = Action.from(byteBuf.readByte());
        this.target = byteBuf.readEntityRuntimeId();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte((byte) this.action.getId());
        byteBuf.writeEntityRuntimeId(this.target);
    }

    @Override
    public int pid() {
        return ProtocolInfo.INTERACT_PACKET;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }

    @Getter
    public enum Action {
        VEHICLE_EXIT(3),
        MOUSEOVER(4),
        OPEN_INVENTORY(6);

        private static final Int2ObjectMap<Action> BY_ID = new Int2ObjectOpenHashMap<>(2);

        static {
            for (Action type : values()) {
                BY_ID.put(type.id, type);
            }
        }

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public static Action from(int id) {
            return BY_ID.get(id);
        }
    }
}
