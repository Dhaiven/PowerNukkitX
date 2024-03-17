package cn.nukkit.level.generator.terra;

import cn.nukkit.Server;
import cn.nukkit.level.DimensionData;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.ChunkState;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.level.generator.ChunkGenerateContext;
import cn.nukkit.level.generator.GenerateStage;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.terra.delegate.PNXProtoChunk;
import cn.nukkit.level.generator.terra.delegate.PNXProtoWorld;
import cn.nukkit.level.generator.terra.delegate.PNXServerWorld;
import cn.nukkit.plugin.InternalPlugin;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.biome.generation.BiomeProvider;
import com.dfsek.terra.api.world.chunk.generation.ChunkGenerator;
import com.dfsek.terra.api.world.chunk.generation.util.GeneratorWrapper;
import com.dfsek.terra.api.world.info.WorldProperties;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Optional;

public class TerraGenerator extends Generator implements GeneratorWrapper {
    private final BiomeProvider biomeProvider;
    private final ConfigPack configPack;
    private final WorldProperties worldProperties;
    private final ChunkGenerator chunkGenerator;

    public TerraGenerator(DimensionData dimensionData, Map<String, Object> options) {
        super(dimensionData, options);
        String packName = "default";
        if (options.containsKey("pack")) {
            packName = options.get("pack").toString();
        }
        this.configPack = createConfigPack(packName);
        this.chunkGenerator = createGenerator(this.configPack);
        this.biomeProvider = this.configPack.getBiomeProvider();
        this.worldProperties = new WorldProperties() {
            @Override
            public long getSeed() {
                return level.getSeed();
            }

            @Override
            public int getMaxHeight() {
                return dimensionData.getMaxHeight();
            }

            @Override
            public int getMinHeight() {
                return dimensionData.getMinHeight();
            }

            @Override
            public Object getHandle() {
                return null;
            }
        };
    }

    @Override
    public void stages(GenerateStage.Builder builder) {
        builder.start(new TerraStage());
    }

    public ConfigPack getConfigPack() {
        return configPack;
    }

    private static ConfigPack createConfigPack(final String packName) {
        Optional<ConfigPack> byID = PNXPlatform.getInstance().getConfigRegistry().getByID(packName);
        return byID.orElseGet(
                () -> PNXPlatform.getInstance().getConfigRegistry().getByID(packName.toUpperCase())
                        .orElseThrow(() -> new IllegalArgumentException("Cant find terra config pack " + packName))
        );
    }


    private static ChunkGenerator createGenerator(ConfigPack config) {
        return config.getGeneratorProvider().newInstance(config);
    }

    class TerraStage extends GenerateStage {
        @Override
        public void apply(ChunkGenerateContext context) {
            final IChunk chunk = context.getChunk();
            final int chunkX = chunk.getX();
            final int chunkZ = chunk.getZ();
            chunkGenerator.generateChunkData(new PNXProtoChunk(chunk), worldProperties, biomeProvider, chunkX, chunkZ);
            final int minHeight = level.getMinHeight();
            final int maxHeight = level.getMaxHeight();
            for (int x = 0; x < 16; x++) {
                for (int y = minHeight; y < maxHeight; y++) {
                    for (int z = 0; z < 16; z++) {
                        chunk.setBiomeId(x, y, z, (Integer) biomeProvider.getBiome(chunkX * 16 + x, y, chunkZ * 16 + z, level.getSeed()).getPlatformBiome().getHandle());
                    }
                }
            }
            chunk.setChunkState(ChunkState.GENERATED);
            var tmp = new PNXProtoWorld(new PNXServerWorld(TerraGenerator.this, context.getLevel()), chunkX, chunkZ);
            try {
                for (var generationStage : configPack.getStages()) {
                    generationStage.populate(tmp);
                }
            } catch (ConcurrentModificationException e) {
                //TODO: 未知原因的 ConcurrentModificationException
            } catch (Exception e) {
                //TODO: 未知原因的 ConcurrentModificationException
            }
            chunk.setChunkState(ChunkState.POPULATED);

            chunk.recalculateHeightMap();
            chunk.populateSkyLight();
            chunk.setLightPopulated();

            chunk.setChunkState(ChunkState.FINISHED);
        }

        @Override
        public String name() {
            return "terra_stage";
        }
    }

    public String getName() {
        return "terra";
    }

    @Override
    public ChunkGenerator getHandle() {
        return chunkGenerator;
    }

    public BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public DimensionData getDimensionData() {
        return dimensionData;
    }

    public Level getLevel() {
        return level;
    }
}
