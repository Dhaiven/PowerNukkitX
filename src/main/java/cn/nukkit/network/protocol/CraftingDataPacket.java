package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import cn.nukkit.recipe.*;
import cn.nukkit.recipe.descriptor.DefaultDescriptor;
import cn.nukkit.recipe.descriptor.ItemDescriptor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nukkit Project Team
 */
@ToString
public class CraftingDataPacket extends DataPacket {

    public static final String CRAFTING_TAG_CRAFTING_TABLE = "crafting_table";
    public static final String CRAFTING_TAG_CARTOGRAPHY_TABLE = "cartography_table";
    public static final String CRAFTING_TAG_STONECUTTER = "stonecutter";
    public static final String CRAFTING_TAG_FURNACE = "furnace";
    public static final String CRAFTING_TAG_CAMPFIRE = "campfire";
    public static final String CRAFTING_TAG_SOUL_CAMPFIRE = "soul_campfire";
    public static final String CRAFTING_TAG_BLAST_FURNACE = "blast_furnace";
    public static final String CRAFTING_TAG_SMOKER = "smoker";
    public static final String CRAFTING_TAG_SMITHING_TABLE = "smithing_table";

    private final List<Recipe> entries = new ArrayList<>();
    private final List<BrewingRecipe> brewingEntries = new ArrayList<>();
    private final List<ContainerRecipe> containerEntries = new ArrayList<>();
    public boolean cleanRecipes;

    public void addNetworkIdRecipe(List<Recipe> recipes) {
        entries.addAll(recipes);
    }

    public void addFurnaceRecipe(FurnaceRecipe... recipe) {
        Collections.addAll(entries, recipe);
    }

    public void addSmokerRecipe(SmokerRecipe... recipe) {
        Collections.addAll(entries, recipe);
    }

    public void addBlastFurnaceRecipe(BlastFurnaceRecipe... recipe) {
        Collections.addAll(entries, recipe);
    }

    public void addCampfireRecipeRecipe(CampfireRecipe... recipe) {
        Collections.addAll(entries, recipe);
    }

    public void addBrewingRecipe(BrewingRecipe... recipe) {
        Collections.addAll(brewingEntries, recipe);
    }

    public void addContainerRecipe(ContainerRecipe... recipe) {
        Collections.addAll(containerEntries, recipe);
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(entries.size());

        int recipeNetworkId = 1;
        for (Recipe recipe : entries) {
            this.putVarInt(recipe.getType().networkType);
            switch (recipe.getType()) {
                case STONECUTTER -> {
                    StonecutterRecipe stonecutter = (StonecutterRecipe) recipe;
                    this.putString(stonecutter.getRecipeId());
                    this.putUnsignedVarInt(1);
                    this.putRecipeIngredient(new DefaultDescriptor(stonecutter.getIngredient()));
                    this.putUnsignedVarInt(1);
                    this.putSlot(stonecutter.getResult(), true);
                    this.putUUID(stonecutter.getUUID());
                    this.putString(CRAFTING_TAG_STONECUTTER);
                    this.putVarInt(stonecutter.getPriority());
                    this.putUnsignedVarInt(recipeNetworkId++);
                }
                case SHAPELESS, CARTOGRAPHY, SHULKER_BOX -> {
                    ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                    this.putString(shapeless.getRecipeId());
                    List<ItemDescriptor> ingredients = shapeless.getIngredients();
                    this.putUnsignedVarInt(ingredients.size());
                    for (var ingredient : ingredients) {
                        this.putRecipeIngredient(ingredient);
                    }
                    this.putUnsignedVarInt(1);
                    this.putSlot(shapeless.getResult(), true);
                    this.putUUID(shapeless.getUUID());
                    switch (recipe.getType()) {
                        case CARTOGRAPHY -> this.putString(CRAFTING_TAG_CARTOGRAPHY_TABLE);
                        case SHAPELESS, SHULKER_BOX -> this.putString(CRAFTING_TAG_CRAFTING_TABLE);
                    }
                    this.putVarInt(shapeless.getPriority());
                    this.putUnsignedVarInt(recipeNetworkId++);
                }
                case SMITHING_TRANSFORM -> {
                    SmithingTransformRecipe smithing = (SmithingTransformRecipe) recipe;
                    this.putString(smithing.getRecipeId());
                    //todo 1.19.80还没有模板，下个版本再加入
                    this.putRecipeIngredient(smithing.getTemplate());
                    this.putRecipeIngredient(smithing.getBase());
                    this.putRecipeIngredient(smithing.getAddition());
                    this.putSlot(smithing.getResult(), true);
                    this.putString(CRAFTING_TAG_SMITHING_TABLE);
                    this.putUnsignedVarInt(recipeNetworkId++);
                }
                case SMITHING_TRIM -> {
                    //todo
                }
                case SHAPED -> {
                    ShapedRecipe shaped = (ShapedRecipe) recipe;
                    this.putString(shaped.getRecipeId());
                    this.putVarInt(shaped.getWidth());
                    this.putVarInt(shaped.getHeight());
                    for (int z = 0; z < shaped.getHeight(); ++z) {
                        for (int x = 0; x < shaped.getWidth(); ++x) {
                            this.putRecipeIngredient(shaped.getIngredient(x, z));
                        }
                    }
                    List<Item> results = shaped.getResults();
                    this.putUnsignedVarInt(results.size());
                    for (Item output : results) {
                        this.putSlot(output, true);
                    }
                    this.putUUID(shaped.getUUID());
                    this.putString(CRAFTING_TAG_CRAFTING_TABLE);
                    this.putVarInt(shaped.getPriority());
                    this.putUnsignedVarInt(recipeNetworkId++);
                }
                case MULTI -> {
                    this.putUUID(((MultiRecipe) recipe).getId());
                    this.putUnsignedVarInt(recipeNetworkId++);
                }
                case FURNACE, FURNACE_DATA, SMOKER, SMOKER_DATA, BLAST_FURNACE, BLAST_FURNACE_DATA, CAMPFIRE, CAMPFIRE_DATA, SOUL_CAMPFIRE_DATA, SOUL_CAMPFIRE -> {
                    SmeltingRecipe smelting = (SmeltingRecipe) recipe;
                    Item input = smelting.getInput().toItem();
                    this.putVarInt(input.getRuntimeId());
                    if (recipe.getType().name().endsWith("_DATA")) {
                        this.putVarInt(input.getDamage());
                    }
                    this.putSlot(smelting.getResult(), true);
                    switch (recipe.getType()) {
                        case FURNACE, FURNACE_DATA -> this.putString(CRAFTING_TAG_FURNACE);
                        case SMOKER, SMOKER_DATA -> this.putString(CRAFTING_TAG_SMOKER);
                        case BLAST_FURNACE, BLAST_FURNACE_DATA -> this.putString(CRAFTING_TAG_BLAST_FURNACE);
                        case CAMPFIRE, CAMPFIRE_DATA -> this.putString(CRAFTING_TAG_CAMPFIRE);
                        case SOUL_CAMPFIRE_DATA, SOUL_CAMPFIRE -> this.putString(CRAFTING_TAG_SOUL_CAMPFIRE);
                    }
                }
            }
        }

        this.putUnsignedVarInt(this.brewingEntries.size());
        for (BrewingRecipe recipe : brewingEntries) {
            this.putVarInt(recipe.getInput().getRuntimeId());
            this.putVarInt(recipe.getInput().getDamage());
            this.putVarInt(recipe.getIngredient().getRuntimeId());
            this.putVarInt(recipe.getIngredient().getDamage());
            this.putVarInt(recipe.getResult().getRuntimeId());
            this.putVarInt(recipe.getResult().getDamage());
        }

        this.putUnsignedVarInt(this.containerEntries.size());
        for (ContainerRecipe recipe : containerEntries) {
            this.putVarInt(recipe.getInput().getRuntimeId());
            this.putVarInt(recipe.getIngredient().getRuntimeId());
            this.putVarInt(recipe.getResult().getRuntimeId());
        }

        this.putUnsignedVarInt(0); // Material reducers size

        this.putBoolean(cleanRecipes);
    }

    @Override
    public int pid() {
        return ProtocolInfo.CRAFTING_DATA_PACKET;
    }
}
