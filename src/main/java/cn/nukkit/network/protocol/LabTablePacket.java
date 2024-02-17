package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;
import cn.nukkit.network.protocol.types.LabTableReactionType;
import cn.nukkit.network.protocol.types.LabTableType;
import lombok.ToString;


@ToString
public class LabTablePacket extends DataPacket {
    
    public LabTableType actionType;
    public BlockVector3 blockPosition;
    public LabTableReactionType reactionType;

    @Override
    public int pid() {
        return ProtocolInfo.LAB_TABLE_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putByte((byte) actionType.ordinal());
        this.putBlockVector3(blockPosition);
        this.putByte((byte) reactionType.ordinal());
    }
}
