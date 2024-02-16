package cn.nukkit.event.block;

import cn.nukkit.block.BlockLeaves;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import lombok.Getter;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class LeavesDecayEvent extends BlockEvent<BlockLeaves> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public LeavesDecayEvent(BlockLeaves block) {
        super(block);
    }
}
