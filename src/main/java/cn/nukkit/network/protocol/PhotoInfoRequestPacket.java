package cn.nukkit.network.protocol;

import lombok.ToString;


@ToString
public class PhotoInfoRequestPacket extends DataPacket {

    public long photoId;


    @Override
    public int pid() {
        return  ProtocolInfo.PHOTO_INFO_REQUEST_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityUniqueId(photoId);
    }
}
