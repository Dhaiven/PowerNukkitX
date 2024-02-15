package cn.nukkit.inventory.request;

import cn.nukkit.Player;
import cn.nukkit.inventory.CraftingTableInventory;
import cn.nukkit.inventory.HumanInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventorySlice;
import cn.nukkit.network.protocol.types.itemstack.ContainerSlotType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NetworkMapping {
    public Inventory getInventory(Player player, ContainerSlotType containerSlotType) {
        return switch (containerSlotType) {
            case CREATED_OUTPUT -> player.getCreativeOutputInventory();
            case CURSOR -> player.getCursorInventory();
            case INVENTORY, HOTBAR, HOTBAR_AND_INVENTORY -> player.getInventory();
            case ARMOR ->
                    new InventorySlice(player.getInventory(), HumanInventory.ARMORS_INDEX, player.getInventory().getSize());
            case BARREL,
                    BREWING_RESULT, BREWING_FUEL, BREWING_INPUT,
                    FURNACE_FUEL, FURNACE_INGREDIENT, FURNACE_RESULT, SMOKER_INGREDIENT, BLAST_FURNACE_INGREDIENT,
                    ENCHANTING_INPUT, ENCHANTING_MATERIAL,
                    SMITHING_TABLE_INPUT, SMITHING_TABLE_MATERIAL, SMITHING_TABLE_RESULT,
                    ANVIL_INPUT, ANVIL_MATERIAL, ANVIL_RESULT,
                    STONECUTTER_INPUT, STONECUTTER_RESULT,
                    GRINDSTONE_ADDITIONAL, GRINDSTONE_INPUT, GRINDSTONE_RESULT,
                    LEVEL_ENTITY -> {
                if (player.getTopWindow().isPresent()) {
                    yield player.getTopWindow().get();
                } else {
                    throw new IllegalArgumentException("error when there is no currently open container when an ItemStackRequest is received");
                }
            }
            case CRAFTING_INPUT -> {
                if (player.getTopWindow().isPresent() && player.getTopWindow().get() instanceof CraftingTableInventory) {
                    yield player.getTopWindow().get();
                } else {
                    yield player.getCraftingGrid();
                }
            }
            //case CARTOGRAPHY_ADDITIONAL, CARTOGRAPHY_INPUT, CARTOGRAPHY_RESULT -> player.getCartographyTableInventory();//todo
            default ->
                    throw new IllegalArgumentException("Cant handle containerSlotType: %s when an ItemStackRequest is received!".formatted(containerSlotType.name().toLowerCase()));
        };
    }
}
