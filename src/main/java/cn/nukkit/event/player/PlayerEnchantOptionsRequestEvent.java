package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.EnchantInventory;
import cn.nukkit.network.protocol.PlayerEnchantOptionsPacket;

import java.util.List;

public class PlayerEnchantOptionsRequestEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private EnchantInventory table;
    private List<PlayerEnchantOptionsPacket.Data> options;

    public PlayerEnchantOptionsRequestEvent(Player player, EnchantInventory table, List<PlayerEnchantOptionsPacket.Data> options) {
        this.player = player;
        this.table = table;
        this.options = options;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }

    public EnchantInventory getInventory() {
        return table;
    }

    public void setOptions(List<PlayerEnchantOptionsPacket.Data> options) {
        this.options = options;
    }

    public List<PlayerEnchantOptionsPacket.Data> getOptions() {
        return options;
    }
}
