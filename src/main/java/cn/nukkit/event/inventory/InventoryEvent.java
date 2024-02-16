package cn.nukkit.event.inventory;

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.inventory.Inventory;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class InventoryEvent<E extends Inventory> extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final E inventory;

    public InventoryEvent(E inventory) {
        this.inventory = inventory;
    }

    public E getInventory() {
        return inventory;
    }

    public Player[] getViewers() {
        return this.inventory.getViewers().toArray(Player.EMPTY_ARRAY);
    }
}
