package cn.nukkit.network.protocol;

import cn.nukkit.lang.TranslationContainer;


public class DeathInfoPacket extends DataPacket{

    public TranslationContainer translation;

    @Override
    public int pid() {
        return ProtocolInfo.DEATH_INFO_PACKET;
    }

    @Override
    public void decode() {
        //empty
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(translation.getText());
        this.putArray(translation.getParameters(), this::putString);
    }
}
