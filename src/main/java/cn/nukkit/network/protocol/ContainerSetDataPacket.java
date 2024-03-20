package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class ContainerSetDataPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.CONTAINER_SET_DATA_PACKET;
    }

    public int windowId;
    public Property property;
    public int value;

    @Override
    public void decode(HandleByteBuf byteBuf) {
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte((byte) this.windowId);
        byteBuf.writeVarInt(this.property.getId());
        byteBuf.writeVarInt(this.value);
    }

    @Getter
    public enum Property {
        FURNACE_TICK_COUNT(0),
        FURNACE_LIT_TIME(1),
        FURNACE_LIT_DURATION(2),
        //TODO: check property 3
        FURNACE_FUEL_AUX(4),

        BREWING_STAND_BREW_TIME(0),
        BREWING_STAND_FUEL_AMOUNT(1),
        BREWING_STAND_FUEL_TOTAL(2);

        private final int id;
    
        Property(int id) {
            this.id = id;
        }
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
