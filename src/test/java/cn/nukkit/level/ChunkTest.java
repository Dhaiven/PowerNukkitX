package cn.nukkit.level;

import cn.nukkit.GameMockExtension;
import cn.nukkit.block.BlockDiamondOre;
import cn.nukkit.block.BlockGoldOre;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityID;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.biome.BiomeID;
import cn.nukkit.level.format.Chunk;
import cn.nukkit.level.format.ChunkSection;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.level.format.LevelProvider;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@ExtendWith({GameMockExtension.class})
public class ChunkTest {
    @Test
    void testSetBlockState(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        chunk.setBlockState(0, 100, 0, BlockGoldOre.PROPERTIES.getDefaultState());
        Assertions.assertEquals(BlockGoldOre.PROPERTIES.getDefaultState(), chunk.getBlockState(0, 100, 0));
    }

    @Test
    void test_recalculateHeightMap(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        chunk.recalculateHeightMap();
    }

    @Test
    void test_recalculateHeightMapColumn(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        chunk.recalculateHeightMapColumn(0, 0);
    }

    @Test
    void test_populateSkyLight(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        chunk.populateSkyLight();
    }

    @Test
    void testSaveAndReadChunkEntity(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        Item item = Item.get(ItemID.GOLD_INGOT);
        EntityItem itemEntity = (EntityItem) Entity.createEntity(Entity.ITEM,
                chunk,
                Entity.getDefaultNBT(new Vector3(0, 64, 0),
                                new Vector3(0, 0, 0),
                                new Random().nextFloat() * 360, 0)
                        .putShort("Health", 5)
                        .putCompound("Item", NBTIO.putItemHelper(item))
                        .putShort("PickupDelay", 10));
        chunk.addEntity(itemEntity);

        List<Entity> list = chunk.getEntities().values().stream().filter(e -> e.getIdentifier().equals(EntityID.ITEM)).toList();
        Assertions.assertEquals(2, chunk.getEntities().values().size());//player test and item entity
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(EntityID.ITEM, list.get(0).getIdentifier());

        levelDBProvider.saveChunk(0, 0, chunk);
        IChunk newChunk = levelDBProvider.getChunk(0, 0);
        Assertions.assertNotNull(newChunk);
        List<Entity> list2 = chunk.getEntities().values().stream().filter(e -> e.getIdentifier().equals(EntityID.ITEM)).toList();
        Assertions.assertEquals(2, chunk.getEntities().values().size());
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals(EntityID.ITEM, list2.get(0).getIdentifier());
    }

    @Test
    @SneakyThrows
    void test_getOrCreateSection(LevelProvider levelDBProvider) {
        IChunk chunk = levelDBProvider.getChunk(0, 0);
        Method getOrCreateSection = Chunk.class.getDeclaredMethod("getOrCreateSection", int.class);
        getOrCreateSection.setAccessible(true);
        ChunkSection s1 = (ChunkSection) getOrCreateSection.invoke(chunk, -4);
        Assertions.assertEquals(-4, s1.y());
        ChunkSection s2 = (ChunkSection) getOrCreateSection.invoke(chunk, 19);
        Assertions.assertEquals(19, s2.y());
    }

    @Test
    void testMultiThreadOperate(LevelProvider levelDBProvider) {
        final IChunk chunk = levelDBProvider.getChunk(0, 0);
        Set<Thread> threadSet = new HashSet<>();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            if (i % 2 == 0) {
                Thread thread = new Thread(() -> {
                    chunk.batchProcess(unsafeChunk -> {
                        for (int k = 0; k < 16; k++) {
                            for (int l = 0; l < 16; l++) {
                                for (int j = unsafeChunk.getDimensionData().getMinHeight(); j < unsafeChunk.getDimensionData().getMaxHeight(); j++) {
                                    unsafeChunk.setBlockState(k, j, l, BlockGoldOre.PROPERTIES.getDefaultState(), 0);
                                    unsafeChunk.setBiomeId(k, j, l, BiomeID.BIRCH_FOREST_MUTATED);
                                }
                                unsafeChunk.setHeightMap(k, l, 4);
                            }
                        }
                    });
                });
                threadSet.add(thread);
                thread.start();
            } else {
                Thread thread = new Thread(() -> {
                    chunk.batchProcess(unsafeChunk -> {
                        for (int k = 0; k < 16; k++) {
                            for (int l = 0; l < 16; l++) {
                                for (int j = unsafeChunk.getDimensionData().getMinHeight(); j < unsafeChunk.getDimensionData().getMaxHeight(); j++) {
                                    unsafeChunk.setBlockState(k, j, l, BlockDiamondOre.PROPERTIES.getDefaultState(), 0);
                                    unsafeChunk.setBiomeId(k, j, l, BiomeID.DEEP_COLD_OCEAN);
                                    unsafeChunk.setBlockSkyLight(k, j, l, 4);
                                }
                            }
                        }
                    });
                });
                threadSet.add(thread);
                thread.start();
            }
        }
        threadSet.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
