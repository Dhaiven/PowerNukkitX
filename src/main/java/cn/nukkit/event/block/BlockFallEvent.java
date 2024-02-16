package cn.nukkit.event.block;

import cn.nukkit.block.BlockFallable;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import lombok.Getter;


public class BlockFallEvent extends BlockEvent<BlockFallable> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public BlockFallEvent(BlockFallable block) {
        super(block);
    }
}
