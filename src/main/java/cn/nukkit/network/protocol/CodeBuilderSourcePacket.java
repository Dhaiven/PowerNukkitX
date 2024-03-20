package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import cn.nukkit.network.protocol.types.CodeBuilderCategoryType;
import cn.nukkit.network.protocol.types.CodeBuilderOperationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeBuilderSourcePacket extends DataPacket {
    private CodeBuilderOperationType operation;
    private CodeBuilderCategoryType category;
    private String value;

    @Override
    public int pid() {
        return ProtocolInfo.CODE_BUILDER_SOURCE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.operation = CodeBuilderOperationType.values()[byteBuf.readByte()];
        this.category = CodeBuilderCategoryType.values()[byteBuf.readByte()];
        this.value = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte((byte) operation.ordinal());
        byteBuf.writeByte((byte) category.ordinal());
        byteBuf.writeString(value);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
