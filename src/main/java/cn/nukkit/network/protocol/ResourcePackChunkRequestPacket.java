package cn.nukkit.network.protocol;

import lombok.ToString;
import cn.nukkit.utils.version.Version;

import java.util.UUID;

@ToString

public class ResourcePackChunkRequestPacket extends AbstractResourcePackDataPacket {

    public UUID packId;
    private Version packVersion;
    public int chunkIndex;

    @Override
    public void decode() {
        decodePackInfo();
        this.chunkIndex = this.getLInt();
    }

    @Override
    public void encode() {
        this.reset();
        encodePackInfo();
        this.putLInt(this.chunkIndex);
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

    @Override
    public int pid() {
        return ProtocolInfo.RESOURCE_PACK_CHUNK_REQUEST_PACKET;
    }
}
