package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.BlockSyncType;
import lombok.ToString;


@ToString
public class UpdateBlockSyncedPacket extends UpdateBlockPacket {

    public long actorUniqueId;
    public BlockSyncType updateType;

    @Override
    public int pid() {
        return ProtocolInfo.UPDATE_BLOCK_SYNCED_PACKET;
    }

    @Override
    public void encode() {
        super.encode();
        this.putUnsignedVarLong(actorUniqueId);
        this.putUnsignedVarLong(updateType.ordinal());
    }
}
