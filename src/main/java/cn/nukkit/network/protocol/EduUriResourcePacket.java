package cn.nukkit.network.protocol;

import cn.nukkit.network.protocol.types.EduSharedUriResource;
import lombok.ToString;


@ToString
public class EduUriResourcePacket extends DataPacket {

    public EduSharedUriResource eduSharedUriResource;


    @Override
    public int pid() {
        return  ProtocolInfo.EDU_URI_RESOURCE_PACKET;;
    }

    @Override
    public void decode() {
        String buttonName = this.getString();
        String linkUri = this.getString();
        this.eduSharedUriResource = new EduSharedUriResource(buttonName, linkUri);
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(eduSharedUriResource.buttonName());
        this.putString(eduSharedUriResource.linkUri());
    }
}
