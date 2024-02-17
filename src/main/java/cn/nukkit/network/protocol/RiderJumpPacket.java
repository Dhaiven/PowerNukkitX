package cn.nukkit.network.protocol;

import lombok.ToString;

@ToString
public class RiderJumpPacket extends DataPacket {

    /**
     * This is jumpStrength.
     * 对应跳跃进度条0-100
     * <p>
     * Corresponds to jump progress bars 0-100
     */
    public int unknown;

    @Override
    public int pid() {
        return ProtocolInfo.RIDER_JUMP_PACKET;
    }

    @Override
    public void decode() {
        this.unknown = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.unknown);
    }
}
