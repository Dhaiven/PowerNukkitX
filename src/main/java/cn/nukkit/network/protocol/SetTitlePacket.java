package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tee7even
 */
@Getter
@Setter
@ToString
public class SetTitlePacket extends DataPacket {

    private static final TitleAction[] TITLE_ACTIONS = TitleAction.values();

    public Type type;
    public @NotNull String text = "";
    public int fadeInTime = 0;
    public int stayTime = 0;
    public int fadeOutTime = 0;
    public String xuid = "";
    public String platformOnlineId = "";

    @Override
    public int pid() {
        return ProtocolInfo.SET_TITLE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.type = Type.values()[byteBuf.readVarInt()];
        this.text = byteBuf.readString();
        this.fadeInTime = byteBuf.readVarInt();
        this.stayTime = byteBuf.readVarInt();
        this.fadeOutTime = byteBuf.readVarInt();
        this.xuid = byteBuf.readString();
        this.platformOnlineId = byteBuf.readString();
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeVarInt(type.ordinal());
        byteBuf.writeString(text);
        byteBuf.writeVarInt(fadeInTime);
        byteBuf.writeVarInt(stayTime);
        byteBuf.writeVarInt(fadeOutTime);
        byteBuf.writeString(xuid);
        byteBuf.writeString(platformOnlineId);
    }

    @NotNull public TitleAction getTitleAction() {
        int currentType = this.type.ordinal();
        return TITLE_ACTIONS[currentType];
    }

    public void setTitleAction(@NotNull TitleAction type) {
        this.type = Type.values()[type.ordinal()];
    }

    public enum Type {
        CLEAR,
        RESET,
        TITLE,
        SUBTITLE,
        ACTION_BAR,
        ANIMATION_TIMES,
        TITLE_JSON,
        SUBTITLE_JSON,
        ACTIONBAR_JSON;
    }

    public enum TitleAction {
        CLEAR,
        RESET,
        SET_TITLE_MESSAGE,
        SET_SUBTITLE_MESSAGE,
        SET_ACTION_BAR_MESSAGE,
        SET_ANIMATION_TIMES,
        SET_TITLE_JSON,
        SET_SUBTITLE_JSON,
        SET_ACTIONBAR_JSON,
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
