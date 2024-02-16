package cn.nukkit.event.command;

import cn.nukkit.block.BlockCommandBlock;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.block.BlockEvent;
import lombok.Getter;


public class CommandBlockExecuteEvent extends BlockEvent<BlockCommandBlock> implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    private String command;

    public CommandBlockExecuteEvent(BlockCommandBlock block, String command) {
        super(block);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
