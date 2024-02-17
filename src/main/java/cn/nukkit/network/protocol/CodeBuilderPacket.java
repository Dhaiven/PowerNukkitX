package cn.nukkit.network.protocol;

import lombok.ToString;


@ToString
public class CodeBuilderPacket extends DataPacket {
    public boolean isOpening;
    public String url = "";

    @Override
    public int pid() {
        return ProtocolInfo.CODE_BUILDER_PACKET;
    }

    @Override
    public void decode() {
        this.url = this.getString();
        this.isOpening = this.getBoolean();
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(url);
        this.putBoolean(isOpening);
    }
}
