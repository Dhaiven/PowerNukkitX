package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import io.netty.util.internal.EmptyArrays;
import lombok.ToString;

/**
 * @since 15-10-13
 */
@ToString
public class TextPacket extends DataPacket {

    @Override
    public int pid() {
        return ProtocolInfo.TEXT_PACKET;
    }

    public Type type;
    public String source = "";
    public String message = "";
    public String[] parameters = EmptyArrays.EMPTY_STRINGS;
    public boolean isLocalized = false;
    public String xboxUserId = "";
    public String platformChatId = "";

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.type = Type.values()[byteBuf.readByte()];
        this.isLocalized = byteBuf.readBoolean() || type == Type.TRANSLATION;
        switch (type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                this.source = byteBuf.readString();
            case RAW:
            case TIP:
            case SYSTEM:
            case OBJECT:
            case OBJECT_WHISPER:
                this.message = byteBuf.readString();
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                this.message = byteBuf.readString();
                this.parameters = byteBuf.readArray(String.class, HandleByteBuf::readString);
        }
        this.xboxUserId = byteBuf.readString();
        this.platformChatId = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeByte(this.type.ordinal());
        byteBuf.writeBoolean(this.isLocalized || type == Type.TRANSLATION);
        switch (this.type) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                byteBuf.writeString(this.source);
            case RAW:
            case TIP:
            case SYSTEM:
            case OBJECT:
            case OBJECT_WHISPER:
                byteBuf.writeString(this.message);
            case TRANSLATION:
            case POPUP:
            case JUKEBOX_POPUP:
                byteBuf.writeString(this.message);
                byteBuf.writeUnsignedVarInt(this.parameters.length);
                for (String parameter : this.parameters) {
                    byteBuf.writeString(parameter);
                }
        }
        byteBuf.writeString(this.xboxUserId);
        byteBuf.writeString(this.platformChatId);
    }

    public enum Type {
        RAW,
        CHAT,
        TRANSLATION,
        POPUP,
        JUKEBOX_POPUP,
        TIP,
        SYSTEM,
        WHISPER,
        ANNOUNCEMENT,
        OBJECT,
        OBJECT_WHISPER;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
