package cn.nukkit.network.protocol;

import lombok.ToString;

@ToString
public class TransferPacket extends DataPacket {

    public String address; // Server address
    public int port = 19132; // Server port

    @Override
    public void decode() {
        this.address = this.getString();
        this.port = (short) this.getLShort();
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(address);
        this.putLShort(port);
    }

    @Override
    public int pid() {
        return ProtocolInfo.TRANSFER_PACKET;
    }
}
