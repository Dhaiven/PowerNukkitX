package cn.nukkit.inventory;

import cn.nukkit.blockentity.BlockEntityBrewingStand;
import cn.nukkit.blockentity.BlockEntityNameable;
import cn.nukkit.item.Item;

public class BrewingInventory extends ContainerInventory implements BlockEntityInventoryNameable {
    public BrewingInventory(BlockEntityBrewingStand brewingStand) {
        super(brewingStand, InventoryType.BREWING_STAND, 5);//1 INPUT, 3 POTION, 1 fuel
    }

    @Override
    public BlockEntityBrewingStand getHolder() {
        return (BlockEntityBrewingStand) this.holder;
    }

    public Item getIngredient() {
        return getItem(0);
    }

    public void setIngredient(Item item) {
        setItem(0, item);
    }

    public void setFuel(Item fuel) {
        setItem(4, fuel);
    }

    public Item getFuel() {
        return getItem(4);
    }

    @Override
    public void onSlotChange(int index, Item before, boolean send) {
        super.onSlotChange(index, before, send);

        if (index >= 1 && index <= 3) {
            this.getHolder().updateBlock();
        }

        this.getHolder().scheduleUpdate();
    }

    @Override
    public BlockEntityNameable getBlockEntityInventoryHolder() {
        return getHolder();
    }
}