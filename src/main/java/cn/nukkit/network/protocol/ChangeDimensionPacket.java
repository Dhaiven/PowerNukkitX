package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @author xtypr
 * @since 2016/1/5
 */
@ToString
public class ChangeDimensionPacket extends DataPacket {

    public int dimension;

    public float x;
    public float y;
    public float z;

    public boolean respawn;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.dimension);
        this.putVector3f(this.x, this.y, this.z);
        this.putBoolean(this.respawn);
    }

    @Override
    public int pid() {
        return ProtocolInfo.CHANGE_DIMENSION_PACKET;
    }
}
