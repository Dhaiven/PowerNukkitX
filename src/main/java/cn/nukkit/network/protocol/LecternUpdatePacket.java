package cn.nukkit.network.protocol;

import cn.nukkit.math.BlockVector3;
import lombok.ToString;

@ToString
public class LecternUpdatePacket extends DataPacket {

    public int page;
    public int totalPages;
    public BlockVector3 blockPosition;
    public boolean dropBook;

    @Override
    public int pid() {
        return ProtocolInfo.LECTERN_UPDATE_PACKET;
    }

    @Override
    public void decode() {
        this.page = this.getByte();
        this.totalPages = this.getByte();
        this.blockPosition = this.getBlockVector3();
        this.dropBook = this.getBoolean();
    }

    @Override
    public void encode() {
    }
}
