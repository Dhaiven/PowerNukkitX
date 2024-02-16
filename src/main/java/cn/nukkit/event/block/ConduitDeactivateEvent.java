package cn.nukkit.event.block;

import cn.nukkit.block.BlockConduit;
import cn.nukkit.event.HandlerList;
import lombok.Getter;


public class ConduitDeactivateEvent extends BlockEvent<BlockConduit> {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public ConduitDeactivateEvent(BlockConduit block) {
        super(block);
    }

}
