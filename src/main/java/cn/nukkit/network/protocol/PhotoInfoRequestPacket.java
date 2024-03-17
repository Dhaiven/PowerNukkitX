package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;


@ToString
public class PhotoInfoRequestPacket extends DataPacket {

    public long photoId;


    @Override
    public int pid() {
        return  ProtocolInfo.PHOTO_INFO_REQUEST_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {

        byteBuf.writeEntityUniqueId(photoId);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
