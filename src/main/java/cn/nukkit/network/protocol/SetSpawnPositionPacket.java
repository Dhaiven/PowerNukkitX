package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author Nukkit Project Team
 */
@ToString
public class SetSpawnPositionPacket extends DataPacket {

    public Type spawnType;
    public int y;
    public int z;
    public int x;

    public int dimension = 0;
    
    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVarInt(this.spawnType.ordinal());
        byteBuf.writeBlockVector3(this.x, this.y, this.z);
        byteBuf.writeVarInt(dimension);
        byteBuf.writeBlockVector3(this.x, this.y, this.z);
    }

    @Override
    public int pid() {
        return ProtocolInfo.SET_SPAWN_POSITION_PACKET;
    }

    public enum Type {
        PLAYER_SPAWN,
        WORLD_SPAWN;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
