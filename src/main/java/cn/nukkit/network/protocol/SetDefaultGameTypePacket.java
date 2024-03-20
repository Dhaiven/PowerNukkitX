package cn.nukkit.network.protocol;

import org.jetbrains.annotations.Range;

import cn.nukkit.network.connection.util.HandleByteBuf;

public class SetDefaultGameTypePacket extends DataPacket {

    public @Range(from=0, to=3) int gamemode;

    @Override
    public int pid() {
        return ProtocolInfo.SET_DEFAULT_GAME_TYPE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.gamemode = byteBuf.readUnsignedVarInt();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeUnsignedVarInt(this.gamemode);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
