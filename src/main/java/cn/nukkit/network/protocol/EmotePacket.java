package cn.nukkit.network.protocol;

import lombok.ToString;

@ToString
public class EmotePacket extends DataPacket {


    public long runtimeId;

    public String xuid = "";

    public String platformId = "";

    public String emoteID;

    public byte flags;

    @Override
    public int pid() {
        return ProtocolInfo.EMOTE_PACKET;
    }

    @Override
    public void decode() {
        this.runtimeId = this.getEntityRuntimeId();
        this.emoteID = this.getString();
        this.xuid = this.getString();
        this.platformId = this.getString();
        this.flags = (byte) this.getByte();
    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(this.runtimeId);
        this.putString(this.emoteID);
        this.putString(this.xuid);
        this.putString(this.platformId);
        this.putByte(flags);
    }
}
