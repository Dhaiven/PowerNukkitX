package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class RequestChunkRadiusPacket extends DataPacket {

    public int radius;

    /**
     * @since v582
     */
    private int maxRadius;

    @Override
    public void decode() {
        this.radius = this.getVarInt();
        this.maxRadius = getByte();
    }

    @Override
    public void encode() {

    }

    @Override
    public int pid() {
        return ProtocolInfo.REQUEST_CHUNK_RADIUS_PACKET;
    }
}
