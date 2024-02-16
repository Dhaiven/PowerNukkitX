package cn.nukkit.event.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.Inventory;
import lombok.Getter;

/**
 * @author Box (Nukkit Project)
 */
public class InventoryOpenEvent extends InventoryEvent<Inventory> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;

    public InventoryOpenEvent(Inventory inventory, Player player) {
        super(inventory);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
