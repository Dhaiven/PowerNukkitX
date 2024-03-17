package cn.nukkit.network.protocol;

import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.network.connection.util.HandleByteBuf;


public class DeathInfoPacket extends DataPacket {

    public TranslationContainer translation;

    @Override
    public int pid() {
        return ProtocolInfo.DEATH_INFO_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        //empty
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeString(translation.getText());
        byteBuf.writeArray(translation.getParameters(), byteBuf::writeString);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
