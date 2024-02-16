package cn.nukkit.event.block;

import cn.nukkit.block.BlockBigDripleaf;
import cn.nukkit.block.property.enums.BigDripleafTilt;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import lombok.Getter;


public class BigDripleafTiltChangeEvent extends BlockEvent<BlockBigDripleaf> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private BigDripleafTilt oldTilt;
    private BigDripleafTilt newTilt;

    public BigDripleafTiltChangeEvent(BlockBigDripleaf block, BigDripleafTilt oldTilt, BigDripleafTilt newTilt) {
        super(block);
        this.oldTilt = oldTilt;
        this.newTilt = newTilt;
    }

    public BigDripleafTilt getOldTilt() {
        return oldTilt;
    }

    public BigDripleafTilt getNewTilt() {
        return newTilt;
    }

    public void setNewTilt(BigDripleafTilt newTilt) {
        this.newTilt = newTilt;
    }
}
