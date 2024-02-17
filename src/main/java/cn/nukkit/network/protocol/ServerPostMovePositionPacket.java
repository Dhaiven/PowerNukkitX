package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;

public class ServerPostMovePositionPacket extends DataPacket {

    public Vector3f position;

    @Override
    public int pid() {
        return ProtocolInfo.SERVER_POST_MOVE_POSITION;
    }

    @Override
    public void decode() {
        this.position = this.getVector3f();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVector3f(this.position);
    }
}
