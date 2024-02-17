package cn.nukkit.network.protocol;

import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author joserobjr
 */


@ToString
@NoArgsConstructor(onConstructor = @__())
public class PositionTrackingDBClientRequestPacket extends DataPacket {
    
    private static final Action[] ACTIONS = Action.values();
    
    private Action action;
    private int trackingId;


    public Action getAction() {
        return action;
    }

    public int getTrackingId() {
        return trackingId;
    }

    @Override
    public void encode() {
        reset();
        putByte((byte) action.ordinal());
        putVarInt(trackingId);
    }

    @Override
    public void decode() {
        int aByte = getByte();
        action = ACTIONS[aByte];
        trackingId = getVarInt();
    }

    @Override
    public int pid() {
        return ProtocolInfo.POS_TRACKING_CLIENT_REQUEST_PACKET;
    }

    public enum Action {


        QUERY
    }
}
