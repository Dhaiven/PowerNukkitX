package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Nukkit Project Team
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public class CraftingEventPacket extends DataPacket {

    public int windowId;
    public Type type;
    public UUID id;

    public Item[] input;
    public Item[] output;

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.windowId = byteBuf.readByte();
        this.type = Type.values()[byteBuf.readVarInt()];
        this.id = byteBuf.readUUID();

        this.input = byteBuf.readArray(Item.class, HandleByteBuf::readSlot);
        this.output = byteBuf.readArray(Item.class, HandleByteBuf::readSlot);
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte((byte) (windowId & 0xFF));
        byteBuf.writeVarInt(type.ordinal());
        byteBuf.writeUUID(id);

        byteBuf.writeArray(input, byteBuf::writeSlot);
        byteBuf.writeArray(output, byteBuf::writeSlot);
    }

    @Override
    public int pid() {
        return ProtocolInfo.CRAFTING_EVENT_PACKET;
    }

    public enum Type {
        INVENTORY,
        CRAFTING,
        WORKBENCH;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
