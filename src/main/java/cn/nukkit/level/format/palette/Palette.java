package cn.nukkit.level.format.palette;

import cn.nukkit.level.format.ChunkSection;
import cn.nukkit.level.format.bitarray.BitArray;
import cn.nukkit.level.format.bitarray.BitArrayVersion;
import cn.nukkit.level.updater.block.BlockStateUpdaters;
import cn.nukkit.level.updater.util.tagupdater.CompoundTagUpdaterContext;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.stream.NBTInputStream;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.TreeMapCompoundTag;
import cn.nukkit.utils.ByteBufVarInt;
import cn.nukkit.utils.HashUtils;
import cn.nukkit.utils.SemVersion;
import com.google.common.base.Objects;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import it.unimi.dsi.fastutil.Pair;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Allay Project 2023/4/14
 *
 * @author JukeboxMC | daoge_cmd
 */
public final class Palette<V> {
    private static final byte COPY_LAST_FLAG_HEADER = (byte) (0x7F << 1) | 1;
    private final List<V> palette;
    private BitArray bitArray;


    public Palette(V first) {
        this(first, BitArrayVersion.V2);
    }

    public Palette(V first, BitArrayVersion version) {
        this.bitArray = version.createArray(ChunkSection.SIZE);
        this.palette = new ArrayList<>(16);
        this.palette.add(first);
    }

    public Palette(V first, List<V> palette, BitArrayVersion version) {
        this.bitArray = version.createArray(ChunkSection.SIZE);
        this.palette = palette;
        this.palette.add(first);
    }

    public V get(int index) {
        return this.palette.get(this.bitArray.get(index));
    }

    public void set(int index, V value) {
        final int paletteIndex = this.paletteIndexFor(value);
        this.bitArray.set(index, paletteIndex);
    }

    /**
     * Write the Palette data to the network buffer
     *
     * @param byteBuf    the byte buf
     * @param serializer the serializer
     */
    public void writeToNetwork(ByteBuf byteBuf, RuntimeDataSerializer<V> serializer) {
        writeWords(byteBuf, serializer);
    }

    public void readFromNetwork(ByteBuf byteBuf, RuntimeDataDeserializer<V> deserializer) {
        readWords(byteBuf, readBitArrayVersion(byteBuf));
        final int size = this.bitArray.readSizeFromNetwork(byteBuf);
        for (int i = 0; i < size; i++) this.palette.add(deserializer.deserialize(ByteBufVarInt.readInt(byteBuf)));
    }

    private boolean writeEmpty(ByteBuf byteBuf) {
        if (this.isEmpty()) {
            byteBuf.writeByte(Palette.getPaletteHeader(BitArrayVersion.V0, true));
            byteBuf.writeIntLE(0);
            return true;
        }
        return false;
    }

    private boolean writeLast(ByteBuf byteBuf, Palette<V> last) {
        if (last != null && last.palette.equals(this.palette)) {
            byteBuf.writeByte(COPY_LAST_FLAG_HEADER);
            return true;
        }
        return false;
    }

    private void writeWords(ByteBuf byteBuf, RuntimeDataSerializer<V> serializer) {
        byteBuf.writeByte(getPaletteHeader(this.bitArray.version(), true));
        for (int word : this.bitArray.words()) byteBuf.writeIntLE(word);
        this.bitArray.writeSizeToNetwork(byteBuf, this.palette.size());
        for (V value : this.palette) ByteBufVarInt.writeInt(byteBuf, serializer.serialize(value));
    }

    public void writeToStoragePersistent(ByteBuf byteBuf, PersistentDataSerializer<V> serializer) {
        byteBuf.writeByte(Palette.getPaletteHeader(this.bitArray.version(), false));
        for (int word : this.bitArray.words()) byteBuf.writeIntLE(word);
        byteBuf.writeIntLE(this.palette.size());
        try (final ByteBufOutputStream bufOutputStream = new ByteBufOutputStream(byteBuf)) {
            for (V value : this.palette) {
                NBTIO.write(serializer.serialize(value), bufOutputStream, ByteOrder.LITTLE_ENDIAN);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromStoragePersistent(ByteBuf byteBuf, RuntimeDataDeserializer<V> deserializer) {
        try (final ByteBufInputStream bufInputStream = new ByteBufInputStream(byteBuf);
             NBTInputStream nbtInputStream = new NBTInputStream(bufInputStream, ByteOrder.LITTLE_ENDIAN)) {
            final BitArrayVersion bversion = readBitArrayVersion(byteBuf);
            if (bversion == BitArrayVersion.V0) {
                this.bitArray = bversion.createArray(ChunkSection.SIZE, null);
                this.palette.clear();
                addBlockPalette(byteBuf, deserializer, nbtInputStream);
                this.onResize(BitArrayVersion.V2);
                return;
            }
            readWords(byteBuf, bversion);
            final int paletteSize = byteBuf.readIntLE();
            for (int i = 0; i < paletteSize; i++) {
                addBlockPalette(byteBuf, deserializer, nbtInputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToStorageRuntime(ByteBuf byteBuf, RuntimeDataSerializer<V> serializer, Palette<V> last) {
        if (writeLast(byteBuf, last)) return;
        if (writeEmpty(byteBuf)) return;

        byteBuf.writeByte(Palette.getPaletteHeader(this.bitArray.version(), true));
        for (int word : this.bitArray.words()) byteBuf.writeIntLE(word);
        byteBuf.writeIntLE(this.palette.size());
        for (V value : this.palette) byteBuf.writeIntLE(serializer.serialize(value));
    }

    public void readFromStorageRuntime(ByteBuf byteBuf, RuntimeDataDeserializer<V> deserializer, Palette<V> last) {
        final short header = byteBuf.readUnsignedByte();

        if (hasCopyLastFlag(header)) {
            last.copyTo(this);
            return;
        }

        final BitArrayVersion version = Palette.getVersionFromPaletteHeader(header);
        if (version == BitArrayVersion.V0) {
            this.bitArray = version.createArray(ChunkSection.SIZE, null);
            this.palette.clear();
            this.palette.add(deserializer.deserialize(byteBuf.readIntLE()));

            this.onResize(BitArrayVersion.V2);
            return;
        }

        readWords(byteBuf, version);

        final int paletteSize = byteBuf.readIntLE();
        for (int i = 0; i < paletteSize; i++) this.palette.add(deserializer.deserialize(byteBuf.readIntLE()));
    }

    public int paletteIndexFor(V value) {
        int index = this.palette.indexOf(value);
        if (index != -1) return index;

        index = this.palette.size();
        this.palette.add(value);

        final BitArrayVersion version = this.bitArray.version();
        if (index > version.maxEntryValue) {
            final BitArrayVersion next = version.next;
            if (next != null) this.onResize(next);
        }

        return index;
    }

    public boolean isEmpty() {
        if (this.palette.size() == 1) {
            for (int word : this.bitArray.words())
                if (Integer.toUnsignedLong(word) != 0L)
                    return false;
            return true;
        } else return false;
    }

    private void addBlockPalette(ByteBuf byteBuf,
                                 RuntimeDataDeserializer<V> deserializer,
                                 NBTInputStream input) throws IOException {
        Pair<Integer, SemVersion> p = PaletteUtils.fastReadBlockHash(input, byteBuf);
        if (p.left() == null) {
            CompoundTag oldBlockNbt = (CompoundTag) input.readTag();
            SemVersion semVersion = p.right();
            int version = CompoundTagUpdaterContext.makeVersion(semVersion.major(), semVersion.minor(), semVersion.patch());
            CompoundTag newNbtMap = BlockStateUpdaters.updateBlockState(oldBlockNbt, version);
            var states = new TreeMapCompoundTag(newNbtMap.getCompound("states").getTags());
            var newBlockNbt = new CompoundTag()
                    .putString("name", newNbtMap.getString("name"))
                    .putCompound("states", states);
            this.palette.add(deserializer.deserialize(HashUtils.fnv1a_32_nbt(newBlockNbt)));
        } else {
            this.palette.add(deserializer.deserialize(p.left()));
        }
    }


    private BitArrayVersion readBitArrayVersion(ByteBuf byteBuf) {
        short header = byteBuf.readUnsignedByte();
        return Palette.getVersionFromPaletteHeader(header);
    }

    private void readWords(ByteBuf byteBuf, BitArrayVersion version) {
        final int wordCount = version.getWordsForSize(ChunkSection.SIZE);
        final int[] words = new int[wordCount];
        for (int i = 0; i < wordCount; i++) words[i] = byteBuf.readIntLE();

        this.bitArray = version.createArray(ChunkSection.SIZE, words);
        this.palette.clear();
    }

    private void onResize(BitArrayVersion version) {
        final BitArray newBitArray = version.createArray(ChunkSection.SIZE);
        for (int i = 0; i < ChunkSection.SIZE; i++)
            newBitArray.set(i, this.bitArray.get(i));

        this.bitArray = newBitArray;
    }

    public void copyTo(Palette<V> palette) {
        palette.bitArray = this.bitArray.copy();
        palette.palette.clear();
        palette.palette.addAll(this.palette);
    }

    private static boolean hasCopyLastFlag(short header) {
        return (header >> 1) == 0x7F;
    }

    private static boolean isPersistent(short header) {
        return (header & 1) == 0;
    }

    private static int getPaletteHeader(BitArrayVersion version, boolean runtime) {
        return (version.bits << 1) | (runtime ? 1 : 0);
    }

    private static BitArrayVersion getVersionFromPaletteHeader(short header) {
        return BitArrayVersion.get(header >> 1, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Palette<?> palette1)) return false;
        return Objects.equal(palette, palette1.palette) && Objects.equal(bitArray, palette1.bitArray);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(palette, bitArray);
    }
}