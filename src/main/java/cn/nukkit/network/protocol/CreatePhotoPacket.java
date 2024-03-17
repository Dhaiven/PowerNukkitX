package cn.nukkit.network.protocol;

import cn.nukkit.network.connection.util.HandleByteBuf;
import lombok.ToString;


@ToString
public class CreatePhotoPacket extends DataPacket {
    public long id;
    public String photoName;
    public String photoItemName;


    @Override
    public int pid() {
        return ProtocolInfo.CREATE_PHOTO_PACKET;
    }

    @Override
    public void decode(HandleByteBuf byteBuf) {

    }

    @Override
    public void encode(HandleByteBuf byteBuf) {
        
        byteBuf.writeLongLE(id);
        byteBuf.writeString(photoName);
        byteBuf.writeString(photoItemName);
    }

    public void handle(PacketHandler handler) {
        handler.handle(this);
    }
}
