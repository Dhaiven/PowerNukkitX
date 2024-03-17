package cn.nukkit.network.protocol;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.Getter;
import lombok.ToString;

/**
 * @author MagicDroidX (Nukkit Project)
 */
@ToString
public class MoveEntityAbsolutePacket extends DataPacket {

    public static final byte FLAG_GROUND = 0x01;
    public static final byte FLAG_TELEPORT = 0x02;
    public static final byte FLAG_FORCE_MOVE_LOCAL_ENTITY = 0x04;

    public long eid;
    public double x;
    public double y;
    public double z;
    public double yaw;
    public double headYaw;
    public double pitch;
    public boolean onGround;
    public boolean teleport;
    public boolean forceMoveLocalEntity;

    @Override
    public int pid() {
        return ProtocolInfo.MOVE_ENTITY_ABSOLUTE_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {
        this.eid = byteBuf.readEntityRuntimeId();
        int flags = byteBuf.readByte();
        onGround = (flags & 0x01) != 0;
        teleport = (flags & 0x02) != 0;
        forceMoveLocalEntity = (flags & 0x04) != 0;
        Vector3f v = byteBuf.readVector3f();
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.pitch = byteBuf.readByte() * (360d / 256d);
        this.headYaw = byteBuf.readByte() * (360d / 256d);
        this.yaw = byteBuf.readByte() * (360d / 256d);
    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeEntityRuntimeId(this.eid);
        byte flags = 0;
        if (onGround) {
            flags |= 0x01;
        }
        if (teleport) {
            flags |= 0x02;
        }
        if (forceMoveLocalEntity) {
            flags |= 0x04;
        }
        this.putByte(flags);
        this.putVector3f((float) this.x, (float) this.y, (float) this.z);
        this.putByte((byte) (this.pitch / (360d / 256d)));
        this.putByte((byte) (this.headYaw / (360d / 256d)));
        this.putByte((byte) (this.yaw / (360d / 256d)));
    }
    
    @Getter
    public enum Flag {
        GROUND(0x01),
        TELEPORT(0x02),
        FORCE_MOVE_LOCAL_ENTITY(0x04);

        private final byte flag;

        Flag(int flag) {
            this.flag = (byte) flag;
        }

        public static Boolean isOnGround(int flags) {
            return (flags & Flag.GROUND.getFlag()) != 0;
        }
    }
    
}
