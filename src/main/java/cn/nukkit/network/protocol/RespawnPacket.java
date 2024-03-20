package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class RespawnPacket extends DataPacket {

    public float x;
    public float y;
    public float z;
    public State respawnState = State.SEARCHING_FOR_SPAWN;
    public long runtimeEntityId;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        Vector3f v = byteBuf.readVector3f();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.respawnState = State.values()[byteBuf.readByte()];
        this.runtimeEntityId = byteBuf.readEntityRuntimeId();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVector3f(this.x, this.y, this.z);
        byteBuf.writeByte((byte) respawnState.ordinal());
        byteBuf.writeEntityRuntimeId(runtimeEntityId);
    }

    @Override
    public int pid() {
        return ProtocolInfo.RESPAWN_PACKET;
    }

    public enum State {
        SEARCHING_FOR_SPAWN,
        READY_TO_SPAWN,
        CLIENT_READY_TO_SPAWN;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
