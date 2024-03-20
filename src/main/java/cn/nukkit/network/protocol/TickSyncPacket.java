package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author GoodLucky777
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(onConstructor = @__())
public class TickSyncPacket extends DataPacket {
    
    private long requestTimestamp;
    private long responseTimestamp;
    
    @Override
    public int pid() {
        return ProtocolInfo.TICK_SYNC_PACKET;
    }
    
    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.requestTimestamp = byteBuf.readLongLE();
        this.responseTimestamp = byteBuf.readLongLE();
    }
    
    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeLongLE(this.requestTimestamp);
        byteBuf.writeLongLE(this.responseTimestamp);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
