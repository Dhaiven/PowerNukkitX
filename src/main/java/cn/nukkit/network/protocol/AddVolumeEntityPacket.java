package cn.nukkit.network.protocol;

import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.connection.util.HandleByteBuf;


public class AddVolumeEntityPacket extends DataPacket {

    public int id;
    public CompoundTag data;
    /**
     * @since v465
     */
    public String engineVersion;
    /**
     * @since v485
     */
    public String identifier;
    /**
     * @since v485
     */
    public String instanceName;

    public AddVolumeEntityPacket() {
        // Does nothing
    }

    @Override
    public int pid() {
        return ProtocolInfo.ADD_VOLUME_ENTITY_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        id = byteBuf.readUnsignedVarInt();
        data = byteBuf.readTag();
        engineVersion = byteBuf.readString();
        identifier = byteBuf.readString();
        instanceName = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt((int) Integer.toUnsignedLong(id));
        byteBuf.writeTag(data);
        byteBuf.writeString(engineVersion);
        byteBuf.writeString(identifier);
        byteBuf.writeString(instanceName);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
