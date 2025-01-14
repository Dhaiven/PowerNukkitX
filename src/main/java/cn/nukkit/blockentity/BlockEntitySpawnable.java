package cn.nukkit.blockentity;

import cn.nukkit.Player;
import cn.nukkit.level.format.IChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public abstract class BlockEntitySpawnable extends BlockEntity {

    public BlockEntitySpawnable(IChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initBlockEntity() {
        super.initBlockEntity();
        this.spawnToAll();
    }

    /***
     * This method cannot call any chunk-related and level-related methods, otherwise it will cause a deadlock
     */
    public CompoundTag getSpawnCompound() {
        return new CompoundTag()
                .putString("id", namedTag.getString("id"))
                .putInt("x", (int) x)
                .putInt("y", (int) y)
                .putInt("z", (int) z);
    }

    public void spawnTo(Player player) {
        if (this.closed) {
            return;
        }

        player.dataPacket(getSpawnPacket());
    }

    public BlockEntityDataPacket getSpawnPacket() {
        return getSpawnPacket(null);
    }

    public BlockEntityDataPacket getSpawnPacket(CompoundTag nbt) {
        if (nbt == null) {
            nbt = this.getSpawnCompound();
        }

        BlockEntityDataPacket pk = new BlockEntityDataPacket();
        pk.x = (int) this.x;
        pk.y = (int) this.y;
        pk.z = (int) this.z;
        pk.namedTag = nbt;

        return pk;
    }

    public void spawnToAll() {
        if (this.closed) {
            return;
        }

        for (Player player : this.getLevel().getChunkPlayers(this.chunk.getX(), this.chunk.getZ()).values()) {
            if (player.spawned) {
                this.spawnTo(player);
            }
        }
    }

    /**
     * Called when a player updates a block entity's NBT data
     * for example when writing on a sign.
     *
     * @param nbt    tag
     * @param player player
     * @return bool indication of success, will respawn the tile to the player if false.
     */
    public boolean updateCompoundTag(CompoundTag nbt, Player player) {
        return false;
    }
}
