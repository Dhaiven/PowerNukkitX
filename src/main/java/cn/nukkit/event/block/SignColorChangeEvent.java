package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.BlockSignBase;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.utils.BlockColor;
import lombok.Getter;

public class SignColorChangeEvent extends BlockEvent<BlockSignBase> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final BlockColor color;

    public SignColorChangeEvent(BlockSignBase block, Player player, BlockColor color) {
        super(block);
        this.player = player;
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public BlockColor getColor() {
        return this.color;
    }
}
