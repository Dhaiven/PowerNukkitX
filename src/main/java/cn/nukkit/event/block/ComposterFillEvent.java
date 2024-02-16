package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.BlockComposter;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;
import lombok.Getter;


public class ComposterFillEvent extends BlockEvent<BlockComposter> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final Item item;
    private final int chance;
    private boolean success;

    public ComposterFillEvent(BlockComposter block, Player player, Item item, int chance, boolean success) {
        super(block);
        this.player = player;
        this.item = item;
        this.chance = chance;
        this.success = success;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItem() {
        return item;
    }

    public int getChance() {
        return chance;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
