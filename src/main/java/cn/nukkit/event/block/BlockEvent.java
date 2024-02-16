package cn.nukkit.event.block;

import cn.nukkit.block.Block;
import cn.nukkit.event.Event;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class BlockEvent<T extends Block> extends Event {

    protected final T block;

    public BlockEvent(T block) {
        this.block = block;
    }

    public T getBlock() {
        return block;
    }
}
