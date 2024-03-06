package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.GameType;
import lombok.ToString;


@ToString
public class UpdatePlayerGameTypePacket extends DataPacket {

    public GameType gameType;
    public long entityId;

    @Override
    public int pid() {
        return ProtocolInfo.UPDATE_PLAYER_GAME_TYPE_PACKET;
    }

    @Override
    public void decode() {
        this.gameType = GameType.from(this.getVarInt());
        this.entityId = this.getVarLong();
    }

    @Override
    public void encode() {
        this.reset();
        this.putVarInt(this.gameType.ordinal());
        this.putVarLong(entityId);
    }
}
