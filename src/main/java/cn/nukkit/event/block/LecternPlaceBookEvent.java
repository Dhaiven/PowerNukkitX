package cn.nukkit.event.block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockLectern;
import cn.nukkit.blockentity.BlockEntityLectern;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.item.Item;
import lombok.Getter;


public class LecternPlaceBookEvent extends BlockEvent<BlockLectern> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private final Player player;
    private final BlockEntityLectern lectern;
    private Item book;

    public LecternPlaceBookEvent(Player player, BlockEntityLectern lectern, Item book) {
        super((BlockLectern) lectern.getBlock());
        this.player = player;
        this.lectern = lectern;
        this.book = book;
    }

    public BlockEntityLectern getLectern() {
        return lectern;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getBook() {
        return book.clone();
    }

    public void setBook(Item book) {
        this.book = book;
    }
}
