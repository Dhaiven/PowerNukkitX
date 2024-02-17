package cn.nukkit.network.protocol;

import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.ToString;

import java.io.IOException;
import java.nio.ByteOrder;

@ToString
public class UpdateTradePacket extends DataPacket {

    public byte containerId;
    public byte containerType = 15; //trading id
    public int size = 0; // hardcoded to 0
    public int tradeTier;//交易等级
    public long traderUniqueEntityId;//村民id
    public long playerUniqueEntityId;//村民id
    public String displayName;//硬编码的显示名
    public CompoundTag offers;//交易配方
    public boolean newTradingUi;//是否启用新版交易ui
    public boolean usingEconomyTrade;//未知


    @Override
    public int pid() {
        return ProtocolInfo.UPDATE_TRADE_PACKET;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(containerId);
        this.putByte(containerType);
        this.putVarInt(size);
        this.putVarInt(tradeTier);
        this.putEntityUniqueId(traderUniqueEntityId);
        this.putEntityUniqueId(playerUniqueEntityId);
        this.putString(displayName);
        this.putBoolean(newTradingUi);
        this.putBoolean(usingEconomyTrade);
        try {
            this.put(NBTIO.write(this.offers, ByteOrder.LITTLE_ENDIAN, true));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
