package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.registry.Registries;
import lombok.ToString;

@ToString
public class BiomeDefinitionListPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.BIOME_DEFINITION_LIST_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeBytes(Registries.BIOME.getBiomeDefinitionListPacketData());
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
