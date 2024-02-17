package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.LessonAction;
import lombok.ToString;


@ToString
public class LessonProgressPacket extends DataPacket {

    public LessonAction action;
    public int score;
    public String activityId;

    @Override
    public int pid() {
        return ProtocolInfo.LESSON_PROGRESS_PACKET;
    }

    @Override
    public void decode() {
        this.action = LessonAction.values()[this.getVarInt()];
        this.score = this.getVarInt();
        this.activityId = this.getString();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(action.ordinal());
        this.putVarInt(score);
        this.putString(activityId);
    }
}
