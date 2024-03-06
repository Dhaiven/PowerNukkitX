package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @since 15-10-13
 */
@ToString
public class PlayStatusPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.PLAY_STATUS_PACKET;
    }

    public Status status;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putInt(this.status.ordinal());
    }
}
