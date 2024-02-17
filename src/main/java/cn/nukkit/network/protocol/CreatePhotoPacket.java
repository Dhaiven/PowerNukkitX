package cn.nukkit.network.protocol;

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
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putLLong(id);
        this.putString(photoName);
        this.putString(photoItemName);
    }
}
