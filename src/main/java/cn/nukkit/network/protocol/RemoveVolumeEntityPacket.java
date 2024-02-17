package cn.nukkit.network.protocol;


public class RemoveVolumeEntityPacket extends DataPacket {

    private long id;
    /**
     * @since v503
     */
    private int dimension;


    public RemoveVolumeEntityPacket() {
        // Does nothing
    }

    @Override
    public int pid() {
        return ProtocolInfo.REMOVE_VOLUME_ENTITY_PACKET;
    }

    @Override
    public void decode() {
        id = getUnsignedVarInt();
    }

    @Override
    public void encode() {
        reset();
        putUnsignedVarInt(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
