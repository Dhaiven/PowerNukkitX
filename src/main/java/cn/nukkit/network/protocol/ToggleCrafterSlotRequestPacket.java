package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;

public class ToggleCrafterSlotRequestPacket extends DataPacket {

    public Vector3f blockPosition;
    public byte slot;
    public boolean disabled;

    @Override
    public int pid() {
        return (byte) ProtocolInfo.TOGGLE_CRAFTER_SLOT_REQUEST;
    }

    @Override
    public void decode() {
        this.blockPosition = this.getVector3f();
        this.slot = (byte) this.getByte();
        this.disabled = this.getBoolean();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVector3f(this.blockPosition);
        this.putByte(this.slot);
        this.putBoolean(this.disabled);
    }
}
