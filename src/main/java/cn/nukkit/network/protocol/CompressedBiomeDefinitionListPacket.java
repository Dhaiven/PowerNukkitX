package cn.nukkit.network.protocol;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class CompressedBiomeDefinitionListPacket extends DataPacket {

    private CompoundTag definitions;

    @Override
    public int pid() {
        return ProtocolInfo.COMPRESSED_BIOME_DEFINITIONS_LIST;
    }

    protected static final byte[] COMPRESSED_INDICATOR = new byte[]{(byte) 0xe4, (byte) 0x92, 0x3f, 0x43, 0x4f, 0x4d, 0x50, 0x52, 0x45, 0x53, 0x53, 0x45, 0x44}; // __?COMPRESSED

    @Override
    public void encode() {//todo 计划实现
    }

    @Override
    public void decode() {
    }
}
