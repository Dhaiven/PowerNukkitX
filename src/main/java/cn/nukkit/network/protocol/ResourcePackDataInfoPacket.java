package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;
import cn.nukkit.utils.version.Version;

import java.util.UUID;

@ToString(exclude = "sha256")
public class ResourcePackDataInfoPacket extends AbstractResourcePackDataPacket {

    public UUID packId;
    private Version packVersion;
    public int maxChunkSize;
    public int chunkCount;
    public long compressedPackSize;
    public byte[] sha256;
    public boolean premium;
    public Type type = Type.RESOURCE;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        decodePackInfo(byteBuf);
        this.maxChunkSize = byteBuf.readIntLE();
        this.chunkCount = byteBuf.readIntLE();
        this.compressedPackSize = byteBuf.readLongLE();
        this.sha256 = byteBuf.readByteArray();
        this.premium = byteBuf.readBoolean();
        this.type = Type.values()[byteBuf.readByte()];
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        encodePackInfo(byteBuf);
        byteBuf.writeIntLE(this.maxChunkSize);
        byteBuf.writeIntLE(this.chunkCount);
        byteBuf.writeLongLE(this.compressedPackSize);
        byteBuf.writeByteArray(this.sha256);
        byteBuf.writeBoolean(this.premium);
        byteBuf.writeByte((byte) this.type.ordinal());
    }

    @Override
    public int pid() {
        return ProtocolInfo.RESOURCE_PACK_DATA_INFO_PACKET;
    }

    @Override
    public Version getPackVersion() {
        return packVersion;
    }

    @Override
    public void setPackVersion(Version packVersion) {
        this.packVersion = packVersion;
    }

    @Override
    public UUID getPackId() {
        return packId;
    }

    @Override
    public void setPackId(UUID packId) {
        this.packId = packId;
    }

    public enum Type {
       INVALID,
       ADDON,
       CACHED,
       COPY_PROTECTED,
       BEHAVIOR,
       PERSONA_PIECE,
       RESOURCE,
       SKINS,
       WORLD_TEMPLATE,
       COUNT;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
