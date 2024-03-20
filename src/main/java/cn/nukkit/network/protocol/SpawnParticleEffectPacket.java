package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

import java.util.Optional;

@ToString
public class SpawnParticleEffectPacket extends DataPacket {

    public int dimensionId;
    public long uniqueEntityId = -1;
    public Vector3f position;
    public String identifier;
    /**
     * @since v503
     */
    public Optional<String> molangVariablesJson = Optional.empty();

    @Override
    public int pid() {
        return ProtocolInfo.SPAWN_PARTICLE_EFFECT_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte((byte) this.dimensionId);
        byteBuf.writeEntityUniqueId(uniqueEntityId);
        byteBuf.writeVector3f(this.position);
        byteBuf.writeString(this.identifier);
        byteBuf.writeBoolean(molangVariablesJson.isPresent());
        molangVariablesJson.ifPresent(byteBuf::writeString);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
