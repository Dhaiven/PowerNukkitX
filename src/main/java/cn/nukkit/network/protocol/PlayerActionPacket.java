package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;
import cn.nukkit.network.connection.util.HandleByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class PlayerActionPacket extends DataPacket {

    public long entityId;
    public Action action;
    public int x;
    public int y;
    public int z;
    public BlockVector3 resultPosition;
    public int face;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.entityId = byteBuf.readEntityRuntimeId();
        this.action = Action.form(byteBuf.readVarInt());
        BlockVector3 v = byteBuf.readBlockVector3();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.resultPosition = byteBuf.readBlockVector3();
        this.face = byteBuf.readVarInt();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeEntityRuntimeId(this.entityId);
        byteBuf.writeVarInt(this.action.getId());
        byteBuf.writeBlockVector3(this.x, this.y, this.z);
        byteBuf.writeBlockVector3(this.resultPosition != null ? this.resultPosition : new BlockVector3());
        byteBuf.writeVarInt(this.face);
    }

    @Override
    public int pid() {
        return ProtocolInfo.PLAYER_ACTION_PACKET;
    }

    @Getter
    public enum Action {
        START_BREAK(0),
        ABORT_BREAK(1),
        STOP_BREAK(2),
        GET_UPDATED_BLOCK(3),
        DROP_ITEM(4),
        START_SLEEPING(5),
        STOP_SLEEPING(6),
        RESPAWN(7),
        JUMP(8),
        START_SPRINT(9),
        STOP_SPRINT(10),
        START_SNEAK(11),
        STOP_SNEAK(12),
        CREATIVE_PLAYER_DESTROY_BLOCK(13),
        DIMENSION_CHANGE_ACK(14), //sent when spawning in a different dimension to tell the server we spawned
        START_GLIDE(15),
        STOP_GLIDE(16),
        BUILD_DENIED(17),
        CONTINUE_BREAK(18),
        SET_ENCHANTMENT_SEED(20),
        START_SWIMMING(21),
        STOP_SWIMMING(22),
        START_SPIN_ATTACK(23),
        STOP_SPIN_ATTACK(24),
        INTERACT_BLOCK(25),
        PREDICT_DESTROY_BLOCK(26),
        CONTINUE_DESTROY_BLOCK(27),
        START_ITEM_USE_ON(28),
        STOP_ITEM_USE_ON(29),

        START_FLYING(34),
        STOP_FLYING(35),
        RECEIVED_SERVER_DATA(36);

        private static final Int2ObjectMap<Action> BY_ID = new Int2ObjectOpenHashMap<>(31);

        static {
            for (Action action : values()) {
                BY_ID.put(action.id, action);
            }
        }

        private final int id;

        Action(int id) {
            this.id = id;
        }

        public static Action form(int id) {
            return BY_ID.get(id);
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
