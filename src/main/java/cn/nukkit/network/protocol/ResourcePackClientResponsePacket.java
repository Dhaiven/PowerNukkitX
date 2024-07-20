package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

import java.util.UUID;

@ToString
public class ResourcePackClientResponsePacket extends DataPacket {

    public Status responseStatus;
    public Entry[] packEntries;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.responseStatus = Status.values()[byteBuf.readByte() - 1];
        this.packEntries = new Entry[byteBuf.readShortLE()];
        for (int i = 0; i < this.packEntries.length; i++) {
            String[] entry = byteBuf.readString().split("_");
            this.packEntries[i] = new Entry(UUID.fromString(entry[0]), entry[1]);
        }
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte(this.responseStatus.ordinal() + 1);
        byteBuf.writeShortLE(this.packEntries.length);
        for (Entry entry : this.packEntries) {
            byteBuf.writeString(entry.uuid.toString() + '_' + entry.version);
        }
    }

    @Override
    public int pid() {
        return ProtocolInfo.RESOURCE_PACK_CLIENT_RESPONSE_PACKET;
    }

    public record Entry (UUID uuid, String version) {

    }

    public enum Status {
        REFUSED,
        SEND_PACKS,
        HAVE_ALL_PACKS,
        COMPLETED
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
