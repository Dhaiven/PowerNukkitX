package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;

/**
 * @author CreeperFace
 * @since 30. 10. 2016
 */
@ToString
public class BossEventPacket extends DataPacket {

    public long bossEid;
    public Type type;
    public long playerEid;
    public float healthPercent;
    public String title = "";
    public short unknown;
    public int color;
    public int overlay;
    
    @Override
    public int pid() {
        return ProtocolInfo.BOSS_EVENT_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.bossEid = byteBuf.readEntityUniqueId();
        this.type = Type.values()[byteBuf.readUnsignedVarInt()];
        switch (this.type) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
            case QUERY:
                this.playerEid = byteBuf.readEntityUniqueId();
            case SHOW:
                this.title = byteBuf.readString();
                this.healthPercent = byteBuf.readFloatLE();
            case UPDATE_PROPERTIES:
                this.unknown = (short) byteBuf.readShort();
            case TEXTURE:
                this.color = (int) byteBuf.readUnsignedVarInt();
                this.overlay = (int) byteBuf.readUnsignedVarInt();
            case HEALTH_PERCENT:
                this.healthPercent = byteBuf.readFloatLE();
            case TITLE:
                this.title = byteBuf.readString();
            case HIDE:
        }
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        byteBuf.writeEntityUniqueId(this.bossEid);
        byteBuf.writeUnsignedVarInt(this.type.ordinal());
        switch (this.type) {
            case REGISTER_PLAYER:
            case UNREGISTER_PLAYER:
            case QUERY:
                byteBuf.writeEntityUniqueId(this.playerEid);
            case SHOW:
                byteBuf.writeString(this.title);
                byteBuf.writeFloatLE(this.healthPercent);
            case UPDATE_PROPERTIES:
                byteBuf.writeShort(this.unknown);
            case TEXTURE:
                byteBuf.writeUnsignedVarInt(this.color);
                byteBuf.writeUnsignedVarInt(this.overlay);
            case HEALTH_PERCENT:
                byteBuf.writeFloatLE(this.healthPercent);
            case TITLE:
                byteBuf.writeString(this.title);
            case HIDE:
        }
    }

    public enum Type {
        /* S2C: Shows the bossbar to the player. */
        SHOW,
        /* C2S: Registers a player to a boss fight. */
        REGISTER_PLAYER,
        /* S2C: Removes the bossbar from the client. */
        HIDE,
        /* C2S: Unregisters a player from a boss fight. */
        UNREGISTER_PLAYER,
        /* S2C: Sets the bar percentage. */
        HEALTH_PERCENT,
        /* S2C: Sets title of the bar. */
        TITLE,
        /* S2C: Update a player's bossbar properties. */
        UPDATE_PROPERTIES,
        /* S2C: Sets color and overlay of the bar. */
        TEXTURE,
        /* S2C: Get a player's bossbar information. TODO: 2022/2/9 implement query packet. */
        QUERY;
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
