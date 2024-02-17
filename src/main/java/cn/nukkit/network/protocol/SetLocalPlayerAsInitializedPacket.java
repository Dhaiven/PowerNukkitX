package cn.nukkit.network.protocol;

import lombok.ToString;

@ToString
public class SetLocalPlayerAsInitializedPacket extends DataPacket {

    public long eid;

    @Override
    public int pid() {
        return ProtocolInfo.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET;
    }

    @Override
    public void decode() {
        eid = this.getUnsignedVarLong();
    }

    @Override
    public void encode() {
        this.putUnsignedVarLong(eid);
    }
}
