package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author GoodLucky777
 */


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

    public long getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public long getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
