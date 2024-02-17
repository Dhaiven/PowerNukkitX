package cn.nukkit.network.protocol;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.ToString;

import java.util.List;
import java.util.UUID;


@ToString
public class EmoteListPacket extends DataPacket {

    public long runtimeId;
    public final List<UUID> pieceIds = new ObjectArrayList<>();

    @Override
    public int pid() {
        return ProtocolInfo.EMOTE_LIST_PACKET;;
    }

    @Override
    public void decode() {
        this.runtimeId = this.getEntityRuntimeId();
        int size = (int) this.getUnsignedVarInt();
        for (int i = 0; i < size; i++) {
            UUID id = this.getUUID();
            pieceIds.add(id);
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(runtimeId);
        this.putUnsignedVarInt(pieceIds.size());
        for (UUID id : pieceIds) {
            this.putUUID(id);
        }
    }
}
