package cn.nukkit.network.protocol;

import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString(exclude = "data")
public class LevelChunkPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.FULL_CHUNK_DATA_PACKET;
    }

    public int chunkX;
    public int chunkZ;
    public int subChunkCount;
    public boolean cacheEnabled;
    public boolean requestSubChunks;
    public int subChunkLimit;
    public long[] blobIds;
    public byte[] data;
    /**
     * @since v649
     */
    public int dimension;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.chunkX);
        this.putVarInt(this.chunkZ);
        this.putVarInt(this.dimension);
        if (!this.requestSubChunks) {
            this.putUnsignedVarInt(this.subChunkCount);
        } else if (this.subChunkLimit < 0) {
            this.putUnsignedVarInt(-1);
        } else {
            this.putUnsignedVarInt(-2);
            this.putUnsignedVarInt(this.subChunkLimit);
        }
        this.putBoolean(cacheEnabled);
        if (this.cacheEnabled) {
            this.putUnsignedVarInt(blobIds.length);

            for (long blobId : blobIds) {
                this.putLLong(blobId);
            }
        }
        this.putByteArray(this.data);
    }
}
