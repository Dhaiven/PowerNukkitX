package cn.nukkit.network.protocol;

import cn.nukkit.Server;
import cn.nukkit.entity.data.Skin;
import lombok.ToString;

import java.util.UUID;

/**
 * @author Nukkit Project Team
 */
@ToString
public class PlayerListPacket extends DataPacket {

    public static final byte TYPE_ADD = 0;
    public static final byte TYPE_REMOVE = 1;

    public byte type;
    public Entry[] entries = Entry.EMPTY_ARRAY;

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(this.type);
        this.putUnsignedVarInt(this.entries.length);

        if (this.type == TYPE_ADD) {
            for (Entry entry : this.entries) {
                this.putUUID(entry.uuid);

                this.putVarLong(entry.entityId);
                this.putString(entry.name);
                this.putString(entry.xboxUserId);
                this.putString(entry.platformChatId);
                this.putLInt(entry.buildPlatform);
                this.putSkin(entry.skin);
                this.putBoolean(entry.isTeacher);
                this.putBoolean(entry.isHost);
                this.putBoolean(entry.subClient);
            }

            for (Entry entry : this.entries) {
                this.putBoolean(entry.trustedSkin || Server.getInstance().isForceSkinTrusted());
            }
        } else {
            for (Entry entry : this.entries) {
                this.putUUID(entry.uuid);
            }
        }
    }

    @Override
    public int pid() {
        return ProtocolInfo.PLAYER_LIST_PACKET;
    }

    @ToString
    public static class Entry {
        public static final Entry[] EMPTY_ARRAY = new Entry[0];

        public final UUID uuid;
        public long entityId = 0;
        public String name = "";
        public String xboxUserId = ""; //TODO
        public String platformChatId = ""; //TODO
        public int buildPlatform = -1;
        public Skin skin;
        public boolean isTeacher;
        public boolean isHost;
        private boolean subClient;

        public boolean trustedSkin;

        public Entry(UUID uuid) {
            this.uuid = uuid;
        }

        public Entry(UUID uuid, long entityId, String name, Skin skin) {
            this(uuid, entityId, name, skin, "");
        }

        public Entry(UUID uuid, long entityId, String name, Skin skin, String xboxUserId) {
            this.uuid = uuid;
            this.entityId = entityId;
            this.name = name;
            this.skin = skin;
            this.trustedSkin = skin.isTrusted();
            this.xboxUserId = xboxUserId == null ? "" : xboxUserId;
        }
    }

}
